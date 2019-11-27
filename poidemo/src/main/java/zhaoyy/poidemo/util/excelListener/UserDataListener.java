package zhaoyy.poidemo.util.excelListener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import zhaoyy.poidemo.dao.UserDao;
import zhaoyy.poidemo.entity.User;
import zhaoyy.poidemo.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 16:47
 */
public class UserDataListener extends AnalysisEventListener<User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataListener.class);

    /**
    * 用来保存最终数据的list
    */
    List<User> list = new ArrayList<>(6);

    /**
     * 用户业务需要用到的dao
     */
    UserDao userDao;

    /**
    * 用户业务需要用到的service
    */
    UserService userService;

    /**
    * 因为项目使用了spring，每次创建Listener的时候需要把Spring管理的类传进来
     * 这里不仅可以只传service，也可以传dao，或者两个值都传,因此三个构造方法我都定义了
    *
    * @param userService
    * @return
    * @author zhaoyuyang
    * @since 2019/11/25 0025 17:11
    */
    public UserDataListener(UserService userService){
        this.userService = userService;
    }

    public UserDataListener(UserDao userDao){
        this.userDao = userDao;
    }

    public UserDataListener(UserDao userDao,UserService userService){
        this.userService = userService;
        this.userDao = userDao;
    }

    /**
    * 每一条数据解析的过程都会调用此方法，所以判断excel文件内部的具体状况都在这个方法中执行
    *  在这里可以将解析后的数据添加到上方定义的list
    * @param user
	* @param analysisContext
    * @return void
    * @author zhaoyuyang
    * @since 2019/11/25 0025 16:51
    */
    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        LOGGER.info("解析到的第{}行数据，内容为{}", analysisContext.readRowHolder().getRowIndex(),JSONObject.toJSONString(user));
        list.add(user);
    }

    /**
    * 所有数据解析完成，都回来调用的方法,这里可以写具体的逻辑，用service或者dao都行
    *
    * @param analysisContext
    * @return void
    * @author zhaoyuyang
    * @since 2019/11/25 0025 16:52
    */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //这里也要保存数据，确保最后遗留的数据也存储到数据库
        LOGGER.info("所有的数据解析完成");
        saveDataToDataBase();
    }

    /**
    * 这里写最后的逻辑
    *
    * @param
    * @return void
    * @author zhaoyuyang
    * @since 2019/11/25 0025 16:56
    */
    private void saveDataToDataBase() {
        // 这里先不写具体的逻辑，先进行输出
        list.forEach(user -> {
            System.out.println(JSONObject.toJSONString(user));
        });
    }

    /**
    * 异常处理
    *
    * @param exception
	* @param context
    * @return void
    * @author zhaoyuyang
    * @since 2019/11/25 0025 17:21
    */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        //如果是某一个单元格的转换异常，能获取到具体的行号
        String rowIndex = "第"+context.readRowHolder().getRowIndex()+"行";
        LOGGER.error("{}解析失败CauseBy:{}",rowIndex,exception.getMessage());
       if(exception instanceof ExcelDataConvertException){
           ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
          // String rowIndex = "第"+excelDataConvertException.getRowIndex()+"行";
           String columnIndex = "第"+excelDataConvertException.getColumnIndex()+"列";
           throw new RuntimeException(rowIndex+"-"+columnIndex+"-"+"CauseBy:"+exception.getMessage());
       }
        throw new RuntimeException(rowIndex+":"+exception.getMessage());
    }
}

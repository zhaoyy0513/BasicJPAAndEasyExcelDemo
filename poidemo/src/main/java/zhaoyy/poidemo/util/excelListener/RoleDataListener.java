package zhaoyy.poidemo.util.excelListener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhaoyy.poidemo.dao.RoleDao;
import zhaoyy.poidemo.entity.Role;
import zhaoyy.poidemo.service.RoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 18:44
 */
public class RoleDataListener extends AnalysisEventListener<Role> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleDataListener.class);

    /**
     * 用来保存最终数据的list
     */
    List<Role> list = new ArrayList<>(6);

    /*
    *  可能会用到的业务类
    */
    RoleDao roleDao;
    RoleService roleService;

    public RoleDataListener(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public RoleDataListener(RoleService roleService) {
        this.roleService = roleService;
    }

    public RoleDataListener(RoleDao roleDao, RoleService roleService) {
        this.roleDao = roleDao;
        this.roleService = roleService;
    }

    @Override
    public void invoke(Role role, AnalysisContext analysisContext) {
        LOGGER.info("开始读取第{}行数据，数据为{}",analysisContext.readRowHolder().getRowIndex(), JSONObject.toJSONString(role));
        //将读入的整行数据转换为实体并存入list中
        list.add(role);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        LOGGER.info("数据读取完成");
        saveDataToDataBase();
    }

    private void saveDataToDataBase() {
        // 暂时不写具体的逻辑
        list.forEach(role -> {
            System.out.println(JSONObject.toJSONString(role));
        });
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        String info = "第"+context.readRowHolder().getRowIndex()+"行数据传递异常，请查看文件内容";
        if(exception instanceof ExcelDataConvertException){
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            String position = "第"+excelDataConvertException.getRowIndex()+"行,第"+excelDataConvertException.getColumnIndex()+"列";
            LOGGER.error("{}存在异常,causeBy:{}",position,exception.getMessage());
            throw new RuntimeException(position+"数据转换异常，请查看文件内容");
        }
        throw  new RuntimeException(info);
    }
}

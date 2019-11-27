package zhaoyy.poidemo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import zhaoyy.poidemo.dao.RoleDao;
import zhaoyy.poidemo.dao.UserDao;
import zhaoyy.poidemo.entity.Role;
import zhaoyy.poidemo.entity.User;
import zhaoyy.poidemo.service.RoleService;
import zhaoyy.poidemo.service.UserService;
import zhaoyy.poidemo.util.R;
import zhaoyy.poidemo.util.excelListener.RoleDataListener;
import zhaoyy.poidemo.util.excelListener.UserDataListener;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 17:02
 */
@RequestMapping("/upload")
@Controller
public class UploadController {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleDao roleDao;

    /**
     * 最简单的读
     * 创建excel对应的实体对象
     * 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器
     *
     * @param multipartFile
     * @return org.springframework.http.ResponseEntity
     * @author zhaoyuyang
     * @since 2019/11/25 0025 17:16
     */
    @PostMapping("/user")
    public ResponseEntity uploadUser(@RequestParam("importFile") MultipartFile multipartFile) throws IOException {
        // 创建导入文件的输入流
        InputStream inputStream = multipartFile.getInputStream();
        // 这里 需要指定用哪个class去读，并指定需要使用的文件监听器，然后读取一个sheet文件流后会自动关闭
        EasyExcel.read(inputStream, User.class, new UserDataListener(userService)).sheet().doRead();
        return R.ok(multipartFile.getOriginalFilename() + "数据上传成功!");
    }

    @PostMapping("/userAndRole")
    public ResponseEntity uploadUserAnRole(@RequestParam("importFile") MultipartFile multipartFile) throws IOException {
        // 创建导入文件的输入流
        InputStream inputStream = multipartFile.getInputStream();

        /* 如果有多个sheet并且sheet中的类型对应的是同一个实体，则使用下面的方法
        EasyExcel.read(inputStream, User.class, new UserDataListener(userService)).doReadAll();*/

        // 这里 需要指定用哪个class去读，并指定需要使用的文件监听器，然后读取一个sheet文件流后会自动关闭
        ExcelReader excelReader = EasyExcel.read(inputStream).build();
        try {
            // 为不同的sheet添加不同的Listener
            ReadSheet readUser = EasyExcel.readSheet(0).head(User.class).registerReadListener(new UserDataListener(userService)).build();
            ReadSheet readRole = EasyExcel.readSheet(1).head(Role.class).registerReadListener(new RoleDataListener(roleService)).build();
            excelReader.read(readUser, readRole);
        } finally {
            // 无论如何都要记得关闭，读的时候会创建临时文件，不关闭磁盘会崩
            excelReader.finish();
        }
        return R.ok(multipartFile.getOriginalFilename() + "数据上传成功!");
    }
}

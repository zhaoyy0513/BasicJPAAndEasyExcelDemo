package zhaoyy.poidemo.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import zhaoyy.poidemo.dao.RoleDao;
import zhaoyy.poidemo.entity.Role;
import zhaoyy.poidemo.service.RoleService;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 21:53
 */
@RequestMapping("/role")
@Controller
public class RoleController {

    @Autowired
    RoleService roleService;

    @PutMapping
    public ResponseEntity updateRole(@RequestBody JSONObject jsonObject) throws IllegalAccessException {
        Role role = jsonObject.toJavaObject(Role.class);
        Role updated = roleService.updateRole(role);
        return ResponseEntity.ok(updated);
    }


}

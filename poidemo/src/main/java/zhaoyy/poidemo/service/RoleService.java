package zhaoyy.poidemo.service;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhaoyy.poidemo.dao.RoleDao;
import zhaoyy.poidemo.entity.Role;
import zhaoyy.poidemo.entity.User;
import zhaoyy.poidemo.util.JudgeNullUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 18:45
 */
@Service
public class RoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);
    @Autowired
    RoleDao roleDao;

    public Role updateRole(Role role) throws IllegalAccessException {
        // 从数据库获取原有的值
        Optional<Role> byId = roleDao.findById(role.getId());
        if(byId.isPresent()){
            //如果optional其不为空
            Role old = byId.get();
            System.out.println("前:"+JSONObject.toJSONString(old));
            // 获取接收的对象为空的属性数组
            String[] ignore = JudgeNullUtil.getNullPropertiesArray(role);
            // 将前面的赋值给后面的
            BeanUtils.copyProperties(role,old,ignore);
            System.out.println("后"+JSONObject.toJSONString(old));
            return roleDao.save(old);
        }
        // 如果没有修改成功就将原来的返回
        LOGGER.error("RoleService-updateRole异常，没有更新成功");
        return byId.get();
    }
}

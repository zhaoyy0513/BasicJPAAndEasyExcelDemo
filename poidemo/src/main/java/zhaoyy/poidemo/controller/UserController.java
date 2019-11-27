package zhaoyy.poidemo.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhaoyy.poidemo.dto.UserDTO;
import zhaoyy.poidemo.entity.User;
import zhaoyy.poidemo.service.UserService;
import zhaoyy.poidemo.util.PageEntity;
import zhaoyy.poidemo.util.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 11:04
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    /**
    * 新增用户
    *
    * @param jsonObject
    * @return org.springframework.http.ResponseEntity
    * @author zhaoyuyang
    * @since 2019/11/25 0025 19:24
    */
    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody JSONObject jsonObject){
        return R.ok(userService.deleteUser(jsonObject.getInteger("id")));
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody JSONObject jsonObject){
        int result = userService.updateUser(jsonObject.toJavaObject(User.class));
        return R.ok("id为"+result+"的用户修改成功");
    }

    /**
    * 用JPA封装好的方法查询
    *
    * @param
    * @return org.springframework.http.ResponseEntity
    * @author zhaoyuyang
    * @since 2019/11/25 0025 19:17
    */
    @GetMapping("/getAllUser")
    public ResponseEntity listUser() {
        return R.ok(userService.listAllUser());
    }

    /**
    * 用普通的example查询
    *
    * @param id
    * @return org.springframework.http.ResponseEntity
    * @author zhaoyuyang
    * @since 2019/11/25 0025 19:17
    */
    @GetMapping("/getUser")
    public ResponseEntity getUserById(@RequestParam(value = "id") Integer id) {
        User user = userService.getUserById(id);
        return R.ok(user);
    }

    /**
     * 用ExampleMatcher创建查询
     *
     * @param nickname
     * @param username
     * @return org.springframework.http.ResponseEntity
     * @author zhaoyuyang
     * @since 2019/11/25 0025 14:54
     */
    @GetMapping("/getUserForParam")
    public ResponseEntity getUserByParam(@RequestParam(value = "nickname", required = false) String nickname,
                                         @RequestParam(value = "username", required = false) String username) {
        User condition = new User();
        if (StringUtils.isNotBlank(nickname)) {
            condition.setNickname(nickname);
        }

        if (StringUtils.isNotBlank(username)) {
            condition.setUsername(username);
        }
        List<User> users = userService.getUserForParam(condition);
        return R.ok(users);
    }

    /**
     * 用Specification进行查询
     *
     * @param nickname
     * @param username
     * @return org.springframework.http.ResponseEntity
     * @author zhaoyuyang
     * @since 2019/11/25 0025 14:55
     */
    @GetMapping("/getUserForParamCriteria")
    public ResponseEntity getUserForParamCriteria(@RequestParam(value = "nickname", required = false) String nickname,
                                                  @RequestParam(value = "username", required = false) String username) {
        User condition = new User();
        if (StringUtils.isNotBlank(nickname)) {
            condition.setNickname(nickname);
        }
        if (StringUtils.isNotBlank(username)) {
            condition.setUsername(username);
        }
        List<User> users = userService.getUserForParamCriteria(condition);
        return R.ok(users);
    }

    /**
    * 手写模糊代码
    *
    * @param nickname
	* @param username
    * @return org.springframework.http.ResponseEntity
    * @author zhaoyuyang
    * @since 2019/11/25 0025 15:37
    */
    @GetMapping("/relationSelect")
    public ResponseEntity relationSelect(@RequestParam(value = "nickname", required = false) String nickname,
                                         @RequestParam(value = "username", required = false) String username) {

        User condition = new User();
        if (StringUtils.isNotBlank(nickname)) {
            condition.setNickname(nickname);
        }
        if (StringUtils.isNotBlank(username)) {
            condition.setUsername(username);
        }
        List<User> users = userService.relationSelect(condition);
        return R.ok(users);
    }

    /**
    * 手写代码并返回指定的DTO
    *
    * @param nickname
	* @param username
    * @return org.springframework.http.ResponseEntity
    * @author zhaoyuyang
    * @since 2019/11/25 0025 17:01
    */
    @GetMapping("/relationSelectForDTO")
    public ResponseEntity relationSelectForDTO(@RequestParam(value = "nickname", required = false) String nickname,
                                         @RequestParam(value = "username", required = false) String username) {

        User condition = new User();
        if (StringUtils.isNotBlank(nickname)) {
            condition.setNickname(nickname);
        }
        if (StringUtils.isNotBlank(username)) {
            condition.setUsername(username);
        }
        UserDTO user = userService.relationSelectForDTO(condition);
        return R.ok(user);
    }

    /**
    * 手写模糊查询和分页并返回DTO
    *
    * @param page
	* @param limit
	* @param nickname
	* @param username
    * @return org.springframework.http.ResponseEntity
    * @author zhaoyuyang
    * @since 2019/11/27 0027 9:43
    */

    @GetMapping("/listUserForParam")
    public ResponseEntity listUserForParam(@RequestParam(value = "page",required = false)Integer page,
                                           @RequestParam(value = "limit",required = false)Integer limit,
                                           @RequestParam(value = "nickname",required = false)String nickname,
                                           @RequestParam(value = "username",required = false)String username){
        // pageAble默认是从0开始的 page如果为null或者小于0,则page为0，
        page = page==null||page<0? 0: page-1;

        if(limit==null||limit>20||limit<0){
            limit = 20;
        }
        Map<String,Object> map = new HashMap<>(6);
        if(StringUtils.isNotBlank(nickname)){
            map.put("nickname",nickname);
        }
        if(StringUtils.isNotBlank(username)){
            map.put("username",username);
        }
        Page<User> list = userService.listUserForParam(map,page,limit);
        PageEntity instance = PageEntity.getInstance(list);
        return R.ok(instance);
    }
}

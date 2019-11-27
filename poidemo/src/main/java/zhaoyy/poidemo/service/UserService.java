package zhaoyy.poidemo.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zhaoyy.poidemo.dao.UserDao;
import zhaoyy.poidemo.dto.UserDTO;
import zhaoyy.poidemo.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 11:12
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserDao userDao;

    public List<User> deleteUser(Integer id) {
        userDao.deleteById(id);
        return userDao.findAll();
    }

    public List<User> listAllUser() {
        return userDao.findAll();
    }

    public User getUserById(Integer id) {
        User user = new User();
        user.setId(id);
        Example<User> example = Example.of(user);
        Optional<User> one = userDao.findOne(example);
        if (!one.isPresent()) {
            // 如果为空
            logger.error("user未找到UserService-getUserById()");
            Assert.assertEquals("Optional.empty", one.toString());
        } else {
            return one.get();
        }
        return null;
    }

    public List<User> getUserForParam(User condition) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase()
                //contains 全部模糊查询，即%{nickname}%
                // 对应的还有endsWith()  %{nickname}
                // startsWith()  {nickname}%
                ////改变默认字符串匹配方式：模糊查询
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withMatcher("nickname", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id");
        //忽略字段，即不管id是什么值都不加入查询条件
        // 创建实例
        Example example = Example.of(condition, exampleMatcher);
        // 查询
        List all = userDao.findAll(example);
        System.out.println(JSONObject.toJSONString(all));
        return all;
    }

    public List<User> getUserForParamCriteria(User condition) {
        List<User> users = userDao.findAll((Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            // 通过criteriaBuilder创建条件
            Predicate p0 = criteriaBuilder.isNotNull(root.get("id"));
            Predicate p1 = criteriaBuilder.like(root.get("nickname"), "%" + condition.getNickname() + "%");
            // 里面的root.get()里面的参数名 必须与entity里的属性名对应，不是跟数据库的对应
            Predicate p2 = criteriaBuilder.isNotNull(root.get("createDate"));
            return criteriaBuilder.and(p0, p1, p2);
        });
        return users;
    }

    public List<User> relationSelect(User condition) {
        List<User> users = userDao.relationSelect(condition);
        System.out.println(JSONObject.toJSONString(condition));
        return users;
    }

    public UserDTO relationSelectForDTO(User condition) {
        UserDTO userDTO = userDao.relationSelectForDTO(condition);
        return userDTO;
    }

    public int updateUser(User user) {
        user = userDao.save(user);
        return user.getId();
    }

    public Page<User> listUserForParam(Map<String, Object> map, Integer page, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page, limit,sort);
        //PageRequest id = PageRequest.of(page, limit, Sort.Direction.DESC, "id");

        Page<User> all = userDao.findAll((Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            //通过Predicate创建条件
            List<Predicate> condition = new ArrayList<>();
            Predicate p0 = criteriaBuilder.isNotNull(root.get("id"));
            condition.add(p0);
            if (map.get("nickname") != null) {
                //criteriaBuilder.equal(root.get("nickname"),map.get("nickname"));
                Predicate p1 = criteriaBuilder.like(root.get("nickname"), "%" + map.get("nickname") + "%");
                condition.add(p1);
            }
            if (map.get("username") != null) {
                Predicate p2 = criteriaBuilder.like(root.get("nickname"), "%" + map.get("username") + "%");
                condition.add(p2);
            }
           return criteriaBuilder.and(condition.toArray(new Predicate[0]));
//            return criteriaQuery.where(condition.toArray(new Predicate[0]));
        }, pageRequest);
        return all;
    }
}

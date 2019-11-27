package zhaoyy.poidemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zhaoyy.poidemo.dto.UserDTO;
import zhaoyy.poidemo.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 11:17
 */
@Repository
public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User>, Serializable {

    @Transactional(rollbackFor = Exception.class)
    @Query(value = "select u from User u where u.nickname like concat('%',:#{#condition.nickname},'%') ")
    List<User> relationSelect(@Param("condition") User condition);

    @Transactional(rollbackFor = Exception.class)
    @Query(value = "select new zhaoyy.poidemo.dto.UserDTO(u.nickname,r.id,r.nameRemark ) from User u left join UserRole ur on ur.userId = u.id " +
            "left join Role r on r.id = ur.roleId where u.nickname like concat('%',:#{#condition.nickname},'%') ")
    UserDTO relationSelectForDTO(User condition);




}

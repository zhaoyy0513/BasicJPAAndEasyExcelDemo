package zhaoyy.poidemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zhaoyy.poidemo.entity.Role;

import java.io.Serializable;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 18:45
 */
@Repository
public interface RoleDao extends JpaRepository<Role,Integer>, JpaSpecificationExecutor<Role>, Serializable {

}

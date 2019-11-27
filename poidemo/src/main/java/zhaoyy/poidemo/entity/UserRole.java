package zhaoyy.poidemo.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 15:40
 */
@Entity
@Table(name = "user_role", schema = "tb_security", catalog = "")
public class UserRole {
    private long id;
    private Long userId;
    private Long roleId;
    private Timestamp createdTime;
    private Timestamp modifiedTime;
    private Integer status;
    private Byte isDelete;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "created_time")
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "modified_time")
    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "is_delete")
    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return id == userRole.id &&
                Objects.equals(userId, userRole.userId) &&
                Objects.equals(roleId, userRole.roleId) &&
                Objects.equals(createdTime, userRole.createdTime) &&
                Objects.equals(modifiedTime, userRole.modifiedTime) &&
                Objects.equals(status, userRole.status) &&
                Objects.equals(isDelete, userRole.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, roleId, createdTime, modifiedTime, status, isDelete);
    }
}

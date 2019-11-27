package zhaoyy.poidemo.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 16:13
 */
@Entity
public class Role {
    @ExcelIgnore
    private int id;
    @ExcelProperty(index = 0,value = "角色名")
    private String name;
    @ExcelProperty(index = 1,value = "角色描述")
    private String nameRemark;
    private Timestamp createdTime;
    private Timestamp modifiedTime;
    private Integer status;
    private Byte isDelete;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "name_remark")
    public String getNameRemark() {
        return nameRemark;
    }

    public void setNameRemark(String nameRemark) {
        this.nameRemark = nameRemark;
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
        Role role = (Role) o;
        return id == role.id &&
                Objects.equals(name, role.name) &&
                Objects.equals(nameRemark, role.nameRemark) &&
                Objects.equals(createdTime, role.createdTime) &&
                Objects.equals(modifiedTime, role.modifiedTime) &&
                Objects.equals(status, role.status) &&
                Objects.equals(isDelete, role.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nameRemark, createdTime, modifiedTime, status, isDelete);
    }
}

package zhaoyy.poidemo.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 21:08
 */
@Entity
public class User {
    @ExcelIgnore
    private int id;
    @ExcelProperty(index = 0,value = "名字")
    private String nickname;
    @ExcelProperty(index = 2,value = "用户名")
    private String username;
    @ExcelProperty(index = 3,value = "密码")
    private String password;
    @ExcelProperty(index = 4,value = "用户类型")
    private Integer admin;
    private Timestamp createDate;
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
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "admin")
    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    @Basic
    @Column(name = "create_date")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
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
        User user = (User) o;
        return id == user.id &&
                Objects.equals(nickname, user.nickname) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(admin, user.admin) &&
                Objects.equals(createDate, user.createDate) &&
                Objects.equals(modifiedTime, user.modifiedTime) &&
                Objects.equals(status, user.status) &&
                Objects.equals(isDelete, user.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, username, password, admin, createDate, modifiedTime, status, isDelete);
    }
}

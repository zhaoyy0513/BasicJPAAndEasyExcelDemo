package zhaoyy.poidemo.dto;

import zhaoyy.poidemo.entity.User;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/25 0025 15:37
 */
public class UserDTO {
    private String nickName;
    private Integer roleId;
    private String roleName;
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public UserDTO(String nickName, Integer roleId, String roleName) {
        this.nickName = nickName;
        this.roleId = roleId;
        this.roleName = roleName;
    }
}

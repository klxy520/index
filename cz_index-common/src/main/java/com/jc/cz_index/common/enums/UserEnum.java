package com.jc.cz_index.common.enums;

/*
 * 
 * 使用enum表達常量
 */
public enum UserEnum {

    Admin(1, "管理員"), SYEMentor(2, "SYE導師"), SYEUser(3, "SYE創業者");
    private String roleInfo;
    private int    role;



    private UserEnum(int role, String roleInfo) {
        this.role = role;
        this.roleInfo = roleInfo;
    }



    public String getRoleInfo() {
        return roleInfo;
    }



    public void setRoleInfo(String roleInfo) {
        this.roleInfo = roleInfo;
    }



    public int getRole() {
        return role;
    }



    public void setRole(int role) {
        this.role = role;
    }



    public static UserEnum stateOf(int role) {
        for (UserEnum user : values()) {
            if (user.getRole() == role)
                return user;
        }
        return null;
    }

}

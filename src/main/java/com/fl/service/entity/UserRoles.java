package com.fl.service.entity;

public class UserRoles {
    private Long userId;
    private boolean admin;
    private boolean basic;
    private boolean roleEditor;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isBasic() {
        return basic;
    }

    public void setBasic(boolean basic) {
        this.basic = basic;
    }

    public boolean isRoleEditor() {
        return roleEditor;
    }

    public void setRoleEditor(boolean roleEditor) {
        this.roleEditor = roleEditor;
    }

    @Override
    public String toString() {
        return "UserRoles{" +
                "userId=" + userId +
                ", admin=" + admin +
                ", basic=" + basic +
                ", roleEditor=" + roleEditor +
                '}';
    }
}

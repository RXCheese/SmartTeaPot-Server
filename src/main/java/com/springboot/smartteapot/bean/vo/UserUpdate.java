package com.springboot.smartteapot.bean.vo;
import javax.validation.constraints.*;


public class UserUpdate {

    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]{3,11}$",message = "用户名需要字母开头,长度为4-12位,不包含特殊字符")
    private String username;

    @NotBlank
    @NotEmpty
    @Size(min = 6,max = 12,message = "密码长度为6-12位")
    private String oldPassword;

    @NotEmpty
    @NotBlank
    @Size(min = 6,max = 12,message = "密码长度为6-12位")
    private String newPassword;

    @NotEmpty
    @NotBlank
    @Size(min = 6,max = 12,message = "密码长度为6-12位")
    private String confirmNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserUpdate{" +
                "username='" + username + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmNewPassword='" + confirmNewPassword + '\'' +
                '}';
    }
}

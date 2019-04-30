package com.springboot.smartteapot.bean.vo;
import javax.validation.constraints.*;


public class UserInfo {

    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]{3,11}$",message = "用户名需要字母开头,长度为4-12位,不包含特殊字符")
    private String username;

    @NotBlank
    @NotEmpty
    @Size(min = 6,max = 12,message = "密码长度为6-12位")
    private String password;

    @NotEmpty
    @NotBlank
    @Size(min = 6,max = 12,message = "密码长度为6-12位")
    private String confirmPwd;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^0?(13|14|15|17|18|19)[0-9]{9}$",message = "手机号码格式错误")
    private String phone;

    @NotEmpty
    @NotBlank
    @Email(message = "邮箱格式错误")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPwd='" + confirmPwd + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

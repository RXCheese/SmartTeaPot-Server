package com.springboot.smartteapot.controller;

import com.springboot.smartteapot.bean.vo.UserInfo;
import com.springboot.smartteapot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RegController{

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public String UserRegister(@ModelAttribute("user") @Valid UserInfo userInfo, BindingResult result, Model model) {

        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        String confirmPwd = userInfo.getConfirmPwd();

        if (result.hasErrors()) {
            //System.out.println("注册格式错误");
            return "register";
        }

        if (username.equalsIgnoreCase("root") ||
                username.equalsIgnoreCase("admin") ||
                username.equalsIgnoreCase("test") ||
                username.equalsIgnoreCase("administrator")) {
            result.rejectValue("username", "error.username", "用户名需要字母开头,长度为4-12位,不包含特殊字符");
            System.out.println("用户名格式错误");
            return "register";
        }

        if (adminService.existsByUsername(userInfo.getUsername())) {
            result.rejectValue("username", "error.username", "用户名已被注册");
            System.out.println("用户名已被注册");
            return "register";
        }

        if(!password.equals(confirmPwd))
        {
            result.rejectValue("password", "error.password", "确认密码有误");
            System.out.println("确认密码有误");
            return "register";
        }

        if (adminService.existsByEmail(userInfo.getEmail())) {
            result.rejectValue("email", "error.email", "邮箱已被注册");
            System.out.println("邮箱已被注册");
            return "register";
        }

        if (adminService.existsByPhone(userInfo.getPhone())) {
            result.rejectValue("phone", "error.phone", "手机已被注册");
            System.out.println("手机已被注册");
            return "register";
        }
        adminService.createUser(userInfo);

        model.addAttribute("result","true");
        return "register";
    }

}

package com.springboot.smartteapot.controller;

import com.springboot.smartteapot.bean.Admin;
import com.springboot.smartteapot.bean.vo.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/signIn")
    public String signIn(Model model){
        model.addAttribute("user",new Admin());
        return "signIn";
    }

    @RequestMapping("/logOut")
    public String logOut() {
        return "logOut";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("user",new UserInfo());
        return "register";
    }

    @RequestMapping("/manage")
    public String manage() {
        return "manage";
    }

    @RequestMapping("/")
    public String main(){
        return "manage";
    }

}

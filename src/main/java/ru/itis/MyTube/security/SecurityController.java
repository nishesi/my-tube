package ru.itis.MyTube.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @RequestMapping("/perform_login")
    @ResponseBody
    public String handle1() {
        return "login";
    }
    @RequestMapping("/perform_logout")
    @ResponseBody
    public String handle2() {
        return "logout";
    }
    @RequestMapping("/admin")
    @ResponseBody
    public String handle3() {
        return "admin";
    }
    @RequestMapping("/anonymous")
    @ResponseBody
    public String handle4() {
        return "anonymous";
    }
    @RequestMapping
    @ResponseBody
    public String handle5() {
        return null;
    }

}

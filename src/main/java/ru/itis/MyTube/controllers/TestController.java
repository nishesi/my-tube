package ru.itis.MyTube.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/admin")
    @ResponseBody
    public String handle() {
        return "Hello Admin";
    }

    @GetMapping
    @ResponseBody
    public String handleEmpty() {
        return "Hello anonymous";
    }
}

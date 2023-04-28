package ru.itis.MyTube.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/test")
public class TestController {

    @PutMapping
//    @ResponseBody
    public String post(RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("testAttribute", "redirect attribute");
//        return "success";
        return "redirect:" + "/test";
    }

    @GetMapping
    public String get(ModelMap modelMap, HttpSession session, HttpServletRequest request) {
        return "test";
    }
}

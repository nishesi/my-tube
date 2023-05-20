package ru.itis.nishesi.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    @GetMapping("/ex")
    @ResponseBody
    public List<?> getPage() {
        throw new RuntimeException("tutut");
    }

    @GetMapping
    public String test() {
        return "test";
    }
}

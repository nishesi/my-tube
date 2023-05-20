package ru.itis.nishesi.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.nishesi.MyTube.repositories.VideoRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final VideoRepository videoRepository;
    @GetMapping
    @ResponseBody
    public List<?> getPage() {
        Pageable pageable = PageRequest.of(0, 3);
        var list = videoRepository.getRecommendedVideos(1L, pageable);
        return null;
    }
}

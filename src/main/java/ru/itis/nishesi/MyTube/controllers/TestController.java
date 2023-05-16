package ru.itis.nishesi.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.nishesi.MyTube.repositories.ChannelRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final ChannelRepository channelRepository;

    @GetMapping
    @ResponseBody
    public Page<?> getPage() {
        throw new RuntimeException();
//        Page<?> byAgeCategory = channelRepository.findByAgeCategory(AgeCategory.ADULT, PageRequest.of(0, 20));
//        return byAgeCategory;
    }
}

package ru.itis.MyTube.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.MyTube.dto.UserDto;
import ru.itis.MyTube.dto.ViewDto;
import ru.itis.MyTube.dto.forms.ReactionForm;
import ru.itis.MyTube.services.ViewService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reaction")
public class ReactionRestController {
    private final ViewService viewService;

    @GetMapping("/{videoId}")
    public ViewDto getView(@PathVariable UUID videoId,
                           @SessionAttribute UserDto userDto) {
        return viewService.getView(videoId, userDto);
    }

    @PostMapping
    public ViewDto updateView(
            @Valid @RequestBody ReactionForm reactionForm,
            @SessionAttribute UserDto user) {
        reactionForm.setUserDto(user);
        return viewService.updateView(UUID.fromString(reactionForm.getVideoId()), user, reactionForm.getReaction());
    }
}

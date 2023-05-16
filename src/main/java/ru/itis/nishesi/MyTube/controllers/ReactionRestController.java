package ru.itis.nishesi.MyTube.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.dto.ViewDto;
import ru.itis.nishesi.MyTube.dto.forms.ReactionForm;
import ru.itis.nishesi.MyTube.services.ViewService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reaction")
public class ReactionRestController {
    private final ViewService viewService;

    @GetMapping("/{videoId}")
    public ViewDto getView(@PathVariable UUID videoId,
                           @SessionAttribute UserDto user) {
        return viewService.getView(videoId, user);
    }

    @PostMapping
    public ViewDto updateView(
            @Valid @RequestBody ReactionForm reactionForm,
            @SessionAttribute UserDto user) {
        reactionForm.setUserDto(user);
        return viewService.updateView(UUID.fromString(reactionForm.getVideoId()), user, reactionForm.getReaction());
    }


}

package ru.itis.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.MyTube.exceptions.ValidationException;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.dto.forms.ReactionForm;
import ru.itis.MyTube.services.ReactionService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reaction")
public class ReactionController {
    private final ReactionService reactionService;

    @GetMapping("/{videoId}")
    public ResponseEntity<?> getReaction(@PathVariable String videoId,
                                         @SessionAttribute(required = false) UserDto userDto) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reactionService.getReaction(videoId, userDto));
    }

    @PostMapping("/{videoId}")
    public ResponseEntity<?> addReaction(@PathVariable String videoId,
                                         @SessionAttribute UserDto userDto,
                                         HttpServletRequest req) {
        ReactionForm reactionForm = ReactionForm.builder()
                .videoUuid(videoId)
                .userDto(userDto)
                .reaction(req.getParameter("reaction"))
                .build();
        try {
            reactionService.updateReaction(reactionForm);
            return ResponseEntity.ok().build();
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

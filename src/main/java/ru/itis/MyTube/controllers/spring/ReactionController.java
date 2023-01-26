package ru.itis.MyTube.controllers.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.ReactionForm;
import ru.itis.MyTube.model.services.ReactionService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reaction")
public class ReactionController {
    private final ReactionService reactionService;

    @GetMapping("/{videoId}")
    public ResponseEntity<?> getReaction(@PathVariable String videoId, @SessionAttribute(required = false) User user) {
        ReactionForm reactionForm = ReactionForm.builder()
                .videoUuid(videoId)
                .user(user)
                .build();
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(reactionService.getReaction(reactionForm));

        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{videoId}")
    public ResponseEntity<?> addReaction(@PathVariable String videoId,
                                         @SessionAttribute User user,
                                         HttpServletRequest req) {
        ReactionForm reactionForm = ReactionForm.builder()
                .videoUuid(videoId)
                .user(user)
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

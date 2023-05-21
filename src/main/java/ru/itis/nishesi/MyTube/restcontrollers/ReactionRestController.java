package ru.itis.nishesi.MyTube.restcontrollers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.dto.ViewDto;
import ru.itis.nishesi.MyTube.dto.rest.ReactionForm;
import ru.itis.nishesi.MyTube.exceptions.ContentNotFoundException;
import ru.itis.nishesi.MyTube.restcontrollers.api.ReactionApi;
import ru.itis.nishesi.MyTube.services.ViewService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reaction/v1")
public class ReactionRestController implements ReactionApi {
    private final ViewService viewService;

    @PostMapping
    public ResponseEntity<?> createReaction(@Valid @RequestBody ReactionForm reactionForm,
                                            @SessionAttribute UserDto user) {
        return ResponseEntity.accepted()
                .body(viewService.createView(reactionForm, user));
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<?> getReaction(@PathVariable UUID videoId,
                                         @SessionAttribute UserDto user) {
        try {
            return ResponseEntity.ok(viewService.getView(videoId, user));
        } catch (ContentNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ViewDto updateReaction(@Valid @RequestBody ReactionForm reactionForm,
                                  @SessionAttribute UserDto user) {
        return viewService.updateView(reactionForm, user);
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<?> deleteReaction(@PathVariable UUID videoId,
                                            @SessionAttribute UserDto user) {
        viewService.deleteView(videoId, user);
        return ResponseEntity.accepted().build();
    }
}

package ru.itis.nishesi.MyTube.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.nishesi.MyTube.dto.Alert;
import ru.itis.nishesi.MyTube.dto.AlertsDto;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.dto.forms.comment.NewCommentForm;
import ru.itis.nishesi.MyTube.enums.AlertType;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.services.CommentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class VideoCommentController {
    private final CommentService commentService;

    @GetMapping("/{videoId}/add")
    public String getNewCommentPage(@PathVariable @ModelAttribute String videoId) {
        return "comment/new";
    }

    @PostMapping
    public String addComment(ModelMap modelMap,
                             @Valid NewCommentForm newCommentForm,
                             BindingResult bindingResult,
                             @SessionAttribute UserDto user,
                             RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()) {
            try {
                commentService.AddComment(newCommentForm, user);
                AlertsDto alertsDto = new AlertsDto(Alert.of(AlertType.SUCCESS, "Comment added."));
                redirectAttributes.addFlashAttribute("alerts", alertsDto);
                return "redirect:" + MvcUriComponentsBuilder
                        .fromMappingName("VC#getVideoPage")
                        .arg(1, newCommentForm.getVideoId())
                        .build();

            } catch (ServiceException ex) {
                AlertsDto alertsDto = new AlertsDto(Alert.of(AlertType.WARNING, ex.getMessage()));
                modelMap.put("alerts", alertsDto);
            }
        }
        return "comment/new";
    }
}

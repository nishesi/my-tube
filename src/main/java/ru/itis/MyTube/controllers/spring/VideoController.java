package ru.itis.MyTube.controllers.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.dto.forms.VideoForm;
import ru.itis.MyTube.model.services.UserService;
import ru.itis.MyTube.model.services.VideoService;
import ru.itis.MyTube.view.Alert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import static ru.itis.MyTube.controllers.UrlPatterns.CHANNEL;
import static ru.itis.MyTube.view.Attributes.FORM;

@Controller
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {
    private final VideoService videoService;
    private final UserService userService;
    @Value("${context.path}")
    private String contextPath;

    @GetMapping("/{id}")
    public String getVideo(Model model,
                           @PathVariable String id,
                           @SessionAttribute(required = false) User user) {
        Byte reaction = null;
        try {
            Video video = videoService.getVideo(id);

            if (Objects.nonNull(user)) {
                reaction = userService.getUserReaction(video.getUuid(), user.getUsername());
            }
            List<VideoCover> list = videoService.getRandomVideos();

            model.addAttribute("reaction", reaction);
            model.addAttribute("video", video);
            model.addAttribute("videoCoverList", list);
            model.addAttribute("videoReactionsScriptUrl",
                    contextPath + "/js/ReactionRequester.js");
            return "/jsp/VideoPage";

        } catch (ServiceException ex) {
//            alerts.add(new Alert(Alert.alertType.DANGER, ex.getMessage()));
            return "/jsp/BaseWindow";
        }
    }

    @GetMapping("/update/{id}")
    public String getVideoUpdatePage(Model model, @PathVariable String id) {
        model.addAttribute("pageType", "Update");
        model.addAttribute("url", contextPath + "/update/" + id);
        return "/jsp/UtilVideoPage";
    }

    @PostMapping("/update/{id}")
    public void updateVideo(@PathVariable String id,
                            @SessionAttribute User user,
                            @SessionAttribute(required = false) Queue<? super Alert> alerts,
                            HttpServletRequest req,
                            HttpServletResponse resp
    ) throws ServletException, IOException {

        VideoForm videoForm = VideoForm.builder()
                .videoUuid(id)
                .channelId(user.getChannelId())
                .name(req.getParameter("name"))
                .info(req.getParameter("info"))
                .iconPart(req.getPart("icon"))
                .videoPart(req.getPart("video"))
                .build();

        try {
            videoService.updateVideo(videoForm);
            alerts.add(new Alert(Alert.alertType.SUCCESS, "Video updated."));
            resp.sendRedirect(contextPath + CHANNEL + "?id=" + videoForm.getChannelId());
            return;

        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());
            req.setAttribute(FORM, videoForm);

        } catch (ServiceException ex) {
            alerts.add(new Alert(Alert.alertType.DANGER, ex.getMessage()));
        }

        req.setAttribute("pageType", "Update");
        req.getRequestDispatcher("/WEB-INF/jsp/UtilVideoPage.jsp").forward(req, resp);
    }

    @GetMapping("/add")
    public String  getVideoAddPage(Model model) {
        model.addAttribute("pageType", "Upload");
        return "/jsp/UtilVideoPage";
    }

    @PostMapping
    public void addVideo(@SessionAttribute User user,
                         @SessionAttribute(required = false) Queue<? super Alert> alerts,
                         HttpServletRequest req,
                         HttpServletResponse resp) throws IOException, ServletException {
        VideoForm videoForm = VideoForm.builder()
                .channelId(user.getChannelId())
                .name(req.getParameter("name"))
                .info(req.getParameter("info"))
                .iconPart(req.getPart("icon"))
                .videoPart(req.getPart("video"))
                .build();

        try {
            videoService.addVideo(videoForm);
            alerts.add(new Alert(Alert.alertType.SUCCESS, "Video added."));
            resp.sendRedirect(contextPath + CHANNEL + "?id=" + videoForm.getChannelId());
            return;

        } catch (ValidationException e) {
            req.setAttribute("problems", e.getProblems());
            req.setAttribute(FORM, videoForm);


        } catch (ServiceException ex) {
            alerts.add(new Alert(Alert.alertType.DANGER, ex.getMessage()));
        }
        req.setAttribute("pageType", "Upload");
        req.getRequestDispatcher("/WEB-INF/jsp/UtilVideoPage.jsp").forward(req, resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable String id,
                            @SessionAttribute User user) {
        try {
            Long userChannelId = user.getChannelId();
            videoService.deleteVideo(id, userChannelId);
            return ResponseEntity.ok().build();

        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();

        } catch (ServiceException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

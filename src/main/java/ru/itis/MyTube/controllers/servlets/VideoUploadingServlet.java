package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Attributes;
import ru.itis.MyTube.auxiliary.validators.VideoValidator;
import ru.itis.MyTube.model.forms.VideoForm;
import ru.itis.MyTube.model.services.VideoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/add/video")
@MultipartConfig
public class VideoUploadingServlet extends HttpServlet {
    private VideoService videoService;

    private VideoValidator videoValidator;

    @Override
    public void init() {
        videoService = (VideoService) getServletContext().getAttribute(Attributes.VIDEO_SERVICE.toString());
        videoValidator = new VideoValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/videoUploadingPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VideoForm videoForm = VideoForm.builder()
                .name(req.getParameter("name"))
                .info(req.getParameter("info"))
                .build();

        videoValidator.validate(videoForm);

        Part icon = req.getPart("icon");
        InputStream iconInputStream = icon.getInputStream();

        Part video = req.getPart("video");
        InputStream videoInputStream = video.getInputStream();

        videoService.addVideo(1L, videoForm, videoInputStream, iconInputStream);
    }
}

package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.model.dto.ChannelCover;
import ru.itis.MyTube.model.dto.Video;
import ru.itis.MyTube.model.dto.VideoCover;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/watch")
public class VideoPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VideoCover> list = new ArrayList<>();
        VideoCover videoCover = VideoCover.builder()
                .videoCoverImgUrl("http://localhost:8080/MyTube/images/reg-background-img.jpg")
                .name("bestVideo")
                .channelCover(ChannelCover.builder()
                        .channelImgUrl("http://localhost:8080/MyTube/images/reg-background-img.jpg")
                        .name("bestChannel")
                        .build())
                .addedDate(LocalDateTime.of(2022, 10, 29, 10, 10, 10, 0))
                .views("10100")
                .duration(LocalTime.of(0, 10, 45))
                .build();

        for (int i = 0; i < 15; i++) {
            list.add(videoCover);
        }

        VideoCover baseVideoCover = VideoCover.builder()
                .videoCoverImgUrl("http://localhost:8080/MyTube/images/reg-background-img.jpg")
                .name("играю в майнкрафт очень повезло повезло")
                .channelCover(ChannelCover.builder()
                        .channelImgUrl("http://localhost:8080/MyTube/images/reg-background-img.jpg")
                        .name("minecrafters")
                        .build())
                .addedDate(LocalDateTime.of(2022, 10, 29, 10, 10, 10, 0))
                .views("10100")
                .duration(LocalTime.of(0, 10, 45))
                .build();

        Video video = Video.builder()
                .videoCover(baseVideoCover)
                .info("невероятное везение в майнкрафте такого просто не может " +
                        "быть вы в жизни бы не поверили если бы я не записал.")
                .videoUrl("C:/MyTube/videoStorage/video.mp4")
                .build();

        req.setAttribute("video", video);
        req.setAttribute("videoCoverList", list);
        req.setAttribute("commonCssUrl", getServletContext().getContextPath() + "/css/common.css");

        req.getRequestDispatcher("WEB-INF/jsp/videoPage.jsp").forward(req, resp);
    }
}

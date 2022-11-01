package ru.itis.MyTube.controllers.servlets;

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


@WebServlet("")
public class BaseWindowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VideoCover> list = new ArrayList<>();
        VideoCover videoCover = VideoCover.builder()
                .videoCoverImgUrl("http://localhost:8080/MyTube/images/reg-background-img.jpg")
                .name("bestVideo")
                .channelImgUrl("http://localhost:8080/MyTube/images/reg-background-img.jpg")
                .channelName("bestChannel")
                .addedDate(LocalDateTime.of(2022, 10, 29, 10, 10, 10, 0))
                .views("10100")
                .duration(LocalTime.of(0, 10, 45))
                .build();

        for (int i = 0; i < 15; i++) {
            list.add(videoCover);
        }

        req.setAttribute("videoCoverList", list);
        req.setAttribute("commonCssUrl", getServletContext().getContextPath() + "/css/common.css");

        req.getRequestDispatcher("/WEB-INF/jsp/BaseWindow.jsp").forward(req, resp);
    }
}

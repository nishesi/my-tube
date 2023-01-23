package ru.itis.MyTube.controllers.servlets.util;

import org.springframework.context.ApplicationContext;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.User;
import ru.itis.MyTube.model.dto.forms.ReactionForm;
import ru.itis.MyTube.model.services.ReactionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.itis.MyTube.view.Attributes.USER;
import static ru.itis.MyTube.controllers.UrlPatterns.AUTHENTICATION_PAGE;
import static ru.itis.MyTube.controllers.UrlPatterns.PRIVATE_REACTION;

@WebServlet(PRIVATE_REACTION)
public class ReactionServlet extends HttpServlet {
    private ReactionService reactionService;

    @Override
    public void init() throws ServletException {
        reactionService = ((ApplicationContext)getServletContext().getAttribute("context")).getBean(ReactionService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReactionForm reactionForm = ReactionForm.builder()
                .videoUuid(req.getParameter("videoUuid"))
                .user((User) req.getSession().getAttribute(USER))
                .build();
        String inf = null;

        try {
            inf = reactionService.getReaction(reactionForm);

        } catch (ValidationException e) {
            resp.sendError(400, String.join(", ", e.getProblems().values()));
        }

        resp.setContentType("application/json");
        resp.getWriter().println(inf);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //todo remove redirection
        if (req.getSession().getAttribute(USER) == null) {
            resp.setContentType("application/json");
            resp.getWriter().println(
                    "{\"redirect\":" + true + ", " +
                    "\"location\": \"" + getServletContext().getContextPath() + AUTHENTICATION_PAGE + "\"}");
            return;
        }

        ReactionForm reactionForm = ReactionForm.builder()
                .videoUuid(req.getParameter("videoUuid"))
                .user((User) req.getSession().getAttribute(USER))
                .reaction(req.getParameter("reaction"))
                .build();
        String inf;
        try {
            inf = reactionService.updateReaction(reactionForm);
        } catch (ValidationException e) {
            resp.sendError(400, String.join(", ", e.getProblems().values()));
            return;
        }
        resp.setContentType("application/json");
        resp.getWriter().println(inf);
    }
}

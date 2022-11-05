package ru.itis.MyTube.controllers.servlets;

import ru.itis.MyTube.auxiliary.Attributes;
import ru.itis.MyTube.auxiliary.Type;
import ru.itis.MyTube.auxiliary.exceptions.StorageException;
import ru.itis.MyTube.model.storage.Storage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@WebServlet("/resource")
public class ResourceServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() {
        storage = (Storage) getServletContext().getAttribute(Attributes.STORAGE.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String type = req.getParameter("type");

         if (!validateParameters(id, type, resp)) {
             return;
         }

        Type type1 = null;

        try {
            switch (type) {
                case "v":
                    UUID.fromString(id);
                    type1 = Type.VIDEO;
                    resp.setContentType("video/mp4");
                    break;

                case "vi":
                    UUID.fromString(id);
                    type1 = Type.VIDEO_ICON;
                    resp.setContentType("image/jpg");
                    break;

                case "ci":
                    Long.parseLong(id);
                    type1 = Type.CHANNEL_ICON;
                    resp.setContentType("image/jpg");
                    break;

                case "ui":
                    type1 = Type.USER_ICON;
                    resp.setContentType("image/jpg");
                    break;

                default:
                    resp.sendError(405, "unknown type = " + type);
                    break;
            }
        } catch (IllegalArgumentException ex) {
            resp.sendError(405 , "invalid id = " + id);
            return;
        }



        try (InputStream resource = storage.get(type1, id)) {

            resource.transferTo(resp.getOutputStream());
        } catch (StorageException ex) {

            resp.setStatus(200);
            resp.getWriter().println(ex.getMessage());
        }
    }

    private boolean validateParameters(String type, String id, HttpServletResponse resp) throws IOException {
        if (Objects.isNull(id)) {
            resp.sendError(405, "void id");
            return false;
        } else if (Objects.isNull(type)) {
            resp.sendError(405, "void type");
            return false;
        }
        return true;
    }
}

package ru.itis.MyTube.controllers.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.MyTube.model.storage.FileType;
import ru.itis.MyTube.model.storage.Storage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/resource")
public class ResourceController {
    private final Storage storage;

    @GetMapping("/{type}/{id}")
    public void getResource(@PathVariable String type,
                            @PathVariable String id,
                            HttpServletResponse response
    ) throws IOException {
        FileType fileType = FileType.from(type);
        if (fileType != null) {
            response.setContentType(storage.getMimeType(fileType, id));
            InputStream inputStream = storage.get(fileType, id);
            inputStream.transferTo(response.getOutputStream());
            response.getOutputStream().flush();
        }
    }
}

package ru.itis.MyTube.controllers.validators;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.dto.forms.video.VideoForm;

import jakarta.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Primary
@Component
public class VideoValidator extends AbstractValidator {
    private static final Pattern invalidChars = Pattern.compile("[<>{}\\[\\];]");

    public void validate(VideoForm form) throws ValidationException {
        Map<String, String> problems = new HashMap<>();

        validateChannelId(form.getChannelId(), problems);
        validateName(form.getName(), problems);
        validateInfo(form.getInfo(), problems);
        validateIconPart(form.getIconPart(), problems);
        validateVideoPart(form.getVideoPart(), problems);

        if (!problems.isEmpty()) {
            throw new ValidationException(problems);
        }
    }

    protected void validateChannelId(Long channelId, Map<String, String> problems) {
        if (Objects.isNull(channelId)) {
            problems.put("channelId", "You have not a channel.");
        }
    }

    protected void validateName(String name, Map<String, String> problems) {
        if (Objects.isNull(name) || "".equals(name)) {
            problems.put("name", "Void name.");
        } else if (name.length() <= 5  || name.length() > 70) {
            problems.put("name", "Invalid name length.");
        } else if (invalidChars.matcher(name).find()) {
            problems.put("name", "Name contains invalid characters");
        }
    }
    protected void validateInfo(String info, Map<String, String> problems) {
        if (Objects.isNull(info) || "".equals(info)) {
            problems.put("info", "Void info.");
        } else if (info.length() > 1000) {
            problems.put("info", "Invalid info length.");
        } else if (invalidChars.matcher(info).find()) {
            problems.put("info", "Info contains invalid characters.");
        }
    }

    protected void validateVideoPart(Part videoPart, Map<String, String> problems) {
        if (!"video/mp4".equals(videoPart.getContentType())) {
            problems.put("video", "FileType type should be mp4.");
        } else if (!videoPart.getSubmittedFileName().endsWith(".mp4")) {
            problems.put("video", "FileType type should be mp4. (2)");
        } else if (videoPart.getSize() <= 100) {
            problems.put("video", "FileType size very small.");
        } else if (videoPart.getSize() >= 1000 * 1024 * 1024) {
            problems.put("video", "FileType size very big.");
        }
    }

}

package ru.itis.MyTube.controllers.validators;

import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.util.Map;

@Component
public class VideoUpdateValidator extends VideoValidator {

    @Override
    protected void validateIconPart(Part part,Map<String, String> problems) {
        if (part.getSize() != 0) {
            super.validateIconPart(part, problems);
        }
    }

    @Override
    protected void validateVideoPart(Part part,Map<String, String> problems) {
        if (part.getSize() != 0) {
            super.validateVideoPart(part, problems);
        }
    }
}

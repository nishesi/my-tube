package ru.itis.MyTube.model.dto.forms;

import lombok.Builder;
import lombok.Data;
import ru.itis.MyTube.model.dto.User;

import jakarta.servlet.http.Part;

@Data
@Builder
public class ChannelForm {
    private User user;
    private String name;
    private Part iconPart;
    private String info;
}

package ru.itis.MyTube.dto.forms.channel;

import lombok.Builder;
import lombok.Data;
import ru.itis.MyTube.model.User;

import jakarta.servlet.http.Part;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelForm {
    private User user;
    private String name;
    private Part iconPart;
    private String info;
}

package ru.itis.MyTube.dto.forms.channel;

import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.MyTube.model.User;

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

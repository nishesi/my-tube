package ru.itis.MyTube.dto.forms;

import lombok.Builder;
import lombok.Data;
import ru.itis.MyTube.model.User;

@Data
@Builder
public class SubscribeForm {
    private User user;
    private String toSubscribe;
    private String channelId;
}

package ru.itis.MyTube.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.MyTube.model.UserDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeForm {
    private UserDto userDto;
    private String toSubscribe;
    private String channelId;
}

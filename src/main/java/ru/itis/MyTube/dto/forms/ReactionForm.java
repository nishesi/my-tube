package ru.itis.MyTube.dto.forms;

import lombok.Builder;
import lombok.Data;
import ru.itis.MyTube.model.UserDto;

@Data
@Builder
public class ReactionForm {
    String videoUuid;
    String reaction;
    UserDto userDto;
}

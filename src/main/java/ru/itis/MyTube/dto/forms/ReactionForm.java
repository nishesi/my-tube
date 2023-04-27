package ru.itis.MyTube.dto.forms;

import lombok.Builder;
import lombok.Data;
import ru.itis.MyTube.model.User;

@Data
@Builder
public class ReactionForm {
    String videoUuid;
    String reaction;
    User user;
}

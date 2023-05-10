package ru.itis.MyTube.dto.forms;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import ru.itis.MyTube.dto.UserDto;
import ru.itis.MyTube.enums.Reaction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionForm {
    @UUID
    @NotNull
    private String videoId;

    @NotNull
    private Reaction reaction;

    private UserDto userDto;
}

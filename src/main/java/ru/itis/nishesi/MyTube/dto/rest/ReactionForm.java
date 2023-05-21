package ru.itis.nishesi.MyTube.dto.rest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionForm {
    @UUID
    @NotNull
    private String videoId;

    @Pattern(regexp = "^(LIKE|DISLIKE|NONE)$")
    @NotNull
    private String reaction;
}

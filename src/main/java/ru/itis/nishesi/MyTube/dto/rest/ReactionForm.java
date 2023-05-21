package ru.itis.nishesi.MyTube.dto.rest;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "reaction form")
public class ReactionForm {
    @UUID
    @NotNull
    @Schema(description = "video id", example = "823f86e8-1d99-480d-aa3a-f7c55a6e7742")
    private String videoId;

    @Pattern(regexp = "^(LIKE|DISLIKE|NONE)$")
    @NotNull
    @Schema(description = "reaction", allowableValues = {"LIKE", "DISLIKE", "NONE"})
    private String reaction;
}

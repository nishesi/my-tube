package ru.itis.nishesi.MyTube.dto.forms.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentForm {
    @UUID
    @NotNull
    private String videoId;

    @NotBlank
    @Size(max = 255)
    private String text;
}

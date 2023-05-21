package ru.itis.nishesi.MyTube.dto.rest;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сведения об ошибке")
public class ExceptionDto {
    @Schema(description = "Текст ошибки", example = "Описание ошибки")
    private String message;
    @Schema(description = "HTTP-код ошибки", example = "404 NOT_FOUND")
    private String code;
}

package ru.itis.nishesi.MyTube.dto.rest;


import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {
    private String message;
    private String code;
}

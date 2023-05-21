package ru.itis.nishesi.MyTube.dto;

import ru.itis.nishesi.MyTube.enums.Reaction;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewDto {
    private Long views;
    private Long likes;
    private Long dislikes;
    private Reaction reaction;
}

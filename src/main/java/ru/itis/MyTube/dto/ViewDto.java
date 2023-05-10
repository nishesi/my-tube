package ru.itis.MyTube.dto;

import ru.itis.MyTube.enums.Reaction;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewDto {
    private long views;
    private long likes;
    private long dislikes;
    private Reaction reaction;
}

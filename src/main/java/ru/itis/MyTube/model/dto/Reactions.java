package ru.itis.MyTube.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Reactions {
    private long likes;
    private long dislikes;
    private byte reaction;
}

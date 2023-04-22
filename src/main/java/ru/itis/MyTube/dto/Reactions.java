package ru.itis.MyTube.dto;

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

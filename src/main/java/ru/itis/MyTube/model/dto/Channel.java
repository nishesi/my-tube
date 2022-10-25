package ru.itis.MyTube.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@Data
public class Channel {
    private Long id;
    private User owner;
    private Long countOfSubscribers;
    private String info;
    private List<Long> VideoCoverIdList;
}

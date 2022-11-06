package ru.itis.MyTube.model.forms;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VideoForm {
    private String name;
    private String info;
}

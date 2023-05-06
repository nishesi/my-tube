package ru.itis.MyTube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import ru.itis.MyTube.model.VideoCover;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDto {
    private Long id;
    private String name;
    private Long countOfSubscribers;
    private String info;
    private String iconUrl;
    private Page<VideoCover> videosPage;
    private boolean isSubscribed;
}

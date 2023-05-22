package ru.itis.nishesi.MyTube.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String userImgUrl;
    private String userFirstName;
    private String userLastName;
    private String text;
    private ZonedDateTime addedDate;
}

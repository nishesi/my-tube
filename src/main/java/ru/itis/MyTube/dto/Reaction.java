package ru.itis.MyTube.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Reaction {
    UUID videoUuid;
    String username;
    Byte reaction;
}

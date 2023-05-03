package ru.itis.MyTube.dto.forms.video;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.MyTube.validation.FileSize;
import ru.itis.MyTube.validation.FileType;
import ru.itis.MyTube.validation.NullOrNotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVideoForm {
    @UUID(message = "Invalid video id.")
    private String uuid;

    @NullOrNotBlank
    @Size(max = 70)
    private String name;

    @NullOrNotBlank
    @Size(max = 2000)
    private String info;

    @FileSize(max = 10*1024*1024)
    @FileType(acceptableTypes = {"image/jpeg", "image/png"})
    private MultipartFile iconFile;
}

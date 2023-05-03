package ru.itis.MyTube.dto.forms.video;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.MyTube.validation.FileSize;
import ru.itis.MyTube.validation.FileType;
import ru.itis.MyTube.validation.NotEmptyFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewVideoForm {
    @NotBlank
    @Size(max = 70)
    private String name;

    @NotBlank
    @Size(max = 2000)
    private String info;

    @NotEmptyFile
    @FileSize(max = 10*1024*1024)
    @FileType(acceptableTypes = {"image/jpeg", "image/png"})
    private MultipartFile iconFile;

    @NotEmptyFile
    @FileSize(max = 1024*1024*1024)
    @FileType(acceptableTypes = {"video/mp4"})
    private MultipartFile videoFile;
}

package ru.itis.nishesi.MyTube.dto.forms.channel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.nishesi.MyTube.validation.constraints.FileSize;
import ru.itis.nishesi.MyTube.validation.constraints.NotEmptyFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewChannelForm {
    @NotBlank
    @Size(max = 20)
    private String name;

    @NotEmptyFile
    @FileSize(max = 10 * 1024 * 1024 * 8)
    private MultipartFile iconFile;

    @NotBlank
    @Size(max = 2000)
    private String info;
}

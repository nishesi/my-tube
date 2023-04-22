package ru.itis.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.MyTube.storage.FileType;
import ru.itis.MyTube.storage.Storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequiredArgsConstructor
@RequestMapping("/resource")
public class ResourceController {
    private final Storage storage;

    @GetMapping("/{type}/{id}")
    public ResponseEntity<byte[]> getResource(@PathVariable String type,
                                              @PathVariable String id) throws IOException {
        try {
            var file = storage.getFile(FileType.from(type), id);
            var mediaType = MediaType.valueOf(Files.probeContentType(file.toPath()));

            try (var input = new FileInputStream(file)) {
                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .body(input.readAllBytes());
            }

        } catch (FileType.UnknownFileTypeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}

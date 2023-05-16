package ru.itis.nishesi.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.nishesi.MyTube.enums.FileType;
import ru.itis.nishesi.MyTube.services.FileService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequiredArgsConstructor
@RequestMapping("/resource")
public class ResourceController {
    private final FileService fileService;

    @GetMapping("/{type}/{id}")
    public ResponseEntity<byte[]> getResource(@PathVariable String type,
                                              @PathVariable String id) throws IOException {
        try {
            var file = fileService.getFile(FileType.from(type), id);
            var mediaType = MediaType.valueOf(Files.probeContentType(file.toPath()));

            try (var input = new FileInputStream(file)) {
                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .body(input.readAllBytes());
            } catch (FileNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (FileType.UnknownFileTypeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
package ru.itis.nishesi.MyTube.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.nishesi.MyTube.dto.ByteResult;
import ru.itis.nishesi.MyTube.enums.FileType;
import ru.itis.nishesi.MyTube.services.FileService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/resource")
public class ResourceController {
    private final FileService fileService;

    @ExceptionHandler({
            FileType.UnknownFileTypeException.class,
            FileNotFoundException.class
    })
    public ResponseEntity<?> handle() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException() {
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/{type}/{id}")
    public ResponseEntity<byte[]> getResource(@PathVariable String type,
                                              @PathVariable String id,
                                              @RequestHeader(required = false, value = "Range") String range
    ) throws IOException {

        File file = fileService.getFile(FileType.from(type), id);
        ByteResult result = fileService.getPartialFile(file, range);

        HttpStatus httpStatus = (result.end() < file.length() - 1) ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK;

        log.warn("File name:" + file.getName() + "; start: " + result.start() + "; end: " + result.end());
        return ResponseEntity.status(httpStatus)
                .header("Content-Type", Files.probeContentType(file.toPath()))
                .header("Content-Length", String.valueOf(result.arr().length))
                .header("Content-Range", "bytes " + result.start() + "-" + result.end() + "/" + file.length())
                .body(result.arr());
    }
}

package ru.itis.MyTube.services;

import ru.itis.MyTube.enums.FileType;

import java.io.File;
import java.io.InputStream;


public interface FileService {
    InputStream get(FileType fileType, String id);
    File getFile(FileType fileType, String id);

    void save(FileType fileType, String id, InputStream inputStream);
    void delete(FileType fileType, String id);
}

package ru.itis.nishesi.MyTube.services;

import ru.itis.nishesi.MyTube.enums.FileType;

import java.io.File;
import java.io.InputStream;


public interface FileService {
    void save(FileType fileType, String id, InputStream inputStream);
    File getFile(FileType fileType, String id);

    void delete(FileType fileType, String id);
    InputStream get(FileType fileType, String id);
}
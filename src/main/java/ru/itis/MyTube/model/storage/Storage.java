package ru.itis.MyTube.model.storage;

import java.io.File;
import java.io.InputStream;


public interface Storage {
    InputStream get(FileType fileType, String id);
    File getFile(FileType fileType, String id);

    void save(FileType fileType, String id, InputStream inputStream);
    void delete(FileType fileType, String id);
}

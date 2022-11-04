package ru.itis.MyTube.model.storage;

import ru.itis.MyTube.auxiliary.Type;
import ru.itis.MyTube.auxiliary.exceptions.StorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStorageImpl implements Storage {

    private static final String VIDEO_TYPE = ".mp4";
    private static final String PHOTO_TYPE = ".jpg";

    private static final Path REPOSITORY = Paths.get("D://MyTube");

    private static final Path VIDEO_STORAGE = Paths.get("videoStorage");
    private static final Path VIDEO_ICON_STORAGE = Paths.get("imageStorage/videoIcons");
    private static final Path CHANNEL_ICON_STORAGE = Paths.get("imageStorage/channelIcons");
    private static final Path USER_ICON_STORAGE = Paths.get("imageStorage/userIcons");

    @Override
    public InputStream get(Type type, String name) {
        if (type == null || name == null) {
            throw new StorageException("type" + type + ", name" + name);
        }

        File file = null;

        switch (type) {
            case VIDEO:
                file = REPOSITORY.resolve(VIDEO_STORAGE).resolve(name + VIDEO_TYPE).toFile();
                break;
            case VIDEO_ICON:
                file = REPOSITORY.resolve(VIDEO_ICON_STORAGE).resolve(name + PHOTO_TYPE).toFile();
                break;
            case CHANNEL_ICON:
                file = REPOSITORY.resolve(CHANNEL_ICON_STORAGE).resolve(name + PHOTO_TYPE).toFile();
                break;
            case USER_ICON:
                file = REPOSITORY.resolve(USER_ICON_STORAGE).resolve(name + PHOTO_TYPE).toFile();
                break;
        }

        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new StorageException("file not found");
        }
    }
}

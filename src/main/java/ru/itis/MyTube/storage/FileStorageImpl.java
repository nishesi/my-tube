package ru.itis.MyTube.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.enums.FileType;
import ru.itis.MyTube.exceptions.StorageException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class FileStorageImpl implements Storage {

    private static final String VIDEO_TYPE = ".mp4";
    private static final String IMAGE_TYPE = ".jpg";
    private final Path VIDEO_STORAGE = Paths.get("videoStorage");
    private final Path VIDEO_ICON_STORAGE = Paths.get("imageStorage/videoIcons");
    private final Path CHANNEL_ICON_STORAGE = Paths.get("imageStorage/channelIcons");
    private final Path USER_ICON_STORAGE = Paths.get("imageStorage/userIcons");
    private final Path REPOSITORY;
    private final File USER_DEFAULT_ICON;
    private final File CHANNEL_DEFAULT_ICON;

    public FileStorageImpl(@Value("${context.repository}") String path) {
        REPOSITORY = Paths.get(path);

        USER_DEFAULT_ICON = REPOSITORY
                .resolve(USER_ICON_STORAGE)
                .resolve("default" + IMAGE_TYPE).toFile();

        CHANNEL_DEFAULT_ICON = REPOSITORY
                .resolve(CHANNEL_ICON_STORAGE)
                .resolve("default" + IMAGE_TYPE).toFile();
    }
    @Override
    public InputStream get(FileType fileType, String id) {
        validateParams(fileType, id);

        File file = getFile(fileType, id, false);

        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new StorageException("file not found");
        }
    }

    @Override
    public File getFile(FileType fileType, String id) {
        validateParams(fileType, id);
        return getFile(fileType, id, false);
    }

    private static void validateParams(FileType fileType, String id) {
        if (fileType == null || id == null) {
            throw new StorageException("fileType" + fileType + ", id" + id);
        }
    }

    @Override
    public void save(FileType fileType, String id, InputStream resource) {
        validateParams(fileType, id);
        if (resource == null) {
            throw new StorageException("name = null");
        }

        File file = getFile(fileType, id, true);

        try {
            file.createNewFile();
            OutputStream outputStream = Files.newOutputStream(file.toPath());
            resource.transferTo(outputStream);

            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException("internal problem");
        }
    }

    @Override
    public void delete(FileType fileType, String id) {
        if (Objects.isNull(fileType) || Objects.isNull(id)) {
            throw new StorageException("fileType = " + fileType + ", id = " + id);
        }
        File file = getFile(fileType, id, true);

        file.delete();
    }

    private File getFile(FileType fileType, String id, boolean real) {

        switch (fileType) {
            case VIDEO:
                return REPOSITORY.resolve(VIDEO_STORAGE).resolve(id + VIDEO_TYPE).toFile();
            case VIDEO_ICON:
                return REPOSITORY.resolve(VIDEO_ICON_STORAGE).resolve(id + IMAGE_TYPE).toFile();
            case CHANNEL_ICON:
                File file = REPOSITORY.resolve(CHANNEL_ICON_STORAGE).resolve(id + IMAGE_TYPE).toFile();
                return (file.exists() || real) ? file : CHANNEL_DEFAULT_ICON;
            case USER_ICON:
                File icon = REPOSITORY.resolve(USER_ICON_STORAGE).resolve(id + IMAGE_TYPE).toFile();
                return (icon.exists() || real) ? icon : USER_DEFAULT_ICON;
            default:
                throw new StorageException("unknown fileType");
        }
    }
}

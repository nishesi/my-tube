package ru.itis.MyTube.model.storage;

import ru.itis.MyTube.auxiliary.enums.FileType;
import ru.itis.MyTube.auxiliary.exceptions.StorageException;

import java.io.*;
import java.nio.file.Files;
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

    private static final File DEFAULT_USER_ICON = REPOSITORY.resolve(VIDEO_STORAGE).resolve("default" + VIDEO_TYPE).toFile();

    @Override
    public InputStream get(FileType fileType, String id) {
        if (fileType == null || id == null) {
            throw new StorageException("fileType" + fileType + ", id" + id);
        }

        File file = getFile(fileType, id);

        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new StorageException("file not found");
        }
    }

    @Override
    public void save(FileType fileType, String id, InputStream resource) {
        if (fileType == null || id == null || resource == null) {
            throw new StorageException("fileType = " + fileType + ", id = " + id + ", name = " + resource);
        }

        File file = getFile(fileType, id);

        try  {
            file.createNewFile();
            OutputStream outputStream = Files.newOutputStream(file.toPath());
            resource.transferTo(outputStream);

            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private File getFile(FileType fileType, String id) {

        switch (fileType) {
            case VIDEO:
                return REPOSITORY.resolve(VIDEO_STORAGE).resolve(id + VIDEO_TYPE).toFile();
            case VIDEO_ICON:
                return REPOSITORY.resolve(VIDEO_ICON_STORAGE).resolve(id + PHOTO_TYPE).toFile();
            case CHANNEL_ICON:
                return REPOSITORY.resolve(CHANNEL_ICON_STORAGE).resolve(id + PHOTO_TYPE).toFile();
            case USER_ICON:
                File icon = REPOSITORY.resolve(USER_ICON_STORAGE).resolve(id + PHOTO_TYPE).toFile();
                return (icon.exists()) ? icon : DEFAULT_USER_ICON;
            default:
                throw new RuntimeException("unknown fileType");
        }
    }
}

package ru.itis.MyTube.model.storage;

import ru.itis.MyTube.auxiliary.Type;
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

    @Override
    public InputStream get(Type type, String id) {
        if (type == null || id == null) {
            throw new StorageException("type" + type + ", id" + id);
        }

        File file = getFile(type, id);

        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new StorageException("file not found");
        }
    }

    @Override
    public void save(Type type, String id, InputStream resource) {
        if (type == null || id == null || resource == null) {
            throw new StorageException("type = " + type + ", id = " + id + ", name = " + resource);
        }

        File file = getFile(type, id);

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

    private File getFile(Type type, String id) {

        switch (type) {
            case VIDEO:
                return REPOSITORY.resolve(VIDEO_STORAGE).resolve(id + VIDEO_TYPE).toFile();
            case VIDEO_ICON:
                return REPOSITORY.resolve(VIDEO_ICON_STORAGE).resolve(id + PHOTO_TYPE).toFile();
            case CHANNEL_ICON:
                return REPOSITORY.resolve(CHANNEL_ICON_STORAGE).resolve(id + PHOTO_TYPE).toFile();
            case USER_ICON:
                return REPOSITORY.resolve(USER_ICON_STORAGE).resolve(id + PHOTO_TYPE).toFile();
            default:
                throw new RuntimeException("unknown type");
        }
    }
}

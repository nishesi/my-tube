package ru.itis.MyTube.model.storage;

import java.io.InputStream;
import ru.itis.MyTube.auxiliary.Type;

public interface Storage {
    InputStream get(Type type, String name);
}

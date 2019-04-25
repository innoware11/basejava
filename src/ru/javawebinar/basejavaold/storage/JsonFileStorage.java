package ru.javawebinar.basejavaold.storage;

import ru.javawebinar.basejavaold.model.Resume;
import ru.javawebinar.basejavaold.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonFileStorage extends FileStorage {

    public JsonFileStorage(String path) {
        super(path);
    }

    @Override
    protected void write(OutputStream os, Resume r) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)){
            JsonParser.write(r, writer);
        }
    }

    @Override
    protected Resume read(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)){
            return JsonParser.read(reader, Resume.class);
        }
    }

    @Override
    public boolean isSectionSupported() {
        return false;
    }
}

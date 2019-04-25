package ru.javawebinar.basejavaold.util;


import com.google.gson.Gson;

import java.io.Reader;
import java.io.Writer;


public class JsonParser {
    private static Gson GSON = new Gson();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }
}

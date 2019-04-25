package ru.javawebinar.basejavaold.storage;

import ru.javawebinar.basejavaold.WebAppException;
import ru.javawebinar.basejavaold.model.Resume;

import java.io.*;

public class SerializeFileStorage extends FileStorage {


    public SerializeFileStorage(String path) {
        super(path);
    }
    @Override
    protected void write(OutputStream os, Resume r) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
           oos.writeObject(r);
        }
    }
    @Override
    protected Resume read(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return  (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new WebAppException("Error read resume", e);
        }
    }
}

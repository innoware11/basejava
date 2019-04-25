package ru.javawebinar.basejavaold.storage;

import ru.javawebinar.basejavaold.model.Resume;

import java.util.Collection;

public interface IStorage {
    void clear();

    void save(Resume r) ;

    void update(Resume r);

    Resume load(String uuid);

    void delete(String uuid);

    Collection<Resume> getAllSorted();

    int size();

    boolean isSectionSupported();
}

package ru.javawebinar.basejavaold.storage;

import ru.javawebinar.basejavaold.model.Resume;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Doing -  сделать реализацию аналогично ArrayStirage на основе коллекции Map
public class ConcurrencyMapStorage extends AbstractStorage<String> {
    private Map<String, Resume> map = new ConcurrentHashMap<>();


    @Override
    public void doClear() {
        map.clear();
    }

    @Override
    protected void doSave(String uuid, Resume r) {
        map.put(uuid, r);
    }

    @Override
    protected void doUpdate(String uuid, Resume r) {
        doSave(uuid, r);
    }

    @Override
    protected Resume doLoad(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doDelete(String uuid) {
        map.remove(uuid);
    }

    @Override
    public List<Resume> doGetAllSorted() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }


    @Override
    protected boolean isExist(String uuid) {
        return map.containsKey(uuid);
    }

    @Override
    protected String getContext(String uuid) {
        return uuid;
    }
}

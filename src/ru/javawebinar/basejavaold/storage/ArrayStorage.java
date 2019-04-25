package ru.javawebinar.basejavaold.storage;


import ru.javawebinar.basejavaold.model.Resume;

import java.util.Arrays;
import java.util.List;

public class ArrayStorage extends AbstractStorage<Integer> {
    private static final int LIMIT = 100;
    private Resume[] array = new Resume[LIMIT];
    private int size = 0;
    //protected Logger logger = Logger.getLogger(getClass().getName());
    //private static Logger logger = Logger.getLogger(ArrayStorage.class.getName());

    @Override
    public void doClear() {
        Arrays.fill(array, 0, size, null);
        size = 0;
    }

    @Override
    protected void doSave(Integer ctx, Resume r) {
        array[size()] = r;
        size++;
    }

    @Override
    protected void doUpdate(Integer idx, Resume r) {
        array[idx] = r;
    }

    @Override
    protected Resume doLoad(Integer idx) {
        return array[idx];
    }

    @Override
    protected void doDelete(Integer idx) {
        int numMoved = size - idx - 1;
        if (numMoved > 0) {
            System.arraycopy(array, idx + 1, array, idx, numMoved);
        }
        array[--size] = null; //Clear
    }

    @Override
    public List<Resume> doGetAllSorted() {
        return Arrays.asList(Arrays.copyOf(array, size));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Integer getContext(String uuid) {
        for (int i = 0; i < size; i++) {
            if (array[i].getUuid().equals(uuid))
                return i;
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer idx) {
        return idx != -1;
    }
}

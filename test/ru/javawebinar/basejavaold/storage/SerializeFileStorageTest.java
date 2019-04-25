package ru.javawebinar.basejavaold.storage;


public class SerializeFileStorageTest extends AbtractStorageTest {
    {
        storage = new SerializeFileStorage(AbtractStorageTest.FILE_STORAGE);
    }
}

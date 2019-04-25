package ru.javawebinar.basejavaold.storage;

import static org.junit.Assert.*;

public class JsonFileStorageTest extends AbtractStorageTest {
    {
        storage = new JsonFileStorage(AbtractStorageTest.FILE_STORAGE);
    }
}
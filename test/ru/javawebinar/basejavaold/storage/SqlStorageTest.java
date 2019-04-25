package ru.javawebinar.basejavaold.storage;

import ru.javawebinar.basejavaold.WebAppConfig;

public class SqlStorageTest extends AbtractStorageTest{
    {
        storage = WebAppConfig.get().getStorage();
    }
}
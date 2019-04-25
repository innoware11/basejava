package ru.javawebinar.basejavaold.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ArrayStorageTest.class, MapStorageTest.class,
        DataStreamFileStorageTest.class, SerializeFileStorageTest.class,
        XmlFileStorageTest.class, JsonFileStorageTest.class,
        SqlStorageTest.class})
public class AllStorageTests {

}
package ru.javawebinar.basejavaold.storage;

import org.junit.Test;
import ru.javawebinar.basejavaold.WebAppConfig;

import java.time.LocalDate;
import java.time.Month;

public class SqlDateTest {
    @Test
    public void testInsertDate() throws Exception {
        SqlStorage storage = (SqlStorage) WebAppConfig.get().getStorage();
        storage.insertDate(LocalDate.now(), LocalDate.of(2015, Month.FEBRUARY, 23));
    }
}
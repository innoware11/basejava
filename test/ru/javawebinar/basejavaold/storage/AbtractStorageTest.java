package ru.javawebinar.basejavaold.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejavaold.WebAppException;
import ru.javawebinar.basejavaold.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public abstract class AbtractStorageTest {
    public static final String FILE_STORAGE = "./file_storage";
    // Doing -  вынести общий код из ArrayStorageTest и MapStorageTest
    protected Resume R1, R2, R3;
    IStorage storage;

    @Before
    public void before() {
        R1 = new Resume("Кислин Григорий", "Россия, г. Санкт-Петербург");
        R1.addContact(ContactType.PHONE, "+7 (921) 855 0482");
        R1.addContact(ContactType.MAIL, "mgkislin@yandex.ru");
        R1.addContact(ContactType.SKYPE, "grigory.kislin");
        R2 = new Resume("Полное имя2", "");
        R2.addContact(ContactType.SKYPE, "skype2");
        R2.addContact(ContactType.PHONE, "222222");
        R3 = new Resume("Полное имя3", "");
        if (storage.isSectionSupported()) {
            R1.addObjective("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям.");
            String ach1 = "С 2013 года: разработка проектов \"Практика Java, разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. " +
                    "Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\" и проведение по ним стажировок и корпоративных обучений. Более 1000 выпускников.";
            String ach2 = "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.";
            R1.addMultiTextSection(SectionType.ACHIEVEMENT, ach1, ach2);
            String qual1  = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2";
            String qual2  = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";

            R1.addMultiTextSection(SectionType.QUALIFICATIONS, qual1, qual2);
            Organization org1 = new Organization("Java Online Projects", "http://javaops.ru/",
                    new Organization.Period(LocalDate.of(2013, Month.OCTOBER, 1), Organization.Period.NOW, "Автор проекта Java Online Projects", "Практика: Разработка Web приложения \"База данных резюме\"\n" +
                            "Объектная модель, коллекции, система ввода-вывода, работа с файлами, сериализация, работа с XML, JSON, SQL, персистентность в базу данных (PostgreSQL), сервлеты, JSP/JSTL, веб-контейнер Tomcat, HTML, модульные тесты JUnit, " +
                            "java.util.Logging, система контроля версий Git."));
            Organization org2 = new Organization("Wrike", "http://wrike.com/", new Organization.Period(2014, Month.OCTOBER,2016, Month.JANUARY, "Старший разработчик (backend)",
                    "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
            R1.addOrganizationSection(SectionType.EXPERIENCE, org1, org2);
            R1.addOrganizationSection(SectionType.EDUCATION,
                    new Organization("Institute", null,
                            new Organization.Period(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                            new Organization.Period(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                    new Organization("Organization12", "http://Organization12.ru"));
        }
        storage.clear();
        storage.save(R3);
        storage.save(R1);
        storage.save(R2);
        //Doing -  add sections
    }


    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume R4 = new Resume("Полное имя4", "");
        storage.save(R4);
        assertEquals(4, storage.size());
        assertEquals(R4, storage.load(R4.getUuid()));
    }

    @Test
    public void update() {
        R2.setFullName("Updated R2");
        storage.update(R2);
        assertEquals(R2, storage.load(R2.getUuid()));
    }

    @Test
    public void load() {
        Resume load = storage.load(R1.getUuid());
        assertEquals(R1, load);
        assertEquals(R2, storage.load(R2.getUuid()));
        assertEquals(R3, storage.load(R3.getUuid()));
    }

    @Test(expected = WebAppException.class)
    public void deleteNotFound() {
        storage.load("dummy");
    }

    @Test
    public void delete() {
        storage.delete(R1.getUuid());
        assertEquals(2, storage.size());
    }

    @Test(expected = WebAppException.class)
    public void testDeleteMissed() {
        storage.delete("dummy");
    }

    @Test(expected = WebAppException.class)
    public void testSavePresented() {
        storage.save(R1);
    }

    @Test(expected = WebAppException.class)
    public void testUpdateMissed() {
        storage.update(new Resume("dummy", "fullname_U1", "location_U1"));
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = Arrays.asList(R1, R2, R3);
        Collections.sort(list);
        assertEquals(list, storage.getAllSorted());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}

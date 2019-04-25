package ru.javawebinar.basejavaold.web;

import ru.javawebinar.basejavaold.model.ContactType;
import ru.javawebinar.basejavaold.model.Organization;
import ru.javawebinar.basejavaold.model.Resume;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class HtmlUtil {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static String mask(String str) {
        return (str == null || str.isEmpty()) ? "&nbsp;" : str;
    }

    public static String getContact(Resume r, ContactType type) {
        String contact = r.getContact(type);
        return contact == null ? "&nbsp;" : type.toHtml(contact);
    }

    public static String format(LocalDate date) {
        return date.equals(Organization.Period.NOW) ? "Сейчас" : date.format(DATE_FORMATTER);
    }

    public static String textArea(String name, List<String> list) {
        return String.format("<textarea name=%s cols=75 rows=5>%s</textarea>", name, String.join("\n", list));
    }

    public static String input(String name, String value) {
        return String.format("<input type='text' name='%s' size=75 value='%s'>", name, value);
    }
}

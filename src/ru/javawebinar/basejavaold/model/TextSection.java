package ru.javawebinar.basejavaold.model;

import java.util.Objects;

public class TextSection extends Section {
    static final long serialVersionUID = 1L;
    private String value;


    public TextSection() {
        this("");
    }

    public TextSection(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return Objects.equals(value, that.value);

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

}

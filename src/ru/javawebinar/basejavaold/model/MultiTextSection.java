package ru.javawebinar.basejavaold.model;


import javax.xml.bind.annotation.XmlElement;
import java.util.*;

public class MultiTextSection extends Section {
    static final long serialVersionUID = 1L;
    public static final MultiTextSection EMPTY = new MultiTextSection("");

    private List<String> items;

    public MultiTextSection() {
    }

    public MultiTextSection(String... items) {
        this(Arrays.asList(items));
    }

    public MultiTextSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultiTextSection that = (MultiTextSection) o;

        return items.equals(that.items);

    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}

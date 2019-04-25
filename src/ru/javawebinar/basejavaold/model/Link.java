package ru.javawebinar.basejavaold.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    static final long serialVersionUID = 1L;
    public static Link EMPTY = new Link();
    private String name;
    private String url;

    public Link() {
        this("", "");
    }

    public Link(Link link) {
        this(link.name, link.url);
    }


    public Link(String name, String url) {
        Objects.requireNonNull(name, "name is null");
        this.name = name;
        this.url = url == null ? "" : url;
    }

    public static Link empty() {
        return EMPTY;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!name.equals(link.name)) return false;
        return Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

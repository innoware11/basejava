package ru.javawebinar.basejavaold.storage;

import ru.javawebinar.basejavaold.model.*;
import ru.javawebinar.basejavaold.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlFileStorage extends FileStorage {
    private XmlParser xmlParser;

    public XmlFileStorage(String path) {
        super(path);
        xmlParser = new XmlParser(Resume.class, Organization.class, Link.class,
                OrganizationSection.class, TextSection.class, MultiTextSection.class, Organization.Period.class);
    }

    @Override
    protected void write(OutputStream os, Resume r) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, writer);
        }
    }

    @Override
    protected Resume read(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)){
            return xmlParser.unmarshall(reader);
        }
    }

    @Override
    public boolean isSectionSupported() {
        return true;
    }
}

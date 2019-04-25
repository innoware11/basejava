package ru.javawebinar.basejavaold.web;

import ru.javawebinar.basejavaold.model.MultiTextSection;
import ru.javawebinar.basejavaold.model.Section;
import ru.javawebinar.basejavaold.model.SectionType;
import ru.javawebinar.basejavaold.model.TextSection;

import java.util.Collections;

import static ru.javawebinar.basejavaold.web.HtmlUtil.input;
import static ru.javawebinar.basejavaold.web.HtmlUtil.textArea;


public enum SectionHtmlType {
    TEXT {
        @Override
        public String toHtml(Section section, SectionType type) {
            return input(type.name(), section == null ? "" : ((TextSection) section).getValue());
        }

        @Override
        public TextSection createSection(String value) {
            return new TextSection(value);
        }
    },
    MULTI_TEXT {
        @Override
        public String toHtml(Section section, SectionType type) {
            return textArea(type.name(), section == null ? Collections.singletonList("") :((MultiTextSection) section).getItems());
        }

        @Override
        public MultiTextSection createSection(String values) {
            return new MultiTextSection(values.split("\\n"));
        }
    },
    ORGANIZATION {
        @Override
        public String toHtml(Section section, SectionType type) {
            return section == null ? "": section.toString();
        }

        @Override
        public Section createSection(String value) {
            throw new UnsupportedOperationException();
        }
    };

    public abstract String toHtml(Section section, SectionType type);

    public abstract Section createSection(String value);
}

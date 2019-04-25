package ru.javawebinar.basejavaold;

import ru.javawebinar.basejavaold.model.Resume;

public class WebAppException extends RuntimeException {
    private Resume resume = null;
    private String uuid = null;

    public WebAppException(String message) {
        super(message);
    }

    public WebAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebAppException(String message, Resume resume) {
        super(message);
        this.resume = resume;
    }

    public WebAppException(String message, Resume resume, Throwable cause) {
        super(message, cause);
        this.resume = resume;
    }

    public WebAppException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }


    public Resume getResume() {
        return resume;
    }

    public String getUuid() {
        return uuid;
    }
}

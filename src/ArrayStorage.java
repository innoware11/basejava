import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        if (get(r.uuid) == null)
            storage[size()] = r;
    }

    Resume get(String uuid) {
        return Arrays.stream(storage).filter(Objects::nonNull).filter(s -> s.uuid.equals(uuid)).findFirst().orElse(null);
    }

    void delete(String uuid) {
        storage = Arrays.stream(storage).filter(Objects::nonNull).filter(s -> !s.uuid.equals(uuid)).toArray(Resume[]::new);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.stream(storage).filter(Objects::nonNull).toArray(Resume[]::new);
    }

    int size() {
        return (int) Arrays.stream(storage).filter(Objects::nonNull).count();
    }

    void update(Resume resume) {
        storage = Arrays.stream(storage).map(s -> {
            if (s.equals(resume)) return resume;
            else return s;
        }).toArray(Resume[]::new);
    }
}


package ru.javawebinar.basejavaold.storage;

import ru.javawebinar.basejavaold.WebAppException;
import ru.javawebinar.basejavaold.model.Resume;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

//Doing -  вынести в AbstractStorage по максимуму общий код из MapStorage и ArrayStorage
public abstract class AbstractStorage<C> implements IStorage {
    protected final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void clear() {
        logger.info("Delete all resumes.");
        doClear();
    }

    protected abstract void doClear();

    protected abstract C getContext(String uuid);

    protected abstract boolean isExist(C ctx);

    @Override
    public void save(Resume r) {
        logger.info("Save resume with uuid=" + r.getUuid());
        C ctx = getContext(r);
        if (isExist(ctx)) {
            throw new WebAppException("Resume " + r.getUuid() + " is already isExist", r);
        }
        doSave(ctx, r);
    }

    protected abstract void doSave(C ctx, Resume r);

    @Override
    public void update(Resume r) {
        logger.info("Update resume with " + r.getUuid());
        C ctx = getContext(r);
        if (!isExist(ctx)) {
            throw new WebAppException("Resume " + r.getUuid() + " is not isExist", r);
        }
        doUpdate(ctx, r);
    }

    protected abstract void doUpdate(C ctx, Resume r);

    @Override
    public Resume load(String uuid) {
        logger.info("Load resume with uuid=" + uuid);
        C ctx = getContext(uuid);
        if (!isExist(ctx)) {
            throw new WebAppException("Resume " + uuid + " is not isExist");
        }
        return doLoad(ctx);
    }

    protected abstract Resume doLoad(C ctx);

    @Override
    public void delete(String uuid) {
        logger.info("Delete resume with uuid=" + uuid);
        C ctx = getContext(uuid);
        if (!isExist(ctx)) {
            throw new WebAppException("Resume " + uuid + " is not isExist", uuid);
        }
        doDelete(ctx);
    }

    protected abstract void doDelete(C ctx);

    @Override
    public Collection<Resume> getAllSorted() {
        logger.info("getAllSorted");
        List<Resume> list = doGetAllSorted();
        Collections.sort(list, new Comparator<Resume>() {
            @Override
            public int compare(Resume o1, Resume o2) {
                int cmp = o1.getFullName().compareTo(o2.getFullName());
                if (cmp != 0) return cmp;
                return o1.getUuid().compareTo(o2.getUuid());
            }
        });
/*
        Collections.sort(list, (Resume o1, Resume o2) -> {
            int cmp = o1.getFullName().compareTo(o2.getFullName());
            if (cmp != 0) return cmp;
            return o1.getUuid().compareTo(o2.getUuid());
        });
        return Collections.singletonList(new Resume());
*/
        return list;
    }

    @Override
    public boolean isSectionSupported() {
        return true;
    }

    protected abstract List<Resume> doGetAllSorted();

    public abstract int size();

    private C getContext(Resume r) {
        return getContext(r.getUuid());
    }
}

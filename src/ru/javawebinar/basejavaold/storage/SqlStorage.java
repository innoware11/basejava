package ru.javawebinar.basejavaold.storage;

import ru.javawebinar.basejavaold.WebAppException;
import ru.javawebinar.basejavaold.model.ContactType;
import ru.javawebinar.basejavaold.model.Resume;
import ru.javawebinar.basejavaold.sql.Sql;
import ru.javawebinar.basejavaold.sql.SqlExecutor;
import ru.javawebinar.basejavaold.sql.SqlTransaction;
import ru.javawebinar.basejavaold.util.Util;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class SqlStorage implements IStorage {
    public Sql sql;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sql = new Sql(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sql.execute("DELETE FROM resume", (SqlExecutor<Void>) ps -> {
            ps.execute();
            return null;
        });
        //  sql.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume r) throws WebAppException {
        sql.execute((SqlTransaction<Void>) conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name, location, home_page) VALUES (?,?,?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.setString(3, r.getLocation());
                ps.setString(4, r.getHomePage());
                ps.execute();
            }
            insertContact(conn, r);
            return null;
        });
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    @Override
    public void update(Resume r) {
        sql.execute((SqlTransaction<Void>) conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=?, location=?, home_page=? WHERE uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getLocation());
                ps.setString(3, r.getHomePage());
                ps.setString(4, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new WebAppException("Resume not found", r);
                }
            }
            deleteContacts(conn, r);
            insertContact(conn, r);
            return null;
        });
    }

    @Override
    public Resume load(String uuid) {
        return sql.execute("" +
                        "SELECT *\n" +
                        "  FROM resume r\n" +
                        "  LEFT JOIN contact c ON c.resume_uuid=r.uuid\n" +
                        " WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new WebAppException("Resume " + uuid + " is not exist");
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"), rs.getString("location"), rs.getString("home_page"));
                    addContact(rs, r);
                    while (rs.next()) {
                        addContact(rs, r);
                    }
                    return r;
                });
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (!Util.isEmpty(value)) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value);
        }
    }


    @Override
    public void delete(String uuid) {
        sql.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new WebAppException("Resume " + uuid + "not exist", uuid);
            }
            return null;
        });
    }

    @Override
    public Collection<Resume> getAllSorted() {
        return sql.execute("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY full_name, uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        Resume resume = map.get(uuid);
                        if (resume == null) {
                            resume = new Resume(uuid, rs.getString("full_name"),
                                    rs.getString("location"), rs.getString("home_page"));
                            map.put(uuid, resume);
                        }
                        addContact(rs, resume);
                    }
                    return new LinkedList<>(map.values());
                });
    }

    @Override
    public int size() {
        return sql.execute("SELECT count(*) from resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    @Override
    public boolean isSectionSupported() {
        return false;
    }

    public void insertDate(LocalDate startDate, LocalDate endDate) {
        sql.execute("INSERT INTO period (start_date, end_date) VALUES (?,?)",
                ps -> {
                    ps.setDate(1, Date.valueOf(startDate));
                    ps.setDate(2, Date.valueOf(endDate));
                    ps.execute();
                    return null;
                });
    }
}

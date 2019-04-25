package ru.javawebinar.basejavaold.sql;

import ru.javawebinar.basejavaold.WebAppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sql {
    private final ConnectionFactory connectionFactory;

    public Sql(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sql) {
        execute(sql, (SqlExecutor<Void>) ps -> {
            ps.execute();
            return null;
        });
        try (Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            throw new WebAppException("SQL failed: " + sql, e);
        }
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw new WebAppException("SQL failed: " + sql, e);
        }
    }

    public <T> T execute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()){
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            }catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }catch (SQLException e) {
            throw new WebAppException("Transaction failed", e);
        }
    }
}

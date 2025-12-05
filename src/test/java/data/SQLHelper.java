package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "asy", "pass123");
    }

    @SneakyThrows
    public static String getStatus() {
        var dataSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            return runner.query(conn, dataSQL, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static long getRecordsCount() {
        var dataSQL = "SELECT COUNT(*) FROM payment_entity";
        try (var conn = getConnection()) {
            return runner.query(conn, dataSQL, new ScalarHandler<>());
        }
    }
}

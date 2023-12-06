package impl.helpClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connectDB() {
        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
        } catch (SQLException | ClassNotFoundException e) {
            // todo: add logger
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}

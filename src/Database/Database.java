package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/jobprotal";
    private static final String USERNAME = "your_user_name";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() {
        Connection connect = null;
        try
        {
            //Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Establish Connection
            connect = DriverManager.getConnection(URL , USERNAME , PASSWORD);
            System.out.println("Database Connected Successfully!");
        }
        catch(SQLException | ClassNotFoundException e) {
            System.out.println("Error : " + e.getMessage());
            System.out.println("Database Connection Failed!");
            throw new RuntimeException(e);
        }
        return connect;
    }
}

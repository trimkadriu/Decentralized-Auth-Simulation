package com.tk.database;

import com.tk.domain.enums.ConfigKeys;
import com.tk.service.util.Config;

import java.sql.*;

/**
 * DBConnection
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class DBConnection {
    // Database Connection Information
    private static final String dbHost = Config.readValue(ConfigKeys.DB_URL);
    private static final String dbPort = Config.readValue(ConfigKeys.DB_PORT);
    private static final String dbSchema = Config.readValue(ConfigKeys.DB_SCHEMA);

    // JDBC driver name and database URL
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String dbUrl = String.format("jdbc:mysql://%s:%s/%s", dbHost, dbPort, dbSchema);

    //  Database credentials
    private static final String username = Config.readValue(ConfigKeys.DB_USER);
    private static final String password = Config.readValue(ConfigKeys.DB_PASSWORD);

    private static Connection dbConnection = null;

    private DBConnection() {
        connectDB();
    }

    public static Connection getConnection() {
        return dbConnection;
    }

    private boolean connectDB() {
        try {
            // Register JDBC driver
            Class.forName(driver);
            // Open a dbConnection
            dbConnection = DriverManager.getConnection(dbUrl, username, password);
        } catch (ClassNotFoundException cn) {
            System.out.println("Problem with driver\n" + cn.getMessage());
            return false;
        } catch (SQLException sq) {
            System.out.println("Problem to connect in db\n" + sq.getMessage());
            return false;
        }
        return true;
    }
}

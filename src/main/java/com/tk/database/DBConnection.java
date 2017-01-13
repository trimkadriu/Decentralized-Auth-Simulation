package com.tk.database;

import java.sql.*;

/**
 * DBConnection
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class DBConnection {
    // JDBC driver name and database URL
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/blockchain";

    //  Database credentials
    private static final String username = "root";
    private static final String password = "";

    protected Connection dbConnection = null;

    public DBConnection() {
        connectDB();
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

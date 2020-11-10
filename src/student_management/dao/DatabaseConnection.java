/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GIANG
 */
public class DatabaseConnection implements IDatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=student_management_java";
    private static final String USER = "sa";
    private static final String PASSWORD = "1";

    private DatabaseConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }

    }

    @Override
    public Connection getConnetion() {
        return connection;
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnetion().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  student_management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GIANG
 */
public class ConnectJdbc implements IConnect {

    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=student_management_java";
    private static final String USER = "sa";
    private static final String PASSWORD = "1";

    public  Connection getConnetion() {
        Connection conn = null;
        if (conn == null) {
            try {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ConnectJdbc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }
}

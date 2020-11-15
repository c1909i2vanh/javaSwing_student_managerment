/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import student_management.entities.Role;
import student_management.entities.User;

/**
 *
 * @author GIANG
 */
public class RoleDaoImpl implements IRole {

    private Connection databaseConnection;

    private static Role role;
    ResultSet rs;

    @Override
    public List<Role> getAllRole() {
        List<Role> listRole = new ArrayList<>();
        try {
            databaseConnection = DatabaseConnection.getInstance().getConnetion();
            try (CallableStatement cst = databaseConnection.prepareCall("{call sp_get_list_role}");) {
                rs = cst.executeQuery();
                while (rs.next()) {
                    role = new Role(rs.getInt(1), rs.getString(2));
                    listRole.add(role);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeDatabaseConnection();
        }

        return listRole;
    }

    @Override
    public void closeDatabaseConnection() {
        try {
            databaseConnection.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

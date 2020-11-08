/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import student_management.entities.User;

/**
 *
 * @author GIANG
 */
public class UserDao implements IUser {

    private static Connection conn = null;
    private static CallableStatement cst = null;
    private static User user;
    ResultSet rs;

    public UserDao() {
    }

    public Connection setConnectionJdbc() {
        if (conn == null) {
            // Kết nối tới Database qlsv_swing         
            conn = new ConnectJdbc().getConnetion();
        }
        return conn;
    }

    public boolean checkUser(User user) {
        if (user != null) {
            if (user.getUserName().equals("1") && user.getPassword().equals("1")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUser(String name, String password) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        if (conn == null) {
            conn = setConnectionJdbc();
        }
        try (CallableStatement cst = conn.prepareCall("{call sp_get_user_byemail(?) }")) {
            cst.setString(1, email);
            ResultSet rs = cst.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getInt("roleId"), rs.getInt("verifyCode"), rs.getInt("status"));

                return user;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close();
        }

        return null;

    }

    @Override
    public User getUserByName(String name) {
        return null;

    }

    @Override
    public User addVerifyCodeByEmail(String email, Integer code) {
        if (conn == null) {
            conn = setConnectionJdbc();

        }
        User user = null;
        try (CallableStatement cst = conn.prepareCall("{call sp_add_verifycode_password(?,?,?)}");) {

            cst.setString(1, email);
            cst.setInt(2, code);
            cst.registerOutParameter(3, Types.INTEGER);
            cst.executeUpdate();
            if (cst.getInt(3) == 0) {
                user = getUserByEmail(email);
                return user;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }

    @Override
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        conn = null;
    }

    @Override
    public void insert(User user) {
    }

    @Override
    public void update(User user) {
    }

    @Override
    public void delete(User user) {
    }

    @Override
    public boolean updatePassword(User user, String password) {
        if (conn == null) {
            conn = setConnectionJdbc();

        }
        try (CallableStatement cst = conn.prepareCall("{call sp_change_password_by_email(?,?)}");) {

            cst.setString(1, user.getEmail());
            cst.setString(2, password);
            int check = cst.executeUpdate();
            if (check>0) {       
                return true;
            }       
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    
        return false;
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import student_management.entities.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author GIANG
 */
public class UserDao implements IUser {

    private Connection databaseConnection;
    private static CallableStatement cst = null;
    private static User user;
    ResultSet rs;

    public UserDao() {
    }

    @Override
    public boolean checkUser(User user) {
        if (user != null) {
            try {
                String password = user.getPassword();
                databaseConnection = DatabaseConnection.getInstance().getConnetion();
                try (CallableStatement cst = databaseConnection.prepareCall("{call sp_get_user_by_name(?) }")) {
                    cst.setString(1, user.getUserName());
                    ResultSet rs = cst.executeQuery();
                    while (rs.next()) {
                        // If value return is not null 
                        // Create new user
                        if (rs.getString(1) != null) {
                            user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getInt("roleId"), rs.getInt("verifyCode"), rs.getInt("status"), rs.getDate("daterelease"));
                            if (BCrypt.checkpw(password, user.getPassword())) {
                                return true;
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    closeDatabaseConnection();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public User getNewUserRegister(String name, String pass, String email) {
        Date date = Date.valueOf(LocalDate.now());
        int status = 0;
        int roleId = 1;
        Random rand = new Random();
        int min = 100000, max = 999999;
        int verifyCode = rand.nextInt(max - min) + min;
        return user = new User(name, pass, email, roleId, verifyCode, status, date);
    }

    @Override
    public User getUserToLogin(String name, String password) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            databaseConnection = DatabaseConnection.getInstance().getConnetion();
            try (CallableStatement cst = databaseConnection.prepareCall("{call sp_get_user_byemail(?) }")) {
                cst.setString(1, email);
                ResultSet rs = cst.executeQuery();
                if (rs.next()) {
                    user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getInt("roleId"), rs.getInt("verifyCode"), rs.getInt("status"), rs.getDate("daterelease"));

                    return user;
                } else {
                    return null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeDatabaseConnection();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User getUserByName(String name) {
        return null;

    }

    @Override
    public User addVerifyCodeByEmail(String email, Integer code) {
        try {
            databaseConnection = DatabaseConnection.getInstance().getConnetion();
            User user = new User();
            try (CallableStatement cst = databaseConnection.prepareCall("{call sp_add_verifycode_password(?,?,?)}");) {
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
                closeDatabaseConnection();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void closeDatabaseConnection() {
        try {

            databaseConnection.close();

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void insert(User user) {
        try {
            databaseConnection = DatabaseConnection.getInstance().getConnetion();

            try (CallableStatement cst = databaseConnection.prepareCall("{call sp_insert_user(?,?,?,?,?,?,?,?) }");) {
                cst.setString(1, user.getUserName());
                cst.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
                cst.setString(3, user.getEmail());
                cst.setInt(4, user.getRoleId());
                cst.setInt(5, user.getVerifyCode());
                cst.setInt(6, user.getStatus());
                cst.setDate(7, user.getDateRelase());
                cst.registerOutParameter(8, java.sql.Types.INTEGER);
                cst.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeDatabaseConnection();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(User user) {
    }

    @Override
    public void delete(User user) {
    }

    @Override
    public boolean updatePasswordByEmail(User user, String password) {
        try {
            databaseConnection = DatabaseConnection.getInstance().getConnetion();
            try (CallableStatement cst = databaseConnection.prepareCall("{call sp_change_password_by_email(?,?)}");) {
                cst.setString(1, user.getEmail());
                cst.setString(2, BCrypt.hashpw(password, BCrypt.gensalt(12)));
                int check = cst.executeUpdate();
                if (check > 0) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeDatabaseConnection();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<User> getAllUser() {
        List<User> listUser = new ArrayList<>();

        try {
            databaseConnection = DatabaseConnection.getInstance().getConnetion();
            try (PreparedStatement ps = databaseConnection.prepareCall("select * from tbluser");) {
                rs = ps.executeQuery();
                while (rs.next()) {
                    user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getInt("roleId"), rs.getInt("verifyCode"), rs.getInt("status"), rs.getDate("daterelease"));
                    listUser.add(user);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUser;
    }

    @Override
    public List<List<User>> getListMapUserWithRole() {
        List<User> listUser = new ArrayList<>();
        List<List<User>> listUser1 = new ArrayList<List<User>>();
        try {
            databaseConnection = DatabaseConnection.getInstance().getConnetion();
            try (CallableStatement cst = databaseConnection.prepareCall("{call sp_get_list_map_user_with_role}");) {
                rs = cst.executeQuery();
                while (rs.next()) {
                    user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getInt("roleId"), rs.getInt("verifyCode"), rs.getInt("status"), rs.getDate("daterelease"));
                   listUser.add(user);
                   listUser1.add(listUser);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUser1;
    }

    @Override
    public boolean checkUserNameNotExists(String username) {
        try {
            databaseConnection = DatabaseConnection.getInstance().getConnetion();
            try (CallableStatement cst = databaseConnection.prepareCall("{call sp_check_username_not_exists(?,?)}");) {
                cst.setString(1, username);
                cst.registerOutParameter(2, Types.INTEGER);
                cst.executeUpdate();
                if (cst.getInt(2) == 0) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeDatabaseConnection();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean checkUserEmailNotExists(String email) {
        try {
            databaseConnection = DatabaseConnection.getInstance().getConnetion();
            try (CallableStatement cst = databaseConnection.prepareCall("{call sp_check_useremail_not_exists(?,?)}");) {
                cst.setString(1, email);
                cst.registerOutParameter(2, java.sql.Types.INTEGER);
                cst.executeUpdate();
                int error = cst.getInt(2);
                if (error == 0) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeDatabaseConnection();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean confirmUser(User user, String code) {
        if (user.getVerifyCode() == Integer.parseInt(code)) {
            try {
                databaseConnection = DatabaseConnection.getInstance().getConnetion();
                String sql = "{call sp_confirmuser(?,?)}";
                CallableStatement cst = databaseConnection.prepareCall(sql);
                cst.setString(1, user.getEmail());
                cst.registerOutParameter(2, java.sql.Types.INTEGER);
                int err = cst.executeUpdate();

                if (cst.getInt(2) == 0) {
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return false;

    }

    public boolean regexNewPassword(String pass) {
        String regexCode = "^(?=.*[A-Z])(?=.*\\d)(?=.*[a-z])[A-Za-z0-9]{8,}$";
        return pass.matches(regexCode);
    }

}

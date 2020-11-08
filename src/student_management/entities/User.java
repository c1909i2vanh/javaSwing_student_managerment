/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  student_management.entities;

import java.util.Date;

/**
 *
 * @author GIANG
 */
public class User {
    private String userName;
    private String password;
    private String email;
    private int roleId;
    private int verifyCode;
    private int status ;
    private Date dateRelase;
    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, String email, int roleId, int verifyCode, int status, Date dateRelase) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
        this.verifyCode = verifyCode;
        this.status = status;
        this.dateRelase = dateRelase;
    }

    public User(String userName, String password, String email, int roleId, int verifyCode, int status) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
        this.verifyCode = verifyCode;
        this.status = status;
    }

  

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.roleId = 1;
        this.status =1;
    }

    
    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" + "userName=" + userName + ", password=" + password + ", email=" + email + ", roleId=" + roleId + ", verifyCode=" + verifyCode + ", status=" + status + '}';
    }
    
    
}

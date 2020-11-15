/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.controller;

import student_management.dao.UserDao;
import student_management.entities.User;

/**
 *
 * @author GIANG
 */
public class AccountController {
    private static UserDao userDao;
    private static User user;
    

    public AccountController(User user) {
        this.userDao = new UserDao();
        this.user = user;
    }
    
}

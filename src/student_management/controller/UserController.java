/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import student_management.dao.RoleDaoImpl;
import student_management.dao.UserDao;
import student_management.entities.Role;
import student_management.entities.User;
import student_management.view.MainView;

/**
 *
 * @author GIANG
 */
public class UserController {

    private static UserDao userDao;
    private static RoleDaoImpl userRoleDao;
    private static MainView mainView;

    public UserController(UserDao userDao, MainView mainview) {
        UserController.userDao = userDao;
        UserController.userRoleDao = new RoleDaoImpl();
        mainView = mainview;

    }

    public void showUserView() {
        List<List<User>> listUser = new ArrayList<List<User>>();
        listUser = userDao.getListMapUserWithRole();

        mainView.showUserView(listUser);
    }

    public void showListRole() {

        List<Role> listRole = userRoleDao.getAllRole();
        mainView.showListRole(listRole);
    }

}

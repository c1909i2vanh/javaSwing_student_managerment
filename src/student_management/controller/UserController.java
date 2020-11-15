/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import student_management.dao.RoleDaoImpl;
import student_management.dao.UserDao;
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

    public UserController(UserDao userDao) {
        UserController.userDao = userDao;
        UserController.userRoleDao = new RoleDaoImpl();
        mainView = new MainView();
       
    }

    public void showUserView() {
        Map<String, User> listMap = new HashMap<>();
        listMap = userDao.getListMapUserWithRole();
          List<User> list = userDao.getAllUser();
            mainView.showUserView(list);
       
     

    }
}

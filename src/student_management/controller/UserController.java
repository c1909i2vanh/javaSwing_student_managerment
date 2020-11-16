/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
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
        mainView.addUserTableActionListener(new UserTableListener());
        mainView.addSaveNewRoleBtnActionListener(new SaveNewRoleListener());

    }

    public void showUserView() {
        // get list User with it's role
        Map<String, String> listMap = new HashMap<>();
        // get list Role 
        List<Role> listRole = userRoleDao.getAllRole();
        listMap = userDao.getListMapUserWithRole();
        mainView.showUserView(listMap);
        mainView.showListRole(listRole);
    }

    private class UserTableListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            // get UserName from Table
            String userName = mainView.getEditUserName();
            String userRole = mainView.getEditUserRole();
            // Object role = mainView.getSelectedItemComboBox();
            if (userName != null) {
                mainView.setEditUserName(userName);
            }
            if (userRole != null) {
                mainView.setSelectedComboBoxRole(userRole);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    private class SaveNewRoleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String roleName = mainView.getRoleName();
            String userName = mainView.getUserInfo();
            if (userName != null) {
                User user = userDao.getUserByName(userName);
                Role role = userRoleDao.getRoleByRoleName(roleName);
                boolean check = userDao.updateNewRole(user, role);
                if (check) {
                    mainView.setEditUserName("");
                    showUserView();
                }
            }

        }

    }
}

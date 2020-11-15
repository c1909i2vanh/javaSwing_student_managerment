/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import student_management.dao.IRole;
import student_management.dao.RoleDaoImpl;
import student_management.dao.UserDao;
import student_management.entities.Role;
import student_management.entities.User;
import student_management.view.MainView;

/**
 *
 * @author GIANG
 */
public class HomeController {

    private static UserDao homeUserDao;
    private static MainView mainView;
    private static User homeUser;
    
    public HomeController() {
        HomeController.mainView = new MainView();
        HomeController.homeUserDao = new UserDao();
        //HomeController.homeUser = user;
        // Add Button ActionListener 
        mainView.addAccountBtnActionListener(new AccountBtnListener());
        mainView.addUserBtnActionListener(new UserBtnListener());
        mainView.addStudentBtnActionListener(new StudentBtnListener());
        mainView.addCourseBtnActionListener(new CourseBtnListener());
        mainView.addOtherBtnActionListener(new OtherBtnListener());
        mainView.addLogOutBtnActionListener(new LogoutBtnListener());
        mainView.addShowChangePassViewBtnActionListener(new ShowPassViewBtnListener());
        mainView.addHideChangePassViewBtnActionListener(new HidePassViewBtnListener());
        mainView.addSaveNewPassBtnActionListener(new SaveNewPassBtn());
    }

    public void showMainView() {
        
        mainView.startMainView();
    }

    private class AccountBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // show AccountPanel
            mainView.showAccountPanel();
        }

    }

    private class UserBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // show UserPanel
            mainView.showUserPanel();           
            UserController userController = new UserController(homeUserDao);
            userController.showUserView();
        }

    }

    private class StudentBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // show StudentPanel
            mainView.showStudentPanel();
        }

    }

    private class CourseBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // show CoursePanel
            mainView.showCoursePanel();
        }

    }

    private class OtherBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Show Other Panel
            mainView.showOtherPanel();
        }

    }

    private class LogoutBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Show Other Panel
            mainView.close();
            LoginController loginController = new LoginController();
            loginController.showLoginView();
        }

    }

    private class ShowPassViewBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Show Other Panel
            mainView.showChangePassView();
        }

    }

    private class HidePassViewBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Show Other Panel
            mainView.hideChangePassView();
        }

    }

    private class SaveNewPassBtn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Show Other Panel
            String newPassword = mainView.getNewPassWord();         
            String passwordConfirm = mainView.getNewPassWordConfirm();
            if (homeUserDao.regexNewPassword(newPassword)) {
                mainView.showNewPassError("");
                if (newPassword.equals(passwordConfirm)) {
                    mainView.showNewConfirmPassError("");
                    if (homeUserDao.updatePasswordByEmail(homeUser, newPassword)) {                    
                        JOptionPane.showMessageDialog(mainView, "Your password has been changed!");                     
                    }
                } else {
                      mainView.showNewConfirmPassError("Your confirm password doesn't match new password!Please try again");
                }
            } else {
                  mainView.showNewPassError("Your new password is too weak! Please re-enter");
            }
        }

    }
}

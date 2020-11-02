/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlsv_swing.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import qlsv_swing.dao.UserDao;
import qlsv_swing.entity.User;
import qlsv_swing.view.LoginView;
import qlsv_swing.view.StudentView;

/**
 *
 * @author GIANG
 */
public class LoginController {

    private static UserDao userDao;
    private static LoginView loginView;
    private static StudentView studentView;

    public LoginController(LoginView view) {
        this.loginView = view;
        this.userDao = new UserDao();
        view.addLoginListener(new LoginListener());
        view.addEnterKeyLoginListener(new EnterKeyPressedListener());
        view.addUserFieldKeyTypedListener(new UserTextFieldKeyPressedListener());
        view.addPasswordFieldKeyTypedListener(new PasswordTextFieldKeyPressedListener());
    }

    public void showLoginView() {

        loginView.startLogin();

    }

    private static class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getUser();
            if (userDao.checkUser(user)) {
                //neu dang nhap thanh cong mo man hinh quan ly sinh vien
                studentView = new StudentView();
                StudentController studentController = new StudentController(studentView);
                studentController.showStudentView();
                loginView.setVisible(false);
            } else {
                loginView.showMessage("username hoặc password không đúng!");

            }
        }
    }

    private static class UserTextFieldKeyPressedListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if(!loginView.checkPasswordFieldEmpty()){
                loginView.clearPasswordField();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!loginView.checkUserNameFieldEmpty()) {
                    loginView.focusPassField();
                    if (!loginView.checkPasswordFieldEmpty()) {
                        loginView.loginBtnClick();
                    } else {
                        loginView.showError("Vui lòng nhập mật khẩu!");
                    }
                } else {
                    loginView.showError("Vui lòng nhập tài khoản!");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

    private static class PasswordTextFieldKeyPressedListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
               
                if (!loginView.checkUserNameFieldEmpty()) {
                    if(!loginView.checkPasswordFieldEmpty()){
                      
                      loginView.loginBtnClick();
                      loginView.hideError();
                    }else{
                        loginView.focusPassField();
                        loginView.showError("Vui lòng nhập mật khẩu!");
                    }
                  
                } else {
                    loginView.focusUserNameField();
                    loginView.showError("Vui lòng nhập tài khoản!");
                }

            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

    private static class EnterKeyPressedListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                User user = loginView.getUser();
                if (userDao.checkUser(user)) {
                    //neu dang nhap thanh cong mo man hinh quan ly sinh vien
                    studentView = new StudentView();
                    StudentController studentController = new StudentController(studentView);
                    studentController.showStudentView();
                    loginView.setVisible(false);
                } else {
                    
                    loginView.showMessage("username hoặc password không đúng!");
                    loginView.focusUserNameField();
                    loginView.showError("Tài khoản hoặc mật khảu không đúng!");

                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
          
        }

    }
}

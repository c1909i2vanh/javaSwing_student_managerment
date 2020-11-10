/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import student_management.dao.ILogin;

import student_management.dao.UserDao;
import student_management.entities.User;
import student_management.view.ForgotPassView;
import student_management.view.LoginView;
import student_management.view.RegisterView;
import student_management.view.StudentView;

/**
 *
 * @author GIANG
 */
public class LoginController implements ILogin {

    private static UserDao userDao;
    private static LoginView loginView;
    private static StudentView studentView;
    private static Boolean status;
    private static RegisterView registerView;
    private static ForgotPassView forgotPassView;

    public LoginController() {
        this.loginView = new LoginView();
        this.userDao = new UserDao();
        loginView.addLoginListener(new LoginListener());
        loginView.addEnterKeyLoginListener(new EnterKeyPressedListener());
        loginView.addEnterKeyTypedListener(new EnterKeyPressedListener());
        loginView.addUserFieldKeyTypedListener(new UserTextFieldKeyPressedListener());
        loginView.addPasswordFieldKeyTypedListener(new PasswordTextFieldKeyPressedListener());
        loginView.addRegisterListener(new ShowHideRegisterViewListener());
        loginView.addForgotPassListener(new ForgotPasswordViewListener() );
    }

  
    @Override
    public void showLoginView() {
        loginView.startLogin();
    }
   
    public static Boolean getStatus() {
        return status;
    }

    public static void setStatus(Boolean status) {
        LoginController.status = status;
    }

    @Override
    public void close() {
        loginView.closeLogin();
    }

    private static class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getLoginUser();
            if (userDao.checkUser(user)) {
                //neu dang nhap thanh cong mo man hinh quan ly sinh vien
                studentView = new StudentView();
                StudentController studentController = new StudentController(studentView);
                studentController.showStudentView();
                loginView.closeLogin();
            } else {

                loginView.showError("Username or password is incorrect");
                loginView.focusUserNameField();

            }
        }
    }

    private static class UserTextFieldKeyPressedListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (!loginView.passwordFieldIsEmpty()) {
                loginView.clearPasswordField();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!loginView.userNameFieldIsEmpty()) {
                    loginView.focusPassField();
                    if (!loginView.passwordFieldIsEmpty()) {
                        loginView.loginBtnClick();
                    } else {
                        loginView.showError("Please enter a password");
                    }
                } else {
                    loginView.showError("Please enter a username!");
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
            if (e.getKeyCode() == KeyEvent.VK_TAB) {
                loginView.focusLoginBtn();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!loginView.userNameFieldIsEmpty()) {
                        loginView.focusLoginBtn();
                    } else {
                        loginView.focusPassField();
                        loginView.showError("Please enter a password!");
                    }
                } else {
                    loginView.focusUserNameField();
                    loginView.showError("Please enter a username!");
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
                User user = loginView.getLoginUser();
                if (userDao.checkUser(user)) {
                    //neu dang nhap thanh cong mo man hinh quan ly sinh vien
                    studentView = new StudentView();
                    StudentController studentController = new StudentController(studentView);
                    studentController.showStudentView();
                    loginView.closeLogin();
                } else {

                    loginView.showError("Username or password is incorrect!");

                    loginView.focusUserNameField();

                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

    private static class ShowHideRegisterViewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
             Component  loginComponent = loginView.getLoginComponent();
            RegisterController registerController = new RegisterController();
            registerController.showRegisterView(loginComponent);
             loginView.closeLogin();

        }
    }

    private static class ForgotPasswordViewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Component  loginComponent = loginView.getLoginComponent();
           
            ForgotPassController forgotController = new ForgotPassController();
            forgotController.showForgotPassView(loginComponent);
            loginView.closeLogin();
        }

    }
}

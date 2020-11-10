/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.controller;

import customClass.SendMail;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
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
public class RegisterController {

    private static UserDao userDao;
    private static LoginView loginView;
    private static StudentView studentView;
    private static Boolean status;
    private static RegisterView registerView;
    private static ForgotPassView forgotPassView;
    private static User user;

    public RegisterController() {
        this.registerView = new RegisterView();
        this.userDao = new UserDao();
        registerView.addRegisterListener(new RegisterListener());
        registerView.addClearListener(new ClearListener());
        registerView.addExitMouseListener(new ExitMouseListener());
        registerView.addConFirmListener(new ConfirmVerifyCode());
    }

    public void showRegisterView(Component component) {
        registerView.startLogin(component);

    }

    private class RegisterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = registerView.getUserField();
            String password = registerView.getPasswordField();
            String confirmPass = registerView.getConfirmPassField();
            String email = registerView.getEmailField();
            boolean nameNotExists = true;
            boolean emailNotExists = true;
            if (registerView.validateUserName(username)) {
                registerView.showErrorUserName("");
                if (userDao.checkUserNameNotExists(username)) {
                    nameNotExists = true;
                    registerView.showErrorUserName("");
                } else {
                    nameNotExists = false;
                    registerView.showErrorUserName("Username already Exists! ");
                }
            } else {
                registerView.showErrorUserName("Username needs at least 8 characters and contains no special characters ");
            }
            if (registerView.validateEmail(email)) {
                registerView.showErrorEmail("");
                if (userDao.checkUserEmailNotExists(email)) {
                    emailNotExists = true;
                    registerView.showErrorEmail("");
                } else {
                    emailNotExists = false;
                    registerView.showErrorEmail("Username already Exists! ");
                }
            } else {
                registerView.showErrorEmail("Please enter a valid email address.");
            }
            if (registerView.validatePassword(password)) {
                registerView.showErrorPassword("");
                if (confirmPass.equals(password)) {
                    registerView.showErrorConfirmPass("");
                } else {
                    registerView.showErrorConfirmPass("Your confirm password isn't match your password!Please try again! ");
                }
            } else {
                registerView.showErrorPassword("Your password is too weak! Please re-enter");
            }
            if (registerView.validateUserName(username) && registerView.validateEmail(email)) {
                if (nameNotExists && emailNotExists && registerView.validatePassword(password) && confirmPass.equals(password)) {
                    user = userDao.getNewUserRegister(username, password, email);
                    if (user != null) {
                        userDao.insert(user);
                        String toEmail = user.getEmail();
                        String subject = "New Student Management Account Email Verification";
                        String message = "Your verify code is " + user.getVerifyCode();
                        SendMail sendMail = new SendMail();
                        sendMail.sendMail(toEmail, message, subject);
                        registerView.showAuthenView();
                        registerView.hideRegisterView();
                    }

                }
            }
        }

    }

    private class ClearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            registerView.clearRegisterView();
        }

    }

    private class ConfirmVerifyCode implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String code = registerView.getVerifyCodeField();
            if (registerView.validateVerifyCode(code)) {
                if (userDao.confirmUser(user, code)) {
                    JOptionPane.showMessageDialog(registerView, "Your acccount has been confirmed!");
                    registerView.close();
                    LoginController controller = new LoginController();
                    controller.showLoginView();

                } else {
                    registerView.showErrorAuthentication("Your confirm code invalid!");
                }

            } else {
                registerView.showErrorAuthentication("Your confirm code invalid!");
            }
        }

    }

    private class ExitMouseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            registerView.close();
            LoginController controller = new LoginController();
            controller.showLoginView();
        }

    }
}

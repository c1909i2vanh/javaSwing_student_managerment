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
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
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
public class ForgotPassController implements ILogin {

    private static UserDao userDao;
    private static LoginView loginView;
    private static StudentView studentView;
    private static Boolean status;
    private static RegisterView registerView;
    private static ForgotPassView forgotPassView;

    private static User user;

    public ForgotPassController() {
        this.forgotPassView = new ForgotPassView();
        this.userDao = new UserDao();
        forgotPassView.addExitMouseListener(new ExitMouseForgotListener());
        forgotPassView.addBackToForgotListener(new BackToForgotViewListener());
        forgotPassView.addSendVerifyCodeListener(new SendVerifyCodeListener());
        forgotPassView.addNextToStepListener(new NextToStepListener());
        forgotPassView.addFinishListener(new FinishForgotPassListener());
    }

    @Override
    public void showLoginView() {
        loginView.startLogin();
    }

    public void showForgotPassView(Component component) {
        forgotPassView.startLogin(component);

    }

    @Override
    public void close() {
        forgotPassView.close();
    }

    private static class SendVerifyCodeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Check email adress
            String toEmail = forgotPassView.getEmailField();
            if (forgotPassView.regexEmail(toEmail)) {
                // Call procedure to add verifyCode to server          
                Random rand = new Random();
                int min = 100000, max = 999999;
                int randomCode = rand.nextInt(max - min) + min;

                // Get user by email and add verifyCode
                user = userDao.addVerifyCodeByEmail(toEmail, randomCode);
                if (user != null) {
                    String subject = "New Student Management Password  Verification";
                    String message = "Your verify code is " + randomCode;
                    SendMail sendMail = new SendMail();
                    sendMail.sendMail(toEmail, message, subject);
                    forgotPassView.disableSendCodeBtn();
                    forgotPassView.deleteError();
                    forgotPassView.enableNextBtn();
                    forgotPassView.showNotify("Code has been send to your email! Please check your email!");
                    forgotPassView.showNotify1("Enter you verify code");
                } else {

                    forgotPassView.showError("We found an account associated with your email! Please try again! ");
                }
            } else {
                forgotPassView.showError("The email address  you entered is invalid. Please try again");
            }
        }
    }

    private static class ExitMouseForgotListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            LoginController controller = new LoginController();
            controller.showLoginView();
            forgotPassView.close();
        }
    }

    private static class BackToLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        }

    }

    private static class NextToStepListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String code = forgotPassView.getVerifyCodeField();
            if (forgotPassView.regexVerifyCode(code)) {
                //Check user retrieve by SendVerifyCodeListener Class
                if (forgotPassView.checkVerifyCode(user)) {
                    forgotPassView.showResetView();
                    forgotPassView.hideForgotView();
                } else {
                    forgotPassView.showError("The provided verification code is invalid. Please verify the entered code and try again");
                }
            } else {
                forgotPassView.showError("The provided verification code is invalid. Please verify the entered code and try again");
            }
        }
    }

    private static class FinishForgotPassListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String newPassword = forgotPassView.getPasswordField();
            String passwordConfirm = forgotPassView.getConfirmPassField();
            if (forgotPassView.regexNewPassword(newPassword)) {
                forgotPassView.showErrorPassword("");
                if (newPassword.equals(passwordConfirm)) {
                    if (userDao.updatePasswordByEmail(user, newPassword)) {
                        JOptionPane.showMessageDialog(loginView, "Your password has been changed!");

                        LoginController controller = new LoginController();
                        controller.showLoginView();
                        forgotPassView.close();
                    }
                } else {
                    forgotPassView.showErrorConfirmPass("Your confirm password doesn't match new password!Please try again");
                }
            } else {
                forgotPassView.showErrorPassword("Your new password is too weak! Please re-enter");
            }
        }
    }

    private static class BackToForgotViewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            forgotPassView.backToForgotView();
            forgotPassView.hideResetView();
        }
    }
}

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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.CallableStatement;
import java.sql.Connection;
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

    public ForgotPassController(ForgotPassView forgotView) {
        this.forgotPassView = forgotView;
        this.userDao = new UserDao();
        forgotView.addExitMouseListener(new ExitMouseForgotListener());
        forgotView.addBackToForgotListener(new BackToForgotViewListener());
        forgotView.addSendVerifyCodeListener(new SendVerifyCodeListener());
        forgotView.addNextToStepListener(new NextToStepListener());
        forgotView.addVerifyCodeListener(new VerifyCodeListener());
        forgotView.addFinishListener(new FinishForgotPassListener());
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
            String toEMail = forgotPassView.getEmailField();

            if (forgotPassView.regexEmail(toEMail)) {

                // Call procedure to add verifyCode to server
                int errorNumber = 1;
                Random rand = new Random();
                int min = 100000, max = 999999;
                int randomCode = rand.nextInt(max - min) + min;
                String host = "smtp.gmail.com";
                String fromEmail = "studentmanagement89@gmail.com";
                String fromPassword = "Giang123";

                String subject = "Verify Code";

                // Get user by email and add verifyCode
                user = userDao.addVerifyCodeByEmail(toEMail, randomCode);
                if (user != null) {
                    Properties pros = new Properties();
                    pros.put("mail.smtp.auth", "true");
                    pros.put("mail.smtp.starttls.enable", "true");
                    pros.put("mail.smtp.host", "smtp.gmail.com");
                    pros.put("mail.smtp.port", "587");
                    Session mailSession = Session.getInstance(pros,
                            new javax.mail.Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(fromEmail, fromPassword);
                                }
                            });
                    try {
                        Message msg = new MimeMessage(mailSession);
                        msg.setFrom(new InternetAddress(fromEmail));
                        msg.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(user.getEmail()));
                        msg.setSubject(subject);
                        msg.setText("Your verify code is " + user.getVerifyCode());
                        Transport.send(msg);

                    } catch (Exception ex1) {
                        System.err.println("" + e);
                    }
                    forgotPassView.disableSendCodeBtn();
                    forgotPassView.deleteError();
                    forgotPassView.enableNextBtn();
                    forgotPassView.showNotify("Code has been send to your email! Please check your email!");
                    forgotPassView.showNotify1("Enter you verify code");

                } else {

                    forgotPassView.showErrorForgot("We found an account associated with your email! Please try again! ");
                }
            } else {

                forgotPassView.showErrorForgot("The email address  you entered is invalid. Please try again");

            }

        }

    }

    private static class VerifyCodeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            String code = forgotPassView.getVerifyCodeField();
//            if (forgotPassView.regexVerifyCode(code)) {
//                if (forgotPassView.checkVerifyCode(user)) {
//                   forgotPassView
//                } else {
//                    forgotPassView.showError("The provided verification code is invalid. Please verify the entered code and try again");
//                }
//            } else {
//                forgotPassView.showError("The provided verification code is invalid. Please verify the entered code and try again");
//            }

        }

    }

    private static class ExitMouseForgotListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            loginView = new LoginView();
            LoginController controller = new LoginController(loginView);
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
                if (forgotPassView.checkVerifyCode(user)) {
                    forgotPassView.showResetView();
                    forgotPassView.hideForgotView();
                } else {
                    forgotPassView.showErrorForgot("The provided verification code is invalid. Please verify the entered code and try again");
                }
            } else {
                forgotPassView.showErrorForgot("The provided verification code is invalid. Please verify the entered code and try again");
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
                System.out.println(newPassword);
                System.out.println(passwordConfirm);
                if (newPassword.equals(passwordConfirm)){
                    if (userDao.updatePassword(user, newPassword)) {
                        JOptionPane.showMessageDialog(loginView, "Your password has been changed!");
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

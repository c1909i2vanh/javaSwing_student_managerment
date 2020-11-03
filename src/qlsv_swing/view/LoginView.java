/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlsv_swing.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import qlsv_swing.entity.User;

/**
 *
 * @author GIANG
 */
public class LoginView extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel userNameLabel;
    private JLabel passwordlabel;
    private JPasswordField passwordField;
    private JTextField userNameField;
    private JTextField errorField;
    private JCheckBox rememberCkBox;
    private JLabel rememberLabel;
    private JButton loginBtn;
    Preferences preference;
    boolean rememberPreference;

    public LoginView() {
        initComponents();
        rememberMe();
    }

    private void initComponents() {
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        userNameLabel = new JLabel("UserName ");
        passwordlabel = new JLabel("Password ");
        userNameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        rememberCkBox = new JCheckBox();
        rememberLabel = new JLabel("Remember me");
        errorField = new JTextField(30);
        loginBtn = new JButton("Login");
      //  loginBtn.addActionListener(this);

        //tao Spring layout
        SpringLayout layout = new SpringLayout();
        JPanel panelParent = new JPanel();
        JPanel panel = new JPanel();
        // tạo đối tượng panel để chứa các thành phần của màn hình login
        panel.setSize(400, 300);
        panel.setLayout(layout);
        panel.add(userNameLabel);
        panel.add(passwordlabel);
        panel.add(userNameField);
        errorField.setEditable(false);
        userNameField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        errorField.setBorder(BorderFactory.createEmptyBorder());
        rememberLabel.setBorder(BorderFactory.createEmptyBorder());
        panel.add(passwordField);
        panel.add(rememberCkBox);
        panel.add(rememberLabel);

        panel.add(errorField);
        panel.add(loginBtn);

        // cai dat vi tri cac thanh phan tren man hinh login
        layout.putConstraint(SpringLayout.WEST, userNameLabel, 50, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, userNameLabel, 80, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, passwordlabel, 50, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, passwordlabel, 105, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, userNameField, 110, SpringLayout.WEST, userNameLabel);
        layout.putConstraint(SpringLayout.NORTH, userNameField, 80, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, passwordField, 110, SpringLayout.WEST, passwordlabel);
        layout.putConstraint(SpringLayout.NORTH, passwordField, 105, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, rememberCkBox, 110, SpringLayout.WEST, passwordlabel);
        layout.putConstraint(SpringLayout.NORTH, rememberCkBox, 140, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, rememberLabel, 140, SpringLayout.WEST, passwordlabel);
        layout.putConstraint(SpringLayout.NORTH, rememberLabel, 141, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, errorField, 80, SpringLayout.WEST, passwordlabel);
        layout.putConstraint(SpringLayout.NORTH, errorField, 180, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, loginBtn, 110, SpringLayout.WEST, passwordlabel);
        layout.putConstraint(SpringLayout.NORTH, loginBtn, 220, SpringLayout.NORTH, panel);

        // add panel toi JFrame
        panelParent.setBackground(Color.red);
        panel.setBackground(Color.BLUE);
        panelParent.add(panel);
        this.add(panelParent);
        this.pack();
        //Tạo border cho Frame
        
        Border panel_title_border = BorderFactory.createMatteBorder(0, 1, 1, 1, Color.yellow);
        // cai thuoc tinh cho JFrame
        
        this.setTitle("Login");
        this.setSize(900, 450);
        this.setBackground(Color.yellow);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
    }

    private void rememberMe() {
        preference = Preferences.userNodeForPackage(this.getClass());

        // Put the boolean of the rememberMe preference
        rememberPreference = preference.getBoolean("rememberMe", Boolean.valueOf(""));
        //Check if the check box was selected
        if (rememberPreference) {
            //Replace the textField by the preferemce user and Password who will be stockF
            userNameField.setText(preference.get("userNameField", ""));
            passwordField.setText(preference.get("passwordField", ""));
            rememberCkBox.setSelected(true);
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String message) {
        errorField.setForeground(Color.red);
        errorField.setText(message);
        errorField.setVisible(true);
    }

    public void hideError() {
        errorField.setText(null);
        errorField.setVisible(false);
    }

    public void focusUserNameField() {
        userNameField.requestFocus();
    }

    public void focusPassField() {
        passwordField.requestFocus();
    }

    public void clearPasswordField() {
        passwordField.setText("");
    }

    public void focusLoginBtn() {
        loginBtn.requestFocus();
    }

    public void loginBtnClick() {
        loginBtn.doClick();
    }

    public User getUser() {
        // if the check box is clicked and the boolean rememberPreference is false
        if (!rememberPreference && rememberCkBox.isSelected()) {
            //Insert into the preference the name
            preference.put("userNameField", userNameField.getText());
            preference.put("passwordField", String.valueOf(passwordField.getPassword()));
            preference.putBoolean("rememberMe", true);
        } else {
            //Reset the preference
            preference.put("userNameField", "");
            preference.put("passwordField", "");
            preference.putBoolean("rememberMe", false);

        }
        return new User(userNameField.getText(), String.copyValueOf(passwordField.getPassword()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void startLogin() {
        this.setVisible(true);
        this.focusField();
    }

    public void focusField() {
        if (userNameField.getText().trim().equals("")) {
            userNameField.requestFocus();
        } else {
            if (passwordField.getPassword().length == 0) {
                passwordField.requestFocus();
            } else {
                loginBtn.requestFocus();
            }
        }
    }

    public boolean userNameFieldIsEmpty() {
        return "".equals(userNameField.getText().trim()) || userNameField.getText() == null;
    }

    public boolean passwordFieldIsEmpty() {

        return passwordField.getPassword().length == 0;
    }

    public void addLoginListener(ActionListener listener) {
        loginBtn.addActionListener(listener);
    }

    public void addEnterKeyTypedListener(KeyListener listener) {
        loginBtn.addKeyListener(listener);
    }

    public void addEnterKeyLoginListener(KeyListener listener) {
        loginBtn.addKeyListener(listener);
    }

    public void addUserFieldKeyTypedListener(KeyListener listener) {
        userNameField.addKeyListener(listener);
    }

    public void addPasswordFieldKeyTypedListener(KeyListener listener) {
        passwordField.addKeyListener(listener);
    }

}

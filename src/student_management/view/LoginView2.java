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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import qlsv_swing.entity.User;

/**
 *
 * @author GIANG
 */
public class LoginView2 extends JFrame implements ActionListener {

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
    private JLabel exitMouse;
    private JPanel header;
    private JLabel headerTitle;
    private JLabel infoIcon;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JPanel mainPanel;
    private JTextField nameField;

    private JLabel passwordLabelIcon;
    private JLabel reduceMouse;
    private JLabel userLabelIcon;

    public LoginView2() {
        initComponents();
        this.setLocationRelativeTo(null);
        rememberMe();
    }

    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        headerTitle = new javax.swing.JLabel();
        reduceMouse = new javax.swing.JLabel();
        exitMouse = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        userLabelIcon = new javax.swing.JLabel();
        passwordLabelIcon = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        infoIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);
        setUndecorated(true);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));

        header.setBackground(new java.awt.Color(0, 204, 204));
        header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerMouseDragged(evt);
            }
        });
        header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerMousePressed(evt);
            }
        });

        headerTitle.setBackground(new java.awt.Color(255, 255, 255));
        headerTitle.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        headerTitle.setForeground(new java.awt.Color(255, 255, 255));
        headerTitle.setText("Login Form");

        reduceMouse.setBackground(new java.awt.Color(0, 204, 204));
        reduceMouse.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        reduceMouse.setForeground(new java.awt.Color(255, 255, 255));
        reduceMouse.setText("  -");
        reduceMouse.setOpaque(true);
        reduceMouse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reduceMouseMouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reduceMouseMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                reduceMouseMouseExited(evt);
            }
        });

        exitMouse.setBackground(new java.awt.Color(0, 204, 204));
        exitMouse.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        exitMouse.setForeground(new java.awt.Color(255, 255, 255));
        exitMouse.setText(" X");
        exitMouse.setOpaque(true);
        exitMouse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseMouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitMouseMouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitMouseMouseExited(evt);
            }
        });

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
                headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(headerLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(headerTitle)
                        .addGap(121, 121, 121)
                        .addComponent(reduceMouse, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(exitMouse, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headerLayout.setVerticalGroup(
                headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(headerLayout.createSequentialGroup()
                        .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(exitMouse)
                                .addComponent(reduceMouse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(headerTitle))
                        .addGap(3, 3, 3))
        );

        nameField.setText("User");
        nameField.setBorder(null);
        nameField.setPreferredSize(new java.awt.Dimension(69, 21));

        passwordField.setText("Password");
        passwordField.setBorder(null);
        passwordField.setPreferredSize(new java.awt.Dimension(69, 20));
       
       

        userLabelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/connection_user x44.png"))); // NOI18N

        passwordLabelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/debloquer x44.png"))); // NOI18N

        infoIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/info x20.png"))); // NOI18N
      

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addContainerGap(106, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(passwordLabelIcon)
                                .addComponent(userLabelIcon))
                        .addGap(32, 32, 32)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addComponent(infoIcon)
                        .addGap(32, 32, 32))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(userLabelIcon)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                        .addGap(15, 15, 15)
                                                        .addComponent(infoIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(passwordLabelIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 161, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

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

    private void reduceMouseMouseEntered(java.awt.event.MouseEvent evt) {
        reduceMouse.setBackground(new Color(207, 207, 207));
        reduceMouse.setForeground(new Color(255, 255, 255));
    }

    private void reduceMouseMouseExited(java.awt.event.MouseEvent evt) {
        reduceMouse.setBackground(new Color(0, 204, 204));
        reduceMouse.setForeground(new Color(255, 255, 255));
    }

    private void exitMouseMouseEntered(java.awt.event.MouseEvent evt) {
        exitMouse.setBackground(new Color(105, 105, 105));
        exitMouse.setForeground(new Color(255, 255, 255));

    }

    private void exitMouseMouseExited(java.awt.event.MouseEvent evt) {
        exitMouse.setBackground(new Color(0, 204, 204));
        exitMouse.setForeground(new Color(255, 255, 255));
    }

    private void reduceMouseMouseClicked(java.awt.event.MouseEvent evt) {
        this.setState(JFrame.ICONIFIED);
    }

    private void exitMouseMouseClicked(java.awt.event.MouseEvent evt) {
        this.dispose();
    }
    int xx;
    int xy;

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }

    private void headerMousePressed(java.awt.event.MouseEvent evt) {
        xx = evt.getX();
        xy = evt.getY();
    }
}

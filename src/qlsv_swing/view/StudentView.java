/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlsv_swing.view;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import qlsv_swing.controller.StudentController;

import qlsv_swing.entity.Student;

/**
 *
 * @author GIANG
 */
public class StudentView extends JFrame implements ActionListener, ListSelectionListener {

    private static final long serialVersionUID = 1L;
    private JButton addStudentBtn;
    private JButton editStudentBtn;
    private JButton deleteStudentBtn;
    private JButton clearBtn;
    private JButton sortStudentByGpaBtn;
    private JButton sortStudentByNameBtn;
    private JScrollPane jScrollPaneStudentTable;
    private JScrollPane jScrollPaneAddress;

    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel ageLabel;
    private JLabel genderLabel;
    private JLabel addressLabel;
    private JLabel phoneLabel;
    private JLabel gpaLabel;
    private JLabel statusLabel;

    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JRadioButton maleBtn, femaleBtn, statusTrue, statusFalse;

    private JTextArea addressTA;
    private JTextField phoneField;
    private JTextField gpaField;
    private JTextField statusField;

    ButtonGroup genderGroup, statusGroup;
    // định nghĩa các cột của bảng student
    private String[] columnNames = new String[]{
        "Order", "ID", "Name", "Age", "Gender", "Address", "Phone", "GPA", "Status"};
    // định nghĩa dữ liệu mặc định của bẳng student là rỗng
    private Object data = new Object[][]{};
    private JTable studentTable;

    public StudentView() {
        initComponents();
    }

    private void initComponents() {
        //Thiết lập tắt cửa sổ
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Khởi tạo các phím chức năng
        addStudentBtn = new JButton("Add");
        editStudentBtn = new JButton("Edit");
        deleteStudentBtn = new JButton("Delete");
        clearBtn = new JButton("Clear");
        sortStudentByGpaBtn = new JButton("Sort By GPA");
        sortStudentByNameBtn = new JButton("Sort By Name");

        // Khởi tạo bảng student
        jScrollPaneStudentTable = new JScrollPane();
        studentTable = new JTable();

        //Khởi tạo các label
        idLabel = new JLabel("Id");
        nameLabel = new JLabel("Name");
        ageLabel = new JLabel("Age");
        genderLabel = new JLabel("Gender");
        addressLabel = new JLabel("Address");
        phoneLabel = new JLabel("Phone");
        gpaLabel = new JLabel("GPA");
        statusLabel = new JLabel("Status");

        // Khởi tạo các trường nhập dữ liệu cho student
        idField = new JTextField(8);
        idField.setEditable(false);
        nameField = new JTextField(15);

        ageField = new JTextField(15);
        addressTA = new JTextArea(5, 15);
        phoneField = new JTextField(15);
        jScrollPaneAddress = new JScrollPane();
        jScrollPaneAddress.setViewportView(addressTA);
        gpaField = new JTextField(15);
        statusField = new JTextField(15);
        maleBtn = new JRadioButton("Male");
        femaleBtn = new JRadioButton("FeMale");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleBtn);
        genderGroup.add(femaleBtn);
        genderGroup.setSelected(maleBtn.getModel(), true);
        statusTrue = new JRadioButton("True");
        statusFalse = new JRadioButton("False");
        statusGroup = new ButtonGroup();
        statusGroup.add(statusTrue);
        statusGroup.add(statusFalse);
        statusGroup.setSelected(statusTrue.getModel(), true);
        //Cài đặt các cột và data cho bảng student
        studentTable.setModel(new DefaultTableModel((Object[][]) data, columnNames));
        ((DefaultTableModel) studentTable.getModel()).fireTableDataChanged();
        jScrollPaneStudentTable.setViewportView(studentTable);

        jScrollPaneStudentTable.setPreferredSize(new Dimension(780, 300));

        // Tạo Spring Layout
        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel();
        panel.setSize(1300, 420);
        panel.setLayout(layout);
        panel.add(jScrollPaneStudentTable);

        panel.add(idLabel);
        panel.add(nameLabel);
        panel.add(ageLabel);
        panel.add(genderLabel);
        panel.add(addressLabel);
        panel.add(phoneLabel);
        panel.add(gpaLabel);
        panel.add(statusLabel);

        panel.add(idField);
        panel.add(nameField);
        panel.add(ageField);
        panel.add(maleBtn);
        panel.add(femaleBtn);
        panel.add(addressTA);
        panel.add(phoneField);
        panel.add(gpaField);
        panel.add(statusTrue);
        panel.add(statusFalse);

        panel.add(addStudentBtn);
        panel.add(editStudentBtn);
        panel.add(clearBtn);
        panel.add(deleteStudentBtn);
        panel.add(sortStudentByGpaBtn);
        panel.add(sortStudentByNameBtn);
        //Cài đặt vị trí các thành phần trên màn hình login
        layout.putConstraint(SpringLayout.WEST, idLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, idLabel, 10, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, nameLabel, 40, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, ageLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, ageLabel, 70, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, genderLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, genderLabel, 100, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, addressLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addressLabel, 130, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, phoneLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, phoneLabel, 230, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, gpaLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, gpaLabel, 260, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, statusLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, statusLabel, 290, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, idField, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, idField, 10, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, nameField, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, nameField, 40, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, ageField, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, ageField, 70, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, maleBtn, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, maleBtn, 100, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, femaleBtn, 160, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, femaleBtn, 100, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, addressTA, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addressTA, 130, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, phoneField, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, phoneField, 230, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, gpaField, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, gpaField, 260, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, statusTrue, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, statusTrue, 290, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, statusFalse, 160, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, statusFalse, 290, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, jScrollPaneStudentTable, 300, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, jScrollPaneStudentTable, 10, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, addStudentBtn, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addStudentBtn, 330, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, editStudentBtn, 60, SpringLayout.WEST, addStudentBtn);
        layout.putConstraint(SpringLayout.NORTH, editStudentBtn, 330, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, deleteStudentBtn, 60, SpringLayout.WEST, editStudentBtn);

        layout.putConstraint(SpringLayout.NORTH, clearBtn, 330, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, clearBtn, 80, SpringLayout.WEST, deleteStudentBtn);

        layout.putConstraint(SpringLayout.NORTH, deleteStudentBtn, 330, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, sortStudentByGpaBtn, 300, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, sortStudentByGpaBtn, 330, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, sortStudentByNameBtn, 115, SpringLayout.WEST, sortStudentByGpaBtn);
        layout.putConstraint(SpringLayout.NORTH, sortStudentByNameBtn, 330, SpringLayout.NORTH, panel);
        this.add(panel);
        this.pack();
        this.setTitle("Student Infomation");
        this.setSize(1100, 420);

        //disable Edit and Delete buttons
        editStudentBtn.setEnabled(false);
        deleteStudentBtn.setEnabled(false);
        //enable Add button
        addStudentBtn.setEnabled(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public Student getStudentInfo() {
        if (!validateAllField()) {
            return null;
        }
        try {
            Student st = new Student();
            st.setName(nameField.getText().trim());
            st.setAge(Integer.parseInt(ageField.getText().trim()));
            st.setGender(maleBtn.isSelected() ? 1 : 0);
            st.setAddress(addressTA.getText());
            st.setPhone(phoneField.getText().trim());
            st.setGpa(Float.parseFloat(gpaField.getText().trim()));
            st.setStatus(statusTrue.isSelected() ? 1 : 0);
            return st;

        } catch (NumberFormatException e) {
            showMessage(e.getMessage());
        }
        return null;
    }

    public Student getStudentEdit() {
        if (!validateAllField()) {
            return null;
        }
        try {
            Student st = new Student();
            st.setId(idField.getText());
            st.setName(nameField.getText().trim());
            st.setAge(Integer.parseInt(ageField.getText().trim()));
            st.setGender(maleBtn.isSelected() ? 1 : 0);
            st.setAddress(addressTA.getText());
            st.setPhone(phoneField.getText().trim());
            st.setGpa(Float.parseFloat(gpaField.getText().trim()));
            st.setStatus(statusTrue.isSelected() ? 1 : 0);
            return st;

        } catch (NumberFormatException e) {
            showMessage(e.getMessage());
        }
        return null;
    }

    public String getStudentDelete() {
        String id = "";
        if (idField.getText() != null && idField.getText() != "") {
            id = idField.getText();
            return id;
        }
        return null;
    }

    public void showListStudents(List<Student> studentList) {
        int startingNumber = 1;
        int size = studentList.size();
        Object[][] data = new Object[size][10];
        int i = 0;
        if (studentList.size() != 0) {
            for (Student st : studentList) {
                data[i][0] = startingNumber;
                data[i][1] = st.getId();
                data[i][2] = st.getName();
                data[i][3] = st.getAge();
                data[i][4] = (st.getGender() == 1 ? "Nam" : "Nữ");
                data[i][5] = st.getAddress();
                data[i][6] = st.getPhone();
                data[i][7] = st.getGpa();
                data[i][8] = (st.getStatus() == 1 ? "Hoạt động" : "Không hoạt động");
                i++;
                startingNumber++;

            }
            studentTable.setModel(new DefaultTableModel(data, columnNames));
        }else{
              studentTable.setModel(new DefaultTableModel(data, columnNames));
        }

    }

    public void setEditStudent(Student student) {
        idField.setText(student.getId());
        nameField.setText(student.getName());
        ageField.setText(String.valueOf(student.getAge()));
        genderGroup.setSelected((student.getGender() == 1 ? maleBtn.getModel() : femaleBtn.getModel()), true);
        addressTA.setText(student.getAddress());

        phoneField.setText(student.getPhone());
        gpaField.setText(String.valueOf(student.getGpa()));
        statusGroup.setSelected((student.getStatus() == 1 ? statusTrue.getModel() : statusFalse.getModel()), true);

        addStudentBtn.setEnabled(false);
        editStudentBtn.setEnabled(true);
        deleteStudentBtn.setEnabled(true);

    }

    public void addAddStudentListener(ActionListener listener) {
        addStudentBtn.addActionListener(listener);
    }

    public void addClearStudentListener(ActionListener listener) {
        clearBtn.addActionListener(listener);
    }

    public void addEditStudentListener(ActionListener listener) {
        editStudentBtn.addActionListener(listener);
    }

    public void addDeleteStudentListener(ActionListener listener) {
        deleteStudentBtn.addActionListener(listener);
    }
    
   

    public void addSortStudentByNameListener(ActionListener listener) {
        sortStudentByNameBtn.addActionListener(listener);
    }

    public void addSortStudentByGpaListener(ActionListener listener) {
        sortStudentByGpaBtn.addActionListener(listener);
    }

    public void addMouseClickedTable(MouseListener listener) {
        studentTable.addMouseListener(listener);

    }
     public void addPhoneFieldKeyTypedListener(KeyListener listener){
       phoneField.addKeyListener(listener);
    }
     public void addAgeFieldClickedTable(KeyListener listener){
       ageField.addKeyListener(listener);
    }
     public void addGpaFieldClickedTable(KeyListener listener){
       gpaField.addKeyListener(listener);
    }
    

    public void clearStudentInfo() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");
        genderGroup.setSelected(maleBtn.getModel(), true);
        addressTA.setText("");
        gpaField.setText("");
        statusGroup.setSelected(statusTrue.getModel(), true);
        // disable Edit and Delete buttons
        editStudentBtn.setEnabled(false);
        deleteStudentBtn.setEnabled(false);
        // enable Add button
        addStudentBtn.setEnabled(true);
    }

    /**
     ** Validate
     *
     * @return
     */
    public boolean validateAllField() {
        return !(!validateName() || !validateAge() || !validateAddress() || !validatePhone() || !validateGpa());
    }

    public boolean validateName() {
        String name = nameField.getText().trim();
        if (name == null || name.equals("")) {
            nameField.requestFocus();
            showMessage("Name không được để trống");
            return false;
        }
        if (name.length() > 50) {
            nameField.requestFocus();
            showMessage("Name không quá 50 ký tự");
            return false;
        }
        return true;
    }

    public boolean validateAge() {
        try {
            Integer age = Integer.parseInt(ageField.getText().trim());
            if (age < 0 || age > 100) {
                ageField.requestFocus();
                showMessage("Age không hợp lệ! Age trong khoảng 0 đến 100");
                return false;
            }
        } catch (NumberFormatException e) {
            ageField.requestFocus();
            showMessage("Age không hợp lệ!Vui lòng nhập vào 1 số nguyên");
            return false;
        }
        return true;
    }

    public String getDeleteStudentInfo() {
        String id = "";
        return id;

    }

    public boolean validateAddress() {
        String address = addressTA.getText().trim();
        if (address == null || "".equals(address)) {
            addressTA.requestFocus();
            showMessage(" Địa chỉ không được để trống");

            return false;
        }
        if (address.length() > 200) {
            addressTA.requestFocus();
            showMessage(" Địa chỉ không quá 200 ký tự");
            return false;
        }
        return true;
    }

    public boolean validatePhone() {
        String phone = phoneField.getText().trim();
        // String regexPhone = "";
        if (phone == null || "".equals(phone)) {
            phoneField.requestFocus();
            showMessage(" Phone không được để trống");
            return false;
        }
        if (phone.length() > 11) {
            phoneField.requestFocus();
            showMessage("Phone không quá 11 ký tự");
            return false;
        }

        return true;
    }

    public boolean validateGpa() {
        try {
            Float gpa = Float.parseFloat(gpaField.getText().trim());
            if (gpa < 0) {
                gpaField.requestFocus();
                showMessage("Gpa không hợp lệ! Gpa nên trong khoảng 0 đến 10");
                return false;
            }
        } catch (NumberFormatException e) {
            gpaField.requestFocus();
            showMessage("Gpa không hợp lệ!Vui lòng nhập vào 1 số thực");
            return false;
        }

        return true;
    }

    public String getEditStudent() {
        int row = studentTable.getSelectedRow();
        String Id = studentTable.getModel().getValueAt(row, 1).toString();
        return Id;
    }

    public void refreshStudentTable() {
        DefaultTableModel dm = (DefaultTableModel) studentTable.getModel();
        dm.fireTableDataChanged();
    }

    
   
}

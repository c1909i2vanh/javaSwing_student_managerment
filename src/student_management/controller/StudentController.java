/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  student_management.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import  student_management.dao.IStudent;
import  student_management.dao.StudentDaoImpl;
import  student_management.entities.Student;
import  student_management.view.StudentView;

/**
 *
 * @author GIANG
 */
public class StudentController {

    private static StudentDaoImpl studentDao;
    private static StudentView studentView;
    private static IStudent studentDao1;

    public StudentController(StudentView view) {
        this.studentView = view;
        studentDao = new StudentDaoImpl();
        //add mouseClicked to JTable
        view.addMouseClickedTable(new AddMouseClickToTableListener());
        //Add Action to button 
        view.addAddStudentListener(new AddStudentListener());
        view.addClearStudentListener(new ClearStudentListener());
        view.addEditStudentListener(new EditStudentListener());
        view.addDeleteStudentListener(new DeleteStudentListener());
        view.addSortStudentByGpaListener(new SortStudentByGpaListener());
        view.addSortStudentByNameListener(new SortStudentByNameListener());
        view.addPhoneFieldKeyTypedListener(new PhoneFieldKeyTypedListener());
        view.addAgeFieldKeyTypedTable(new AgeFieldKeyTypedListener());
        view.addGpaFieldKeyTypedTable(new GpaFieldKeyTypedListener());

    }

    public static void showStudentView() {
        List<Student> studentLists = studentDao.getListStudents();
        for (Student studentList1 : studentLists) {
            System.out.println(studentList1.toString());
        }
        studentView.setVisible(true);
        if (studentLists != null) {
            studentView.showListStudents(studentLists);
        }

    }

 

    private static class AddStudentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Student student = studentView.getStudentInfo();
            if (student != null) {
                studentDao.add(student);
                studentView.showMessage("Thêm mới thành công!");
                studentView.clearStudentInfo();
            }
        }
    }

    private static class ClearStudentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            studentView.clearStudentInfo();
        }

    }

    private static class EditStudentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Student student = studentView.getStudentEdit();
            if (student != null) {
                studentDao.edit(student);
                showStudentView();
                studentView.clearStudentInfo();
                studentView.showMessage(" Cập nhật thành công!");

            }
        }
    }

    private static class DeleteStudentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String id = studentView.getStudentDelete();
            Student student = studentDao.getStudentById(id);

            if (student != null) {
                studentDao.delete(student);
                showStudentView();

                studentView.clearStudentInfo();
                studentView.showMessage(" Xóa thành công sinh viên!");

            }

        }
    }

    private static class SortStudentByGpaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Student> studentList = studentDao.sortStudentByGPA();
            studentView.setVisible(true);
            studentView.clearStudentInfo();
            studentView.showListStudents(studentList);
            studentView.showMessage(" Sắp xếp sinh viên theo Gpa thành công!");

        }
    }

    private static class SortStudentByNameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            List<Student> studentList = studentDao.sortStudentByName();

            studentView.setVisible(true);
            studentView.clearStudentInfo();
            studentView.showListStudents(studentList);
            studentView.showMessage(" Sắp xếp sinh viên theo Name thành công!");
        }
    }

    private static class AddMouseClickToTableListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            String id = studentView.getEditStudent();
            Student student = studentDao.getStudentById(id);
            if (student != null) {
                studentView.setEditStudent(student);
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

    private static class PhoneFieldKeyTypedListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar())) {
                e.consume();
            } }

        @Override
        public void keyPressed(KeyEvent e) {
            }

        @Override
        public void keyReleased(KeyEvent e) {
            }
    }
    private static class AgeFieldKeyTypedListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar())) {
                e.consume();
            } }

        @Override
        public void keyPressed(KeyEvent e) {
            }

        @Override
        public void keyReleased(KeyEvent e) {
            }
    }
 private static class GpaFieldKeyTypedListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar())) {
                e.consume();
            } }

        @Override
        public void keyPressed(KeyEvent e) {
            }

        @Override
        public void keyReleased(KeyEvent e) {
            }
    }

}

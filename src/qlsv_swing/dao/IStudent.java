/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlsv_swing.dao;


import java.util.List;
import qlsv_swing.entity.Student;

/**
 *
 * @author GIANG
 */
public interface IStudent {
    public List<Student> getListStudents();
    public void add(Student student);
    public void edit(Student student);
    public Student getStudentById(String studentID);
    public void delete(Student student);
    public List<Student> sortStudentByName();
    public List<Student>  sortStudentByGPA();
}

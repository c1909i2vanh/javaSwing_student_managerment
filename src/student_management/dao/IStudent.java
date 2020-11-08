/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  student_management.dao;


import java.util.List;
import  student_management.entities.Student;

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

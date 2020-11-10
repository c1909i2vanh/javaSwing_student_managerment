/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import student_management.entities.Student;

/**
 *
 * @author GIANG
 */
public class StudentDaoImpl implements IStudent {

    private Connection databaseConnection;
    CallableStatement cst = null;
    ResultSet rs;
    List<Student> listStudents = new ArrayList<>();

    public StudentDaoImpl() {

    }

    @Override
    public List<Student> getListStudents() {
        List<Student> listStudents = new ArrayList<>();
        try {

            databaseConnection = DatabaseConnection.getInstance().getConnetion();

            cst = databaseConnection.prepareCall("{call sp_getAll}");
            rs = cst.executeQuery();
            while (rs.next()) {

                Student st = new Student(rs.getString("id"),
                        rs.getString("name"), rs.getInt("age"),
                        rs.getInt("gender"), rs.getString("address"),
                        rs.getString("phone"), rs.getFloat("gpa"),
                        rs.getInt("status"));
                listStudents.add(st);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeCallable();
            closeConnect();
        }

        return listStudents;
    }

    @Override
    public void add(Student st) {
        try {
            //    thủ tục lấy số bản ghi trong csdl               
            databaseConnection = DatabaseConnection.getInstance().getConnetion();

            cst = databaseConnection.prepareCall("{call sp_get_count_student(?)}");
            // đăng ký tham số đầu ra
            cst.registerOutParameter(1, Types.INTEGER);
            // chạy thủ tục
            cst.execute();
            String firstId = String.valueOf((cst.getInt(1) > 0) ? cst.getInt(1) + 1 : 1);
            while (firstId.length() < 4) {
                firstId = "0" + firstId;
            }
            String StudentId = "B" + firstId;
            // đóng kết nối
            closeConnect();
            closeCallable();
            databaseConnection = DatabaseConnection.getInstance().getConnetion();
            String sql = "{call sp_addST(?,?,?,?,?,?,?,?)}";
            cst = databaseConnection.prepareCall(sql);
            cst.setString(1, StudentId);
            cst.setString(2, st.getName());
            cst.setInt(3, st.getAge());
            cst.setInt(4, st.getGender());
            cst.setString(5, st.getAddress());
            cst.setString(6, st.getPhone());
            cst.setFloat(7, st.getGpa());
            cst.setInt(8, st.getStatus());

            int number = cst.executeUpdate();
            closeConnect();
            closeCallable();
          
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Student getStudentById(String studentID) {
        try {
            Student st = null;

            databaseConnection = DatabaseConnection.getInstance().getConnetion();

            try {
                cst = databaseConnection.prepareCall("{call sp_get_student_by_id(?)}");
                cst.setString(1, studentID);
                rs = cst.executeQuery();
                if (rs.next()) {
                    st = new Student(rs.getString(1), rs.getString(2),
                            rs.getInt(3), rs.getInt(4),
                            rs.getString(5), rs.getString(6), rs.getFloat(7), rs.getInt(8));
                    return st;
                }

            } catch (SQLException ex) {
                Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeCallable();
                closeConnect();
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void edit(Student st) {
        try {
            System.out.println("cap nhat sv");
            databaseConnection = DatabaseConnection.getInstance().getConnetion();

            // Gọi thủ tục cập nhật sinh viên 
            String sql = "{call sp_editST(?,?,?,?,?,?,?,?)}";
            cst = databaseConnection.prepareCall(sql);
            cst.setString(1, st.getId());
            cst.setString(2, st.getName());
            cst.setInt(3, st.getAge());
            cst.setInt(4, st.getGender());
            cst.setString(5, st.getAddress());
            cst.setString(6, st.getPhone());
            cst.setFloat(7, st.getGpa());
            cst.setInt(8, st.getStatus());
            int number = cst.executeUpdate();
            System.out.println(st.toString());
            if (number > 0) {
                System.out.println("OK");
            } else {
                System.out.println("Khong ok");
            }
            closeConnect();
            closeCallable();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Student student) {
        try {
            databaseConnection = DatabaseConnection.getInstance().getConnetion();

            // Gọi thủ tục cập nhật sinh viên 
            String sql = "{call sp_deleteSt(?,?)}";
            cst = databaseConnection.prepareCall(sql);
            cst.setString(1, student.getId());
            cst.setInt(2, (student.getStatus()) == 1 ? 0 : 1);
            int number = cst.executeUpdate();
            closeConnect();
            closeCallable();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Student> sortStudentByName() {
        List<Student> listStudents = new ArrayList<>();
        listStudents = getListStudents();
        Collections.sort(listStudents, (Student st1, Student st2) -> st2.getName().compareTo(st1.getName()));

        return listStudents;
    }

    @Override
    public List<Student> sortStudentByGPA() {
        List<Student> listStudents = new ArrayList<>();

        listStudents = getListStudents();

        Collections.sort(listStudents, (Student st1, Student st2) -> Float.compare(st1.getGpa(), st2.getGpa()));

        return listStudents;
    }

    public void closeConnect() {
        try {
            databaseConnection.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        databaseConnection = null;
    }

    public void closeCallable() {
        try {
            if (cst != null) {
                cst.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        cst = null;
    }

    public void closeResultSet() {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        rs = null;
    }

}

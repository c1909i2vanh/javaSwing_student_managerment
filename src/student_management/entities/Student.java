/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package   student_management.entities;

import java.util.Scanner;

/**
 *
 * @author GIANG
 */
public class Student {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private int age;
    private int gender;
    private String address;
    private String phone;
    /* điểm trung bình của sinh viên */
    private float gpa;
    private int status;

    public Student() {
    }

    public Student(String id, String name, int age, int gender, String address, String phone, float gpa, int status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.gpa = gpa;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", address=" + address + ", phone=" + phone + ", gpa=" + gpa + ", status=" + status + '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

  
    
    public void insert(){
        Scanner scan = new Scanner(System.in);
       
    
    }
    
}

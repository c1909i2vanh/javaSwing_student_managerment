/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  student_management;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import  student_management.controller.LoginController;
import student_management.controller.HomeController;
import student_management.controller.StudentController;
import  student_management.dao.ILogin;
import  student_management.view.LoginView;
import  student_management.view.RegisterView;
import student_management.view.StudentView;

/**
 *
 * @author GIANG
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
//                StudentView stview = new StudentView();
//                StudentController studentController = new StudentController(stview);
//                studentController.showStudentView();
           // ILogin controller = new LoginController();                     
            // controller.showLoginView();  
                HomeController mainController = new HomeController();
                mainController.showMainView();
               
            }

        });
    }

}

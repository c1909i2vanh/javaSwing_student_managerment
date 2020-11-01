/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlsv_swing;

import java.awt.EventQueue;
import qlsv_swing.controller.LoginController;
import qlsv_swing.view.LoginView;

/**
 *
 * @author GIANG
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       EventQueue.invokeLater(new Runnable(){

           @Override
           public void run() {
               LoginView view = new LoginView();
               LoginController controller = new LoginController(view);
               controller.showLoginView();
               }
       
       });
    }
    
}

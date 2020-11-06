/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlsv_swing.dao;

import qlsv_swing.entity.User;

/**
 *
 * @author GIANG
 */
public class UserDao {

    public boolean checkUser(User user) {
        if (user != null) {
            if (user.getUserName().equals("1")&&user.getPassword().equals("1")){
                return true;
            }
        }
      return false;
    }
}

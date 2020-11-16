/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student_management.dao;

import java.util.List;
import student_management.entities.Role;

/**
 *
 * @author GIANG
 */
public interface IRole {

    public List<Role> getAllRole();
    public Role getRoleByRoleName(String roleName);
    public void closeDatabaseConnection();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.User;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListUser {
    private List<User> users;

    public ListUser() {
        users = new ArrayList<>();
    }

    /**
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }
    
    public void addUser(User user) {
        boolean existe = false;
        for (User u : users) {
            if (u.getUser().equals(user.getUser())) {
                existe = true;
            }
        }
        
        if (!existe)
            users.add(user);
        else
            JOptionPane.showMessageDialog(null, "La Persona Ya Existe");
    }
    
    public boolean addNewUser(User user) {
        boolean existe = false;
        for (User u : users) {
            if (u.getUser().equals(user.getUser())) {
                existe = true;
            }
        }

        users.add(user);
        return existe;
    }
    
    public void deleteUser(String user) {        
        for (User u : users) {
            if (u.getUser().equals(user)) {
                users.remove(u);
                break;
            }
        }
    }
    
    public boolean updateUser(User user, String nameUser) {
        for (User u : users) {
            if (u.getUser().equals(nameUser)) {
                u.setUser(nameUser);
                u.setPerson(user.getPerson());
                u.setRolUser(user.getRolUser());
                u.setPassword(user.getPassword());
                
                return true;
            }
        }
        return false;
    }
    
    public User existeUser(String user, String password) {
        for (User u : users) {
            if (u.getUser().equals(user) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
    
    public User getUserByUser(String user) {
        for (User u : users) {
            if (u.getUser().equals(user)) {
                return u;
            }
        }
        return null;
    }
}

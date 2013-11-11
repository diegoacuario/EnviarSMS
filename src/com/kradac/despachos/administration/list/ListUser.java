/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.User;
import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListUser {
    private DataBase db;
    private List<User> users;

    public ListUser() {
        this.users = new ArrayList<>();
        this.db = new DataBase(Principal.fileConfig, Principal.numHost);
    }

    public ListUser(Properties p, int numHost) {
        this.users = new ArrayList<>();
        this.db = new DataBase(p, numHost);
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
    
    public int addNewUser(User user) {

        int existe = -1;
        for (User u : users) {
            if (u.getUser().equals(user.getUser())) {
                existe = 0;
            }
        }
        if (existe == -1) {
            if (db.insertUser(user)) {
                existe = 1;
                users.add(user);
            }
        }
        return existe;
    }

    public boolean deleteUser(String user) {
        for (User u : users) {
            if (u.getUser().equals(user)) {
                if (db.deleteUser(user)) {
                    users.remove(u);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean updateUser(User user, String nameUser) {
        for (User u : users) {
            if (u.getUser().equals(nameUser)) {
                if (db.updateUser(user, nameUser)) {
                    u.setUser(nameUser);
                    u.setPerson(user.getPerson());
                    u.setRolUser(user.getRolUser());
                    u.setPassword(user.getPassword());
                    return true;
                }
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

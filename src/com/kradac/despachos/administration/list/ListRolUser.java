/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.RolUser;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListRolUser {
    private List<RolUser> rolUsers;

    public ListRolUser() {
        rolUsers = new ArrayList<>();
    }
    
    public void addRolUser(RolUser rolUser) {
        boolean existe = false;
        for (RolUser rU : getRolUsers()) {            
            if (rU.getIdRolUser() == rolUser.getIdRolUser()) {
                existe = true;
            }
        }
        
        if (!existe)
            getRolUsers().add(rolUser);
        else {
            JOptionPane.showMessageDialog(null, "No pueden haber dos Roles de Usuario Identicos");
            System.exit(0);
        }
    }
    
    public RolUser getRolUser(int idRolUser) {
        for (RolUser rU : getRolUsers()) {
            if (rU.getIdRolUser() == idRolUser) {
                return rU;
            }
        }
        return null;
    }
    
    public RolUser getRolUserByRol(String rolUser) {
        for (RolUser rU : getRolUsers()) {
            if (rU.getRolUser().equals(rolUser)) {
                return rU;
            }
        }
        return null;
    }

    /**
     * @return the rolUsers
     */
    public List<RolUser> getRolUsers() {
        return rolUsers;
    }
}

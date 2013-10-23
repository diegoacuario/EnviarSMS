/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration;

/**
 *
 * @author Dalton
 */
public class RolUser {
    private int idRolUser;
    private String rolUser;
    private String detalle;

    public RolUser(int idRolUser, String rolUser, String detalle) {
        this.idRolUser = idRolUser;
        this.rolUser = rolUser;
        this.detalle = detalle;
    }

    /**
     * @return the idRolUser
     */
    public int getIdRolUser() {
        return idRolUser;
    }

    /**
     * @param idRolUser the idRolUser to set
     */
    public void setIdRolUser(int idRolUser) {
        this.idRolUser = idRolUser;
    }

    /**
     * @return the rolUser
     */
    public String getRolUser() {
        return rolUser;
    }

    /**
     * @param rolUser the rolUser to set
     */
    public void setRolUser(String rolUser) {
        this.rolUser = rolUser;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
    
}

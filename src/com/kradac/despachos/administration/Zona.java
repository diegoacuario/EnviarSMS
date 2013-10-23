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
public class Zona {
    private int idZona;
    private String zona;

    public Zona(int idZona, String zona) {
        this.idZona = idZona;
        this.zona = zona;
    }

    /**
     * @return the idZona
     */
    public int getIdZona() {
        return idZona;
    }

    /**
     * @param idZona the idZona to set
     */
    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    /**
     * @return the zona
     */
    public String getZona() {
        return zona;
    }

    /**
     * @param zona the zona to set
     */
    public void setZona(String zona) {
        this.zona = zona;
    }
}

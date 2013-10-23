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
public class Marca {
    private int idMarcaVehiculo;
    private String marcaVehiculo;

    public Marca(int idMarcaVehiculo, String marcaVehiculo) {
        this.idMarcaVehiculo = idMarcaVehiculo;
        this.marcaVehiculo = marcaVehiculo;
    }

    /**
     * @return the idMarcaVehiculo
     */
    public int getIdMarcaVehiculo() {
        return idMarcaVehiculo;
    }

    /**
     * @param idMarcaVehiculo the idMarcaVehiculo to set
     */
    public void setIdMarcaVehiculo(int idMarcaVehiculo) {
        this.idMarcaVehiculo = idMarcaVehiculo;
    }

    /**
     * @return the marcaVehiculo
     */
    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    /**
     * @param marcaVehiculo the marcaVehiculo to set
     */
    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }
}

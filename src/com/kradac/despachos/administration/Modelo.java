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
public class Modelo {
    private int idModeloVehiculo;
    private Marca marca;
    private String modeloVehiculo;

    public Modelo(int idModeloVehiculo, Marca marca, String modeloVehiculo) {
        this.idModeloVehiculo = idModeloVehiculo;
        this.marca = marca;
        this.modeloVehiculo = modeloVehiculo;
    }

    /**
     * @return the idModeloVehiculo
     */
    public int getIdModeloVehiculo() {
        return idModeloVehiculo;
    }

    /**
     * @param idModeloVehiculo the idModeloVehiculo to set
     */
    public void setIdModeloVehiculo(int idModeloVehiculo) {
        this.idModeloVehiculo = idModeloVehiculo;
    }

    /**
     * @return the marca
     */
    public Marca getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    /**
     * @return the modeloVehiculo
     */
    public String getModeloVehiculo() {
        return modeloVehiculo;
    }

    /**
     * @param modeloVehiculo the modeloVehiculo to set
     */
    public void setModeloVehiculo(String modeloVehiculo) {
        this.modeloVehiculo = modeloVehiculo;
    }
}

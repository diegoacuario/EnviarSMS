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
public class Vehiculo {
    private String placa;
    private Zona zona;
    private Person conductor;
    private Person propietario;
    private int vehiculo;
    private Modelo modelo;
    private int year;
    private String numMotor;
    private String numChasis;
    private int regMunicipal;
    private String soat;
    private CodesTaxy codesTaxy;
    private String image;
    private boolean bloqueo;
    private String ip;

    public Vehiculo(String placa, Zona zona, Person conductor, Person propietario, int vehiculo, Modelo modelo, int year, String numMotor, String numChasis, int regMunicipal, String soat, CodesTaxy codesTaxy,String image, String ip) {
        this.placa = placa;
        this.zona = zona;
        this.conductor = conductor;
        this.propietario = propietario;
        this.vehiculo = vehiculo;
        this.modelo = modelo;
        this.year = year;
        this.numMotor = numMotor;
        this.numChasis = numChasis;
        this.regMunicipal = regMunicipal;
        this.soat = soat;
        this.codesTaxy = codesTaxy;
        this.image = image;
        this.ip = ip;
    }
    
    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    /**
     * @return the zona
     */
    public Zona getZona() {
        return zona;
    }

    /**
     * @param zona the zona to set
     */
    public void setZona(Zona zona) {
        this.zona = zona;
    }

    /**
     * @return the conductor
     */
    public Person getConductor() {
        return conductor;
    }

    /**
     * @param conductor the conductor to set
     */
    public void setConductor(Person conductor) {
        this.conductor = conductor;
    }

    /**
     * @return the propietario
     */
    public Person getPropietario() {
        return propietario;
    }

    /**
     * @param propietario the propietario to set
     */
    public void setPropietario(Person propietario) {
        this.propietario = propietario;
    }

    /**
     * @return the vehiculo
     */
    public int getVehiculo() {
        return vehiculo;
    }

    /**
     * @param vehiculo the vehiculo to set
     */
    public void setVehiculo(int vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * @return the modelo
     */
    public Modelo getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the numMotor
     */
    public String getNumMotor() {
        return numMotor;
    }

    /**
     * @param numMotor the numMotor to set
     */
    public void setNumMotor(String numMotor) {
        this.numMotor = numMotor;
    }

    /**
     * @return the numChasis
     */
    public String getNumChasis() {
        return numChasis;
    }

    /**
     * @param numChasis the numChasis to set
     */
    public void setNumChasis(String numChasis) {
        this.numChasis = numChasis;
    }

    /**
     * @return the regMunicipal
     */
    public int getRegMunicipal() {
        return regMunicipal;
    }

    /**
     * @param regMunicipal the regMunicipal to set
     */
    public void setRegMunicipal(int regMunicipal) {
        this.regMunicipal = regMunicipal;
    }

    /**
     * @return the soat
     */
    public String getSoat() {
        return soat;
    }

    /**
     * @param soat the soat to set
     */
    public void setSoat(String soat) {
        this.soat = soat;
    }

    /**
     * @return the codesTaxy
     */
    public CodesTaxy getCodesTaxy() {
        return codesTaxy;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @param codesTaxy the codesTaxy to set
     */
    public void setCodesTaxy(CodesTaxy codesTaxy) {
        this.codesTaxy = codesTaxy;
    }

    /**
     * @return the bloque
     */
    public boolean isBloqueo() {
        return bloqueo;
    }

    /**
     * @param bloqueo the bloque to set
     */
    public void setBloqueo(boolean bloqueo) {
        this.bloqueo = bloqueo;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}

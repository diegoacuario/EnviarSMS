/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration;

/**
 *
 * @author kradac
 */
public class PendingDiarios {
    private int code;
    private String cliente;
    private String fechaIni;
    private String fechaFin;
    private String hora;
    private String nota;
    private int minRecuerdo;
    private String cuandoRecordar;
    private String phone;

    public PendingDiarios(int code, String cliente, String fechaIni, String fechaFin, String hora, String nota, int minRecuerdo, String cuandoRecordar, String phone) {
        this.code = code;
        this.cliente = cliente;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.hora = hora;
        this.nota = nota;
        this.minRecuerdo = minRecuerdo;
        this.cuandoRecordar = cuandoRecordar;
        this.phone = phone;
    }
    

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the fechaIni
     */
    public String getFechaIni() {
        return fechaIni;
    }

    /**
     * @param fechaIni the fechaIni to set
     */
    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    /**
     * @return the fechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return the nota
     */
    public String getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(String nota) {
        this.nota = nota;
    }

    /**
     * @return the minRecuerdo
     */
    public int getMinRecuerdo() {
        return minRecuerdo;
    }

    /**
     * @param minRecuerdo the minRecuerdo to set
     */
    public void setMinRecuerdo(int minRecuerdo) {
        this.minRecuerdo = minRecuerdo;
    }

    /**
     * @return the cuandoRecordar
     */
    public String getCuandoRecordar() {
        return cuandoRecordar;
    }

    /**
     * @param cuandoRecordar the cuandoRecordar to set
     */
    public void setCuandoRecordar(String cuandoRecordar) {
        this.cuandoRecordar = cuandoRecordar;
    }

    /**
     * @return the phone
     */
    public String getEstado() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setEstado(String phone) {
        this.phone = phone;
    }

    
}

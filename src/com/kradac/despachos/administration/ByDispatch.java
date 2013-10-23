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
public class ByDispatch {
    private String time;
    private String phone;
    private int code;
    private String client;
    private String sector;
    private String direction;
    private int vehiculo;
    private int minute;
    private String timeAsig;
    private String placa;
    private int atraso;
    private String note;
    private double latitud;
    private double longitud;

    public ByDispatch(String time, String phone, int code, String client, String sector, String direction, int vehiculo, int minute, String timeAsig, String placa, int atraso, String note, double latitud, double longitud) {
        this.time = time;
        this.phone = phone;
        this.code = code;
        this.client = client;
        this.sector = sector;
        this.direction = direction;
        this.vehiculo = vehiculo;
        this.minute = minute;
        this.timeAsig = timeAsig;
        this.placa = placa;
        this.atraso = atraso;
        this.note = note;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public ByDispatch(String time, String phone, int code, String client, String sector, String direction, int vehiculo, int minute, String timeAsig, String placa, int atraso, String note) {
        this.time = time;
        this.phone = phone;
        this.code = code;
        this.client = client;
        this.sector = sector;
        this.direction = direction;
        this.vehiculo = vehiculo;
        this.minute = minute;
        this.timeAsig = timeAsig;
        this.placa = placa;
        this.atraso = atraso;
        this.note = note;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
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
     * @return the client
     */
    public String getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return the sector
     */
    public String getSector() {
        return sector;
    }

    /**
     * @param sector the sector to set
     */
    public void setSector(String sector) {
        this.sector = sector;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
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
     * @return the minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * @param minute the minute to set
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * @return the timeAsig
     */
    public String getTimeAsig() {
        return timeAsig;
    }

    /**
     * @param timeAsig the timeAsig to set
     */
    public void setTimeAsig(String timeAsig) {
        this.timeAsig = timeAsig;
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
     * @return the atraso
     */
    public int getAtraso() {
        return atraso;
    }

    /**
     * @param atraso the atraso to set
     */
    public void setAtraso(int atraso) {
        this.atraso = atraso;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
    
}

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
public class MessageToGarmin {
    private int vehiculo;
    private String client;
    private String direction;
    private String sector;
    private int tiquete;
    private int code;
    private boolean send;

    public MessageToGarmin(int vehiculo, String client, String direction, String sector, int tiquete, int code, boolean send) {
        this.vehiculo = vehiculo;
        this.client = client;
        this.direction = direction;
        this.sector = sector;
        this.tiquete = tiquete;
        this.code = code;
        this.send = send;
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
     * @return the tiquete
     */
    public int getTiquete() {
        return tiquete;
    }

    /**
     * @param tiquete the tiquete to set
     */
    public void setTiquete(int tiquete) {
        this.tiquete = tiquete;
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
     * @return the send
     */
    public boolean isSend() {
        return send;
    }

    /**
     * @param send the send to set
     */
    public void setSend(boolean send) {
        this.send = send;
    }
    
}

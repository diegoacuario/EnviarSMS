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
public class MessageToAndroid {
    private int idSolicitud;
    private int vehiculo;
    private boolean send;

    public MessageToAndroid(int idSolicitud, int vehiculo, boolean send) {
        this.idSolicitud = idSolicitud;
        this.vehiculo = vehiculo;
        this.send = send;
    }
    
    

    /**
     * @return the idSolicitud
     */
    public int getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
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

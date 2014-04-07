/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Client;
import com.kradac.despachos.administration.Dispatch;
import com.kradac.despachos.administration.MessageToAndroid;
import com.kradac.despachos.administration.MessageToGarmin;
import com.kradac.despachos.interfaz.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dalton
 */
public class ListMessageToServer {

    private List<MessageToGarmin> postsGarmin;
    private List<MessageToAndroid> postsAndroid;
    private List<Dispatch> postsAssigs;

    public ListMessageToServer() {
        postsGarmin = new ArrayList();
        postsAndroid = new ArrayList();
        postsAssigs = new ArrayList();
    }

    public void addMessageGarmin(MessageToGarmin message) {
        postsGarmin.add(message);
    }
    
    public void addMessageAndroid(MessageToAndroid message) {
        postsAndroid.add(message);
    }
    public void addMessageAssigs(Dispatch message) {
        postsAssigs.add(message);
    }
    
    public MessageToAndroid getMessageToSendAndroid() {
        for (MessageToAndroid m : postsAndroid) {
            if (!m.isSend()) {
                m.setSend(true);
                return m;
            }
        }
        return null;
    }
    
    public Dispatch getMessageToSendAssigs() {
        for (Dispatch d : postsAssigs) {
            if (!d.isSend()) {
                d.setSend(true);
                return d;
            }
        }
        return null;
    }

    public String[] getMessageToSendGarmin() {
        for (MessageToGarmin m : postsGarmin) {
            if (!m.isSend()) {
                Client c = Principal.listClient.getClientByCode(m.getCode());
                double latitud, longitud, p;
                String fechaDif = Principal.bd.getFeDif();
                String fechaDifHex, latF, lonF;
                long latG, lonG;

                latitud = c.getLatitud();
                longitud = c.getLongitud();
                fechaDifHex = Integer.toHexString(Integer.parseInt(fechaDif));

                /*Redondear decimal
                 BigDecimal lat = new BigDecimal(latitud);
                 BigDecimal lon = new BigDecimal(longitud);
                 lat = lat.setScale(6, RoundingMode.HALF_UP);
                 lon = lon.setScale(6, RoundingMode.HALF_UP);
                 */
                //Transforma a Hexadecimal para enviar
                if (latitud < 0) {
                    latitud = latitud + 360;
                    p = Math.pow(2, 31) / 180;
                    latitud = latitud * p;
                    latG = (long) latitud;
                    latF = Long.toHexString(latG);
                } else {
                    p = Math.pow(2, 31) / 180;
                    latitud = latitud * p;
                    latG = (long) latitud;
                    latF = Long.toHexString(latG);
                }

                if (longitud < 0) {
                    longitud = longitud + 360;
                    p = Math.pow(2, 31);
                    p = p / 180;
                    longitud = longitud * p;
                    lonG = (long) longitud;
                    lonF = Long.toHexString(lonG);
                } else {
                    p = Math.pow(2, 31) / 180;
                    longitud = longitud * p;
                    lonG = (long) longitud;
                    lonF = Long.toHexString(lonG);
                }
                /**
                 * Trama para que sean leidas por el taximetro, separadas por %
                 */
                String mensaje = "";
                if (m.getSector().equals("") || m.getSector().equals("0")) {
                    m.setSector(" ");
                }

                mensaje = "" + m.getClient() + "%" + m.getSector() + "%" + m.getDirection() + "%"
                        + latF + "%" + lonF + "%" + m.getTiquete() + "%" + fechaDifHex + "%" + fechaDif;
                
                String [] paquete = new String[2];
                paquete[0] = ""+m.getVehiculo();
                paquete[1] = mensaje;
                
                m.setSend(true);
                return paquete;
            }
        }
        return null;
    }
}

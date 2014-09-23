/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.interfaz;

import com.kradac.despachos.comm.central.ConexionModem;

/**
 *
 * @author KRADAC
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConexionModem cm = new ConexionModem();
        cm.start();
        String celular = "0969748969";
        String mensaje = "KRADAC";
        cm.enviarDatos("APZ&F" + (char) (13));
        cm.enviarDatos("AT+CMGF=1" + (char) (13));
        cm.enviarDatos("AT+CMGS=" + (char) (34) + (celular) + (char) (34) + (char) (13));
        cm.enviarDatos(mensaje + (char) 26);
        cm.enviarDatos("AT+CMGD=1,4;");

    }

}

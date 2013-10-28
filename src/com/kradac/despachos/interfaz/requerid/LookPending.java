/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.interfaz.requerid;

import com.kradac.despachos.interfaz.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dalton
 */
public class LookPending extends Thread{
    private final String hora;
    private final String nombre_apellido;
    private int c = 1;

    public LookPending(String hora, String nombre_apellido) {
        this.hora = hora;
        this.nombre_apellido = nombre_apellido;        
    }

    @Override
    public void run() {
        while (c <= 70) {
            try {
                if (c <= 69) {                    
                    Principal.lblMinuteSlope.setText(hora);
                    Principal.lblClientSlope.setText(nombre_apellido);
                    Principal.lblSlope.setVisible(true);
                    Principal.jpSlope.setVisible(true);
                    c++;
                } else {
                    Principal.lblSlope.setVisible(false);
                    Principal.jpSlope.setVisible(false);
                }
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(LookPending.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.threads;

import com.kradac.despachos.conections.LastGps;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dalton
 */
public class ThreadReconexion extends Thread {

    int i;

    public ThreadReconexion() {
        i = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                i++;
                if (i == 10) {
                    i = 1;
                }
                
                if (i == 1) {
                    LastGps tl = new LastGps();
                }

                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadReconexion.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}

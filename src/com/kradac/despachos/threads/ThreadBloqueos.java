/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.threads;

import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dalton
 */
public class ThreadBloqueos extends Thread{
    int c = 0;

    @Override
    public void run() {
        while (true) {            
            try {
                if (c == 0) {
                    DataBase db = new DataBase(Principal.fileConfig, Principal.host);
                    db.getStateVehiculoPendientePago();
                    db.closeConexion();
                }
                c++;
                if (c == 10) {
                    c = 0;
                }
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadBloqueos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}

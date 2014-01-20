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
public class ThreadTrash extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                DataBase db = new DataBase(Principal.fileConfig, Principal.numHost);
                db.loadTrash();
                db.closeConexion();

                sleep(20000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadTrash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

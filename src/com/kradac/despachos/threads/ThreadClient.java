package com.kradac.despachos.threads;

import com.kradac.despachos.interfaz.Principal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dalton
 */
public class ThreadClient extends Thread {

    public ThreadClient() {
        System.out.println("Hilo Corriendo");
    }

    @Override
    public void run() {
        Principal.listClient = Principal.bd.loadClients();
    }
    
}

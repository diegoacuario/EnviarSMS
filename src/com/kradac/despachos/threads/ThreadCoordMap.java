/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.threads;

import com.kradac.despachos.interfaz.FrameClients;
import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ThreadCoordMap extends Thread {

    private static final int puerto = 65000;
    boolean escuchar = true;
    private ServerSocket ss;
    private Socket s;
    
    public ThreadCoordMap() {
        this.start();
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(puerto);
            InputStream is;
            s = ss.accept();
            char caracter;

            int contarSeparador = 0;
            String datosMapa = "";
            int cod;

            while (escuchar) {
                try {
                    is = s.getInputStream();

                    cod = is.read();
                    if (cod == 10) {
                        s.close();
                        s = ss.accept();
                        datosMapa = "";
                        contarSeparador = 0;
                    } else {
                        caracter = (char) cod;

                        if (caracter == '%') {
                            contarSeparador++;
                        }
                        if (contarSeparador == 2) {
                            procesarCoordenadas(datosMapa);
                        } else {
                            datosMapa += "" + caracter;
                        }

                    }
                } catch (IOException ex) {
                    // skip this connection; continue with the next
                }
            }
        } catch(BindException be){
            //JOptionPane.showMessageDialog(null, "Dirección de Puerto para obtener coodenadas ya esta en Uso por Otra Aplicación", "ERROR", JOptionPane.ERROR_MESSAGE);
            cerrarConexion();
        }catch (IOException ex) {
            Logger.getLogger(ThreadCoordMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarConexion() {
        try {
            s.close();
            ss.close();
            this.escuchar = false;
        } catch (IOException | NullPointerException ex) {
        }
    }

    private static void procesarCoordenadas(String datos) {
        String[] coord = datos.split("%");

        try {
            FrameClients.setCoordMap(coord[1], coord[0]);
        } catch (NullPointerException ex) {
        }
    }
}

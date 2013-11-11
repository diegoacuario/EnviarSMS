/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.conections;

import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ChannelLastGps extends Thread {

    private static Socket s;
    private static BufferedReader input;
    private static PrintStream output;
    private static int port;
    private static String ip;
    private DataBase db;
    private boolean isConected = false;

    public ChannelLastGps() {
        try {
            ip = Principal.fileConfig.getProperty("ip_server");
            port = Integer.parseInt(Principal.fileConfig.getProperty("puerto_server"));
            db = new DataBase(Principal.fileConfig, Principal.numHost);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Verifique el Puerto de su Archivo de Configuración", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!isConected) {
                    openConexion();
                    input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    output = new PrintStream(s.getOutputStream(), true);
                }

                try {
                    output.print("$$1##"+Principal.company.getIdCompany()+"$$\n");
                    String response = input.readLine();
                    if (response != null) {
                        ArrayList<String> newData = new ArrayList();
                        
                        if (response.contains("#")) {
                            String[] pos = response.split("#");
                            newData.addAll(Arrays.asList(pos));
                        }
                        
                        for (String trama : newData) {
                            saveDataVehiculo(trama);                                                               
                        }
                    }
                    sleep(10000);
                } catch (IOException ex) {
                    //System.err.println("Conexion Perdida con el Servidor");
                    closeConexion();
                } catch (NullPointerException ex) {
                    try {
                        //System.err.println("No hay Conexion Determinada");
                        sleep(5000);
                    } catch (InterruptedException ex1) {
                        Logger.getLogger(ChannelLastGps.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ChannelLastGps.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(ChannelLastGps.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void openConexion() {
        try {
            try {
                s = new Socket(ip, port);
                isConected = true;
                //System.out.println("Conexion Establecida Correctamente.");
            } catch (UnknownHostException ex) {
                //System.out.println("Aqui: " + ex.getMessage());
            } catch (IOException ex) {
                //System.err.println("No es posible conectarse con el Servidor");
                try {
                    sleep(5000);
                    openConexion();
                } catch (InterruptedException ex1) {
                }
            }
        } catch (StackOverflowError m) {
        }
    }

    public void closeConexion() {
        try {
            try {
                output.close();
                input.close();
            } catch (NullPointerException ex) {
            }
            try {
                s.close();
                isConected = false;
                //System.err.println("Canal Cerrado con el Servidor");
            } catch (NullPointerException ex) {
            }
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
    }
    
    private void saveDataVehiculo(String datoVehiculo) {
        String[] recorrido = datoVehiculo.split(",");
        
        /**
         * ---- ID PARTICION: 20100909 N_UNIDAD: 43 ID_EMPRESA: LN LAT: -3.99473
         * LON: -79.2105116666667 FECHA: 2010-09-09 HORA: 09:44:36 VEL: 0.14 G1:
         * estado del TAXIMETRO pudiendo ser 1 (ON) || 0 (OFF) G2: estado del
         * TAXI pudiendo ser 0 (LIBRE) || 1 (OCUPADO)
         * UNIDAD_BLOQUEADA pudiendo ser 1 (ACTIVO) || 0 (BLOQUEADO)
         */                
        try {
            
            db.insertLastGps(
                    recorrido[1],
                    recorrido[3],
                    recorrido[4],
                    recorrido[5],
                    recorrido[6],
                    recorrido[7],
                    recorrido[8],
                    recorrido[9],
                    recorrido[10]);
        } catch (ArrayIndexOutOfBoundsException ex) {
        } 
    }
}

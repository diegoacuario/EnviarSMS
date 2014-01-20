/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.conections;

import com.kradac.despachos.administration.Call;
import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
import com.kradac.despachos.methods.Functions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ChannelMessageFromServer extends Thread {

    private static Socket s;
    private static BufferedReader input;
    private static PrintStream output;
    private static int port;
    private static String ip;
    private DataBase db;
    private boolean isConected = false;

    public ChannelMessageFromServer() {
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
                    output.print("$$3##" + Principal.company.getIdCompany() + "$$\n");
                }

                try {
                    String data = input.readLine();
                    if (data != null) {
                        data = data.replace("$$", "");
                        String[] campos = data.split("##");
                        int typeMsg = Integer.parseInt(campos[0]);
                        switch (typeMsg) {
                            case 1:
                                clientAndroid(campos[1]);
                                break;
                            case 2:
                                stateTaximetro(campos[1]);
                                break;
                            default:
                                System.err.println("Trama no procesada");
                                break;
                        }
                    }
                    sleep(1000);
                } catch (IOException ex) {
                    //System.err.println("Conexion Perdida con el Servidor");
                    closeConexion();
                } catch (NullPointerException ex) {
                    try {
                        //System.err.println("No hay Conexion Determinada");
                        sleep(5000);
                    } catch (InterruptedException ex1) {
                        Logger.getLogger(ChannelMessageFromServer.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ChannelMessageFromServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(ChannelMessageFromServer.class.getName()).log(Level.SEVERE, null, ex);
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

    private void clientAndroid(String message) {
        String[] dataClient = message.split(";");
        Call c = new Call(Integer.parseInt(dataClient[0]), Functions.getDate(), Functions.getTime(), dataClient[1], "Android", dataClient[2],
                dataClient[3], dataClient[4],
                Double.parseDouble(dataClient[5]), Double.parseDouble(dataClient[6]));
        db.insertCall(c);
        Principal.listCall.addCall(c);
        Principal.tblCall.setRowSelectionInterval(0, 0);
        Principal.tblCall.requestFocus();
    }

    private void stateTaximetro(String message) {
        String[] dataVehiculo = message.split(";");
        Principal.listVehiculos.setCodeTaxyByEtiqueta(Integer.parseInt(dataVehiculo[1]), Principal.listCodesTaxy.getCodesTaxyById(dataVehiculo[0]));
        Principal.paintStateTaxy();
    }
}

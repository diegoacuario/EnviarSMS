/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.conections;

import com.kradac.despachos.administration.Dispatch;
import com.kradac.despachos.administration.MessageToAndroid;
import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
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
public class ChannelMessageToServer extends Thread {

    private static Socket s;
    private static BufferedReader input;
    private static PrintStream output;
    private static int port;
    private static String ip;

    public ChannelMessageToServer() {
        try {
            ip = Principal.fileConfig.getProperty("ip_server");
            port = Integer.parseInt(Principal.fileConfig.getProperty("puerto_server"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Verifique el Puerto de su Archivo de Configuración", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String[] paquete = Principal.listMessageToServer.getMessageToSendGarmin();
                MessageToAndroid mta = Principal.listMessageToServer.getMessageToSendAndroid();
                Dispatch dispatch = Principal.listMessageToServer.getMessageToSendAssigs();
                if (paquete != null) {
                    openConexion();
                    input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    output = new PrintStream(s.getOutputStream(), true);

                    output.print("$$2##" + Principal.company.getIdCompany() + "##" + paquete[0] + "##" + paquete[1] + "$$\n");
                    String response = input.readLine();
                    if (response != null) {
                        Principal.modelListEvents.add(0, "=> " + response);
                        closeConexion();
                    }
                }

                if (mta != null) {
                    openConexion();
                    input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    output = new PrintStream(s.getOutputStream(), true);

                    output.print("$$2##" + Principal.company.getIdCompany() + "##" + mta.getVehiculo() + "##" + mta.getIdSolicitud() + "$$\n");
                    String response = input.readLine();
                    if (response != null) {
                        Principal.modelListEvents.add(0, "=> " + response);
                        closeConexion();
                    }
                }

                if (dispatch != null) {
                    openConexion();
                    input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    output = new PrintStream(s.getOutputStream(), true);

                    String message = Principal.company.getIdCompany() + ";" + Principal.userLogin.getUser() + ";" + dispatch.getDate() + " " + dispatch.getTime() + ";"
                            + dispatch.getPhone() + ";" + dispatch.getCode() + ";" + dispatch.getClient() + ";" + dispatch.getSector() + ";"
                            + dispatch.getDirection() + ";" + dispatch.getDestino() + ";" + dispatch.getVehiculo() + ";" + dispatch.getMinute() + ";"
                            + dispatch.getTimeAsig() + ";" + dispatch.getPlaca() + ";" + dispatch.getAtraso() + ";" + dispatch.getNum_house() + ";"
                            + dispatch.getReference() + ";" + dispatch.getNote() + ";" + dispatch.getLine() + ";" + dispatch.getLatitud() + ";" + dispatch.getLongitud();

                    output.print("$$4##" + Principal.company.getIdCompany() + "##" + dispatch.getVehiculo() + "##" + message + "$$\n");
                    String response = input.readLine();
                    if (response != null) {
                        Principal.modelListEvents.add(0, "=> " + response);
                        closeConexion();
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
                    Logger.getLogger(ChannelMessageToServer.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ChannelMessageToServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void openConexion() {
        try {
            try {
                s = new Socket(ip, port);
                //System.out.println("Conexion Establecida Correctamente.");
            } catch (UnknownHostException ex) {
                //System.out.println("Aqui: " + ex.getMessage());
            } catch (IOException ex) {
                //System.err.println("No es posible conectarse con el Servidor");
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
                //System.err.println("Canal Cerrado con el Servidor");
            } catch (NullPointerException ex) {
            }
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
    }
}

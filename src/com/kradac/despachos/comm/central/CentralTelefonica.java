/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.comm.central;

import com.kradac.despachos.administration.Call;
import com.kradac.despachos.administration.Client;
import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
import com.kradac.despachos.methods.Functions;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class CentralTelefonica extends Thread {

    private CommPortIdentifier id_Puerto;
    private SerialPort sPuerto;
    public Enumeration listaPuertos;
    private DataBase db;
    private String port;

    String strWin32 = "win32com.dll";
    String strComm = "comm.jar";
    String strProp = "javax.comm.properties";

    public CentralTelefonica() {
        db = new DataBase(Principal.fileConfig, Principal.numHost);
        port = Principal.fileConfig.getProperty("comm");
        if (!port.equals("0")) {
            if (!AbrirPuerto(port)) {
                JOptionPane.showMessageDialog(null, "No se pudo abrir el puerto COM: " + port, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            if (!setParametros(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE)) {
                JOptionPane.showMessageDialog(null, "No se pudo setear el puerto con los parametros por defecto... " + port, "ERROR", JOptionPane.ERROR_MESSAGE);
                CerrarPuerto();
            }
        }
    }

    @Override
    public void run() {
        int caracAssi;
        String dato;
        String trama = "";
        String tramaFecha;
        String incognito = "";
        int incognito2 = 0;

        while (true) {
            caracAssi = leerDatosCode();
            dato = "" + (char) caracAssi;
            trama = trama + dato;

            if (caracAssi == 10 && trama.length() > 10) {
                if (trama.contains("Fecha") || trama.contains("Hora") || trama.contains("-")) {
                    trama = "";
                } else {
                    tramaFecha = trama.substring(0, 10);
                    trama = trama.substring(11);
                    trama = trama.replace(" ", "%");
                    trama = trama.replace("%%%%%%%", "%");
                    trama = trama.replace("%%%", "%");
                    trama = trama.replace("%%", "%");
                    if (trama.contains("entrada")) {
                        trama = trama.replace("%<%entrada%%>", "%");
                    } else {
                        trama = trama.replace("%<%E%>", "%");
                    }
                    
                    trama = trama.replace("'", ":");
                    
                    String parte[] = trama.split("%");

                    String fecha = procesoFecha(tramaFecha);
                    String hora = parte[0].substring(0, parte[0].length() - 2);
                    int extension = 0;
                    String linea = parte[1];
                    String telefono = parte[2];
                    String duracion = "00:00:00";

                    if (telefono.length() > 8) {
                        if (parte.length == 7) {
                            fecha = procesoFecha(tramaFecha);
                            hora = parte[0].substring(0, parte[0].length() - 2);
                            extension = Integer.parseInt(parte[1]);
                            linea = parte[2];
                            telefono = parte[3];
                            duracion = parte[4].substring(0, parte[4].length() - 1);
                        }

                        if (parte.length == 9) {
                            fecha = procesoFecha("secundaria: " + tramaFecha);
                            hora = parte[0].substring(0, parte[0].length() - 2);
                            extension = Integer.parseInt(parte[1]);
                            linea = parte[2];
                            telefono = parte[3];
                            duracion = parte[4].substring(0, parte[4].length() - 1);
                            incognito = parte[5];
                            incognito2 = Integer.parseInt(parte[6]);
                        }

                        if (parte.length != 9) {
                            timbrar(true, telefono, linea);
                        }

                        if (extension != 0) {
                            if (parte.length == 9) { //Llamada realizada
                                Principal.modelListEvents.add(0, "=> Llamada Realizada de la linea '" + linea + "' al numero: " + telefono);
                            } else {
                                timbrar(false, telefono, linea);
                            }
                        } else {
                            Principal.modelListEvents.add(0, "=> Llamada Recibida del numero: " + telefono);
                        }

                        trama = "";
                    } else {
                        trama = "";
                    }
                }
            }
        }
    }

    private void timbrar(boolean timbre, String phone, String linea) {
        if (timbre) {
            Principal.lblCall.setIcon(new javax.swing.ImageIcon(CentralTelefonica.class.getResource("/com/kradac/despachos/img/llamada.png")));
            //Principal.txtPhone.setText(phone);
            Call c;

            try {
                Client cl = Principal.listClient.getClientByPhone(phone);
                c = new Call(-1, Functions.getDate(), Functions.getTime(), phone, linea, cl.getName() + " " + cl.getLastname(),
                        cl.getDirection(), cl.getReference(),
                        cl.getLatitud(), cl.getLongitud());
            } catch (NullPointerException e) {
                c = new Call(-1, Functions.getDate(), Functions.getTime(), phone, linea, "",
                        "", "",
                        0.0, 0.0);
            }

            db.insertCall(c);
            Principal.listCall.addCall(c);
            //Principal.tblCall.setRowSelectionInterval(0, 0);
            //Principal.tblCall.requestFocus();
        } else {
            Principal.lblCall.setIcon(new javax.swing.ImageIcon(CentralTelefonica.class.getResource("/com/kradac/despachos/img/nollamada.png")));
        }
    }

    private boolean AbrirPuerto(String comm) {
        try {
            id_Puerto = CommPortIdentifier.getPortIdentifier(comm);
            sPuerto = (SerialPort) id_Puerto.open("MonitoreoKradac", 5000); //tiempo de bloqueo 1m
            return true;
        } catch (PortInUseException ex) {
            JOptionPane.showMessageDialog(null, "Puerto del modem está en uso por otra apicación...\nModificar los paramatros de inicio si no quiere usar el identificador de llamadas.", "error", 0);
            System.err.println("Cerrar Apicación - puerto en uso o no hay puerto serial disponible...");
            System.exit(0);
        } catch (NoSuchPortException ex) {
            System.err.println("" + ex.getMessage());
            JOptionPane.showMessageDialog(null, "No se ha cargado el driver COMM de java o el puerto Serial [" + comm + "] no esta disponible para el uso:\n\n" + ex + "\n\nNo se puede cargar la aplicación...", "error", 0);
            //CargarDriverCOMM();
            System.exit(0);
        }
        return false;
    }

    public String procesoFecha(String fecha) { //    M/D/Y
        fecha = fecha.replace(" ", "");
        String[] parte = fecha.split("/");
        return "20" + parte[2] + "-" + parte[0] + "-" + parte[1];
    }

    public int leerDatosCode() {
        InputStream is;
        try {
            is = sPuerto.getInputStream();
            return is.read();
        } catch (IOException | NullPointerException ex) {

        }
        return 0;
    }

    private boolean setParametros(int baudRate, int dataBits, int stopBits, int paridad) {
        try {
            sPuerto.setSerialPortParams(baudRate, dataBits, stopBits, paridad);
            return true;
        } catch (UnsupportedCommOperationException ex) {
            System.exit(0);
        }
        return false;
    }

    private void CerrarPuerto() {
        sPuerto.close();
    }
}

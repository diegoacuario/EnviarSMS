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
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class LastGps {

    private static Socket s;
    private static BufferedReader input;
    private static PrintStream output;
    private static int port;
    private static String ip;
    private int valor = 0;
    private DataBase db;

    public LastGps() {
        try {
            ip = Principal.fileConfig.getProperty("ip_server");
            port = Integer.parseInt(Principal.fileConfig.getProperty("puerto_server"));
            db = new DataBase(Principal.fileConfig, Principal.host);
            openConexion();
            saveDateRecorridos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Verifique el Puerto de su Archivo de Configuración", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveDateRecorridos() {
        try {
            chageIconSeñaL(true);
            String[] tramas = getDataServerByCompany(Principal.company.getIdCompany());
            for (String trama : tramas) {
                saveDataVehiculo(trama);                                                               
            }
        } catch (NullPointerException ex) {
            
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
            
            /*String estadoUnidad = b.getEstadoUnidad(Integer.parseInt(recorrido[1]));            
            b.destruir();
            
            String estadoTrama;
            if (Integer.parseInt(recorrido[9]) == 0) { //LIBRE
                estadoTrama = "AC";
            } else { //OCUPADO
                estadoTrama = "OCU";
            }
            
            if (estadoUnidad.equals("OCU") && estadoTrama.equals("AC")) {
                //System.out.println("Ocupado a Activo Unidad: " + recorrido[1]);
                Principal.asignacion = 1;
                Principal.libre_automatico(recorrido[1]);
            } else if (estadoUnidad.equals("AC") && estadoTrama.equals("OCU")) {
                //System.out.println("Activo a Ocupado Unidad: " + recorrido[1]);
                Principal.asignacion = 1;
                Principal.ocupado_automatico(recorrido[1]);       //Ilegal                
            } else if (estadoUnidad.equals("ASI") && estadoTrama.equals("OCU")) {
                //System.out.println("Asignado a Ocupado Unidad: " + recorrido[1]);
                Principal.asignacion = 1;
                Principal.asignarOcupado(recorrido[1]);         //Legal
            } /*else if(estadoUnidad.equals("ASI") && estadoTrama.equals("AC")){
             Principal.asignacion = 1;
             Principal.libre_automatico();
             }*/
        } catch (ArrayIndexOutOfBoundsException ex) {
        } 
    }

    private String[] getDataServerByCompany(String idCompany) {
        ArrayList<String> newData = new ArrayList();
        String[] cast;
        String[] datos;
        try {
            input = new BufferedReader(new InputStreamReader(s.getInputStream()));
            output = new PrintStream(s.getOutputStream(), true);

            output.print("$$1##" + idCompany + "$$\n");
            boolean salir = false;
            String dato;
            while (!salir) {
                if ((dato = input.readLine()) != null) {
                    //System.out.println("**** " + dato);
                    if (dato.contains("%")) {
                        String[] resp = dato.split("&");
                        if (resp[1].contains("#")) {
                            String[] pos = resp[1].split("#");
                            for (int i = 0; i < pos.length; i++) {
                                newData.add(pos[i]);
                                if (i == pos.length - 1) {
                                    salir = true;
                                } else {
                                    salir = false;
                                }
                            }
                        }

                        //System.out.println(resp[0].length());
                        String eventos[] = resp[0].split("%");

                        if (eventos[0].contains("#R")) { //Asignacion respuesta Garmin
                            System.out.println("" + eventos[0]);
                            String respuesta[] = eventos[0].split(";"); //respuesta: 1;00001;19

                            // 1 = Acepta
                            // 2 = Rechaza
                            respuesta[0] = respuesta[0].substring(2, respuesta[0].length());
                            valor = Integer.parseInt(respuesta[0]);
                            if (valor == 1) {
                                //Principal.asignarAutomaticamente(respuesta[2]);
                            } else if (valor == 2) {
                                //Principal.rechazoAsignacion(respuesta[2]);
                            }

                        } /*else if (resp[0].contains("#O")) { //Asignar Ocupado/Libre
                         String respuesta[] = resp[0].split(";");
                         // 0 = Ocupado
                         // 1 = Libre
                         respuesta[0] = respuesta[0].substring(2, respuesta[0].length());
                         valor = Integer.parseInt(respuesta[0]);
                         unidadComparar = respuesta[1];
                         /*if (valor == 0) {
                         Principal.asignarOcupado();
                         } else if (valor == 1) {
                         Principal.libre_automatico();
                         }
                         }*/ else if (eventos[0].contains("#Q")) { //Mensajes
                            String respuesta[] = eventos[0].split(";");
                            if (respuesta[0].contains("1")) {
                                System.out.println("Inicio de Jornada");
                                //Principal.inicioJornadaClave(respuesta[1]);
                            } else if (respuesta[0].contains("2")) {
                                System.out.println("Fin de Jornada");
                                //Principal.finJornada(respuesta[1]);
                            } else if (respuesta[0].contains("3")) {
                                System.out.println("Descanso");
                                //Principal.descanso(respuesta[1]);
                            } else if (respuesta[0].contains("4")) {
                                System.out.println("Retorno de Descanso");
                                //Principal.retornoDescanso(respuesta[1]);
                            }
                        } else if (eventos[0].contains("#P")) { //Mensajes
                            String respuesta[] = eventos[0].split(";");
                            respuesta[0] = respuesta[0].substring(2, respuesta[0].length());
                            valor = Integer.parseInt(respuesta[0]);
                            //Principal.alertaPanico(respuesta[1]);
                        } else {
                            System.out.println("Asignar Ocupado");
                        }
                    } else {
                        dato = dato.substring(1, dato.length());
                        String[] pos = dato.split("#");
                        for (int i = 0; i < pos.length; i++) {
                            newData.add(pos[i]);
                            if (i == pos.length - 1) {
                                salir = true;
                            } else {
                                salir = false;
                            }
                        }
                    }
                }
            }

            cast = new String[newData.size()];

            datos = newData.toArray(cast);
            return datos;
        } catch (Exception e) {
            closeConexion();
            openConexion();
        }
        return null;
    }

    public void openConexion() {
        try {
            try {
                s = new Socket(ip, port);
            } catch (UnknownHostException ex) {
                chageIconSeñaL(false);
            } catch (IOException ex) {
                chageIconSeñaL(false);
                if (ex.getMessage().equals("No route to host: connect")) {
                    closeConexion();
                    try {
                        Thread.sleep(1000);
                        openConexion();
                    } catch (InterruptedException ex1) {
                    }
                }
            }
        } catch (StackOverflowError m) {
        }
    }

    public void closeConexion() {
        chageIconSeñaL(false);
        try {
            try {
                output.close();
                input.close();
                db.closeConexion();
            } catch (NullPointerException ex) {
            }
            try {
                s.close();
            } catch (NullPointerException ex) {
            }
        } catch (IOException ex) {

        }
    }

    private void chageIconSeñaL(boolean señal) {
        if (señal) {
            Principal.lblConection.setIcon(new javax.swing.ImageIcon(LastGps.class.getResource("/com/kradac/despachos/img/senal.png")));
        } else {
            Principal.lblConection.setIcon(new javax.swing.ImageIcon(LastGps.class.getResource("/com/kradac/despachos/img/nosenal.png")));
        }
    }
}

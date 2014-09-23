/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
public class ConexionModem extends Thread {
    
    private CommPortIdentifier id_Puerto;
    private SerialPort sPuerto;
    public Enumeration listaPuertos;
    
    private String port;
    private OutputStream os;
    
    String strWin32 = "win32com.dll";
    String strComm = "comm.jar";
    String strProp = "javax.comm.properties";
    
    public ConexionModem() {
        port= JOptionPane.showInputDialog("Ingrese puerto COM");
        //port = "COM11";
        if (!port.equals("0")) {
            if (!AbrirPuerto(port)) {
                JOptionPane.showMessageDialog(null, "No se pudo abrir el puerto COM: " + port, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            if (!setParametros(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE)) {
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
        
        while (true) {
            /*caracAssi = leerDatosCode();
             dato = "" + (char) caracAssi;
             trama = trama + dato;*/
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
    
    public int leerDatosCode() {
        InputStream is;
        try {
            is = sPuerto.getInputStream();
            return is.read();
        } catch (IOException | NullPointerException ex) {
            
        }
        return 0;
    }
    
    public void enviarDatos(String mensaje) {
        try {
            os = sPuerto.getOutputStream();
            System.out.println("[COM]" + mensaje);
            os.write(mensaje.getBytes());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al Enviar mensaje a la Unidad");
        } catch (NullPointerException ex) {
        } catch (IllegalStateException ex) {
        }
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

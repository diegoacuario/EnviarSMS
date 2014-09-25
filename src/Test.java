
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author DIEGO ROMERO
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConexionModem cm = new ConexionModem();
        cm.start();
        String celular= JOptionPane.showInputDialog("Ingrese numero de celular");
         String mensaje =JOptionPane.showInputDialog("Ingrese mensaje a enviar");
//        String celular = "0969748969";
//        String mensaje = "DESDE JAVA ATT DIEGO";
        cm.enviarDatos("APZ&F" + (char) (13));
        cm.enviarDatos("AT+CMGF=1" + (char) (13));
        cm.enviarDatos("AT+CMGS=" + (char) (34) + (celular) + (char) (34) + (char) (13));
        cm.enviarDatos(mensaje + (char) 26);
        cm.enviarDatos("AT+CMGD=1,4;");
        System.exit(0);

    }

}

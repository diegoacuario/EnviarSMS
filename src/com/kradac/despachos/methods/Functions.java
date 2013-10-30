/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class Functions {

    public static Properties getFileProperties(String url) {
        Properties prop = null;
        try {
            CodeSource codeSource = Functions.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            File jarDir = jarFile.getParentFile();
            if (jarDir != null && jarDir.isDirectory()) {
                File propFile = new File(jarDir, url);
                prop = new Properties();
                prop.load(new BufferedReader(new FileReader(propFile.getAbsoluteFile())));
            }
        } catch (URISyntaxException | IOException ex) {
            JOptionPane.showMessageDialog(null, "No se Encuentra el Archivo de Configuracion del Sistema");
            System.exit(0);
        }
        return prop;
    }

    public static String getDate() {
        Calendar calendario = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendario.getTime());
    }
    
    public static String getDateNext() {

        Calendar calendario = new GregorianCalendar();
        calendario.setLenient(false);
        calendario.setTime(new Date());
        
        calendario.add(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(calendario.getTime());
    }
    
    public static String getDateBack() {

        Calendar calendario = new GregorianCalendar();
        calendario.setLenient(false);
        calendario.setTime(new Date());
        
        calendario.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(calendario.getTime());
    }

    public static String getTime() {
        Calendar calendario = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(calendario.getTime());
    }

    public static String validatePhone(String tel) {
        int lon = tel.length();
        if (lon == 10) {
            if (isNumeric(tel)) {
                return tel;
            } else {
                return "";
            }
        } else if (lon == 9) {
            if (tel.substring(0, 2).equalsIgnoreCase("07")) {
                return tel;
            } else if (tel.substring(0, 2).equalsIgnoreCase("02")) {
                return tel;
            } else {
                tel = tel.substring(1, tel.length());
                return "09" + tel;
            }
        } else if (lon == 8) {
            return "0" + tel;
        } else if (lon == 7) {
            return "02" + tel;
        } else if (lon == 6) {
            return "022" + tel;
        } else {
            return "";
        }
    }

    public static boolean validateCedula(String cedula) {

        int NUMERO_DE_PROVINCIAS = 24;
        if (!((cedula.length() == 10) && cedula.matches("^[0-9]{10}$"))) {
            return false;
        }
        int prov = Integer.parseInt(cedula.substring(0, 2));

        if (!((prov > 0) && (prov <= NUMERO_DE_PROVINCIAS))) {
            return false;
        }

        int[] d = new int[10];

        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(cedula.charAt(i) + "");
        }

        int imp = 0;
        int par = 0;

        for (int i = 0; i < d.length; i += 2) {
            d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
            imp += d[i];
        }

        for (int i = 1; i < (d.length - 1); i += 2) {
            par += d[i];
        }

        int suma = imp + par;

        int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1) + "0") - suma;

        d10 = (d10 == 10) ? 0 : d10;

        return d10 == d[9];
    }

    public static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}

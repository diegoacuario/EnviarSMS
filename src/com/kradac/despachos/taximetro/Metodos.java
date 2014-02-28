package com.kradac.despachos.taximetro;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class Metodos {

    public String Meses[] = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
    public Vector archivosValidos;

    public String listarArchivos(String uni) {
        File fichero = new File(uni);
        File[] lista = fichero.listFiles();

        archivosValidos = new Vector();
        String nom = "", aux, ext;
        for (int i = 0; i < lista.length; i++) {
            if (!lista[i].isDirectory()) {
                aux = lista[i].getName();
                ext = aux.substring(aux.length() - 4, aux.length());
                if (ext.equalsIgnoreCase(".txt")) {
                    if (aux.length() == 10 || aux.length() == 11) {
                        if (verificaNombre(aux)) {
                            if (!leerArchivo(lista[i]).isEmpty()) {
                                archivosValidos.add(lista[i]);
                                nom += aux + "\n";
                            } else {
                                System.out.println("Unidad " + uni + " contiene archivo " + lista[i].getName() + " vacío");
                            }

                        }
                    } else {
                        // System.err.println("Unidad " + uni + " contiene archivo .txt incorrecto este es '" + lista[i].getName() + "'");
                    }
                } else {
                    //   System.err.println("Unidad " + uni + " contiene archivo que no es .txt este es '" + lista[i].getName() + "'");
                }

            } else {
                //System.err.println("Unidad " + uni + " contiene directorio '" + lista[i].getName() + "'");
            }
        }

        return nom;
    }

    public boolean borrarArchivos(String uni) {
        File fichero = new File(uni);
        File[] lista = fichero.listFiles();
        String aux, ext;
        boolean borra = false;
        int y = 0, x = 0;
        for (int i = 0; i < lista.length; i++) {
            if (!lista[i].isDirectory()) {
                aux = lista[i].getName();
                ext = aux.substring(aux.length() - 4, aux.length());
                if (ext.equalsIgnoreCase(".txt")) {
                    if (aux.length() == 10 || aux.length() == 11) {
                        if (verificaNombre(aux)) {
                            //archivosValidos.add(lista[i]);
                            y++;
                            if (lista[i].delete()) {
                                x++;
                            }
                        }
                    } else {
                        //System.err.println("Unidad " + uni + " contiene archivo .txt incorrecto este es '" + lista[i].getName() + "'");
                    }
                } else {
                    //  System.err.println("Unidad " + uni + " contiene archivo que no es .txt este es '" + lista[i].getName() + "'");
                }

            } else {
                //System.err.println("Unidad " + uni + " contiene directorio '" + lista[i].getName() + "'");
            }
        }
        if (x == y) {
            borra = true;
        }
        return borra;
    }

    public boolean borrarArchivosTodo(String uni) {
        File fichero = new File(uni);
        File[] lista = fichero.listFiles();
        boolean borra = true;
        for (int i = 0; i < lista.length; i++) {
            lista[i].delete();
        }

        return borra;
    }

    public boolean verificaNombre(String nomArc) {
        boolean res = true;
        String nomSinExt = nomArc.substring(0, nomArc.length() - 4);
        if (!Character.isDigit(nomSinExt.charAt(nomSinExt.length() - 1))
                || !Character.isDigit(nomSinExt.charAt(nomSinExt.length() - 2))) {
            res = false;
            System.err.println("Nombre de archivo " + nomArc + " no es el correcto, ultimos dos caracteres no corresponden al año, deben ser dígitos");
        } else {

            String mes = nomSinExt.substring(nomSinExt.length() - 5, nomSinExt.length() - 2);
            int cont = 0;
            for (int i = 0; i < 12; i++) {
                if (Meses[i].equalsIgnoreCase(mes)) {
                    cont++;
                }
            }
            if (cont == 0) {
                res = false;
                System.err.println("Nombre de archivo " + nomArc + " no es el correcto, debe contener iniciales de mes válidas mes " + mes + " no existe");
            } else {

                if (nomSinExt.length() == 7) {
                    if (!Character.isDigit(nomSinExt.charAt(0))
                            || !Character.isDigit(nomSinExt.charAt(1))) {
                        res = false;
                        System.err.println("Nombre de archivo " + nomArc + " no es el correcto, primeros dos caracteres no corresponden al día, deben ser dígitos");
                    } else {
                        int dia = Integer.parseInt(nomSinExt.substring(0, 2));
                        if (!verificaDia(dia, mes)) {
                            res = false;
                        }

                    }
                }
                if (nomSinExt.length() == 6) {
                    if (!Character.isDigit(nomSinExt.charAt(0))) {
                        res = false;
                        System.err.println("Nombre de archivo " + nomArc + " no es el correcto, primeros dos caracteres no corresponden al día, deben ser dígitos");
                    } else {
                        int dia = Integer.parseInt(nomSinExt.substring(0, 1));
                        if (!verificaDia(dia, mes)) {
                            res = false;
                        }
                    }
                }
            }
        }
        return res;
    }

    public Vector listarUnidades() {
        File unidades[] = File.listRoots();

        Vector it = new Vector();
        for (File unidade : unidades) {
            String s1 = FileSystemView.getFileSystemView().getSystemDisplayName(unidade);
            String s2 = FileSystemView.getFileSystemView().getSystemTypeDescription(unidade);
            if (s2.equals("Disco extraíble")) {
                it.add(s1);
            }
        }
        return it;
    }

    public String leerArchivo(File n) {
        String texto = "";
        try {
            FileInputStream fstream = new FileInputStream(n);
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            while ((strLinea = buffer.readLine()) != null) {
                texto += strLinea;
            }
            entrada.close();
        } catch (Exception e) {
            System.err.println("Ocurrio un error: " + e.getMessage());
        }
        return texto;
    }

    public String[] transArchivosToTramas() {
        String todo = "";
        for (int i = 0; i < archivosValidos.size(); i++) {
            todo += leerArchivo((File) archivosValidos.get(i));
        }

        String tramas[] = todo.split("%");
        return tramas;
    }
    //elimina tramas invalidad

    public String[] filtraTramas(String tra[]) {
        int traVal = 0;
        Vector traTemp = new Vector();
        for (int i = 0; i < tra.length; i++) {
            if (validaTrama(tra[i])) {
                traVal++;
                traTemp.add(tra[i]);
            }
        }
        String tramasValidas[] = new String[traVal];
        for (int i = 0; i < traVal; i++) {
            tramasValidas[i] = traTemp.get(i) + "";
        }
        return tramasValidas;
    }

    public void presentaTramas(String tramas[]) {
        for (int i = 0; i < tramas.length; i++) {
            System.out.println(tramas[i]);
        }
    }

    public Vector<DatoTaximetro> listarDatos(String tramas[]) {

        Vector<DatoTaximetro> listaDatos = new Vector<>();
        String dato[];
        DatoTaximetro d;
        for (int i = 0; i < tramas.length; i++) {
            dato = tramas[i].split(";");
            if (!dato[0].isEmpty()) {
                d = new DatoTaximetro();
                d.setSerial(dato[0]);
                d.setTiquete(dato[1]);
                d.setFecha(dato[2]);
                d.setHora_inicial(dato[3]);
                d.setHora_final(dato[4]);
                d.setDistancia(Double.parseDouble(dato[5]));
                d.setCosto(Double.parseDouble(dato[6]));
                d.setPaga(Double.parseDouble(dato[7]));
                d.setValido(dato[8]);
                d.setTarifa(Integer.parseInt(dato[9]));
                d.setBanderaso(Double.parseDouble(dato[10]));
                d.setTiempo_seg(Integer.parseInt(dato[11]));
                listaDatos.add(d);
            } else {
                JOptionPane.showMessageDialog(null, "Archivo contiene lineas inválidas o esta vacío");
            }
        }
        return listaDatos;
    }

    public boolean validaTrama(String trama) {
        boolean valida = true;
        String datTrama[] = trama.split(";");
        int numDat = datTrama.length;
        String error = "";
        if (numDat != 12) {
            error += "Tamaño de trama incorrecta ";
        } else if (!validaEnteroPosSinCero(datTrama[1])) {
            error += "Error en el número de tiquet";
        } else if (!verificaFecha(datTrama[2])) {
            error += "Error en la fecha de la carrera";
        } else if (!verificaHora(datTrama[3])) {
            error += "Error en la hora inicial de la carrera";
        } else if (!verificaHora(datTrama[4])) {
            error += "Error en la hora final de la carrera";
        } else if (!verificaDouble(datTrama[5])) {
            error += "Error en la distancia recorrida";
        } else if (!verificaDouble(datTrama[6])) {
            error += "Error en la distancia recorrida";
        } else if (!verificaDouble(datTrama[7])) {
            error += "Error en la distancia recorrida";
        } else if (!verificaFecha(datTrama[8])) {
            error += "Error en la fecha válida hasta";
        } else if (!verificaTarifa(datTrama[9])) {
            error += "Error en la tarifa";
        } else if (!verificaDouble(datTrama[10])) {
            error += "Error en el banderaso";
        } else if (!validaEnteroPos(datTrama[11])) {
            error += "Error en el tiempo";
        }
        if (!error.isEmpty()) {
            valida = false;
        }
        return valida;
    }
    //Valida que el número de tiquet sea entero mayor a 0 recordemos que el 
    //maximo es 2147483647

    public boolean validaEnteroPos(String tiquet) {
        boolean valido = true;
        int x = 0;
        try {
            x = Integer.parseInt(tiquet);
        } catch (NumberFormatException e) {
            valido = false;
        }
        if (valido) {
            if (x < 0) {
                valido = false;//Cuando es cero o negativo
            }
        }
        return valido;
    }

    public boolean validaEnteroPosSinCero(String tiquet) {
        boolean valido = true;
        int x = 0;
        try {
            x = Integer.parseInt(tiquet);
        } catch (NumberFormatException e) {
            valido = false;
        }
        if (valido) {
            if (x <= 0) {
                valido = false;//Cuando es negativo
            }
        }
        return valido;
    }

    public boolean verificaFecha(String nomArc) {
        boolean res = true;
        String nomSinExt = nomArc;
        if (!Character.isDigit(nomSinExt.charAt(nomSinExt.length() - 1))
                || !Character.isDigit(nomSinExt.charAt(nomSinExt.length() - 2))) {
            res = false;//ultimos dos caracteres no corresponden al año, deben ser dígitos
        } else {
            String Meses[] = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
            String mes = nomSinExt.substring(nomSinExt.length() - 5, nomSinExt.length() - 2);
            int cont = 0;
            for (int i = 0; i < 12; i++) {
                if (Meses[i].equalsIgnoreCase(mes)) {
                    cont++;
                }
            }
            if (cont == 0) {
                res = false;//debe contener iniciales de mes válidas
            } else {

                if (nomSinExt.length() == 7) {
                    if (!Character.isDigit(nomSinExt.charAt(0))
                            || !Character.isDigit(nomSinExt.charAt(1))) {
                        res = false;//primeros dos caracteres no corresponden al día, deben ser dígitos
                    } else {
                        int dia = Integer.parseInt(nomSinExt.substring(0, 2));
                        if (!verificaDia(dia, mes)) {
                            res = false;
                        }
                    }
                }
                if (nomSinExt.length() == 6) {
                    if (!Character.isDigit(nomSinExt.charAt(0))) {
                        res = false;//primeros dos caracteres no corresponden al día, deben ser dígitos
                    } else {
                        int dia = Integer.parseInt(nomSinExt.substring(0, 1));
                        if (!verificaDia(dia, mes)) {
                            res = false;//Dia incorrecto esta fuera de rango de los días que tiene el mes
                        }
                    }
                }
            }
        }
        return res;
    }

    private boolean verificaDia(int dia, String mes) {
        boolean res = true;
        switch (mes) {
            case "ENE":
            case "MAR":
            case "MAY":
            case "JUL":
            case "SEP":
            case "OCT":
            case "DIC":
                if (dia < 1 || dia > 31) {
                    res = false;
                    //Dia no es correcto no esta entre 1 y 31
                }
                break;
            case "ABR":
            case "JUN":
            case "AGO":
            case "NOV":
                if (dia < 1 || dia > 30) {
                    res = false;
                    //Dia no es correcto no esta entre 1 y 30
                }
                break;
            case "FEB":
                if (dia < 1 || dia > 29) {
                    res = false;
                    //Dia no es correcto no esta entre 1 y 29
                }
                break;
        }
        return res;
    }

    public boolean verificaHora(String hora) {
        boolean valida = true;
//        if (hora.length() == 8) {
        String dat[] = hora.split(":");
        if (dat.length == 3) {
            int h = 0, m = 0, s = 0;
            try {
                h = Integer.parseInt(dat[0]);
            } catch (NumberFormatException e) {
                valida = false;//Cuando la hora no es un número
            }
            if (valida) {
                try {
                    m = Integer.parseInt(dat[1]);
                } catch (NumberFormatException e) {
                    valida = false;//Cuando los minutos no es un número
                }
            }
            if (valida) {
                try {
                    s = Integer.parseInt(dat[2]);
                } catch (NumberFormatException e) {
                    valida = false;//Cuando los segundos no es un número
                }
            }
            if (valida) {
                if (h < 0 || h > 23) {
                    valida = false;//Hora no esta en el rango permitido
                } else if (m < 0 || m > 59) {
                    valida = false;//Minuto no esta en el rango permitido
                } else if (s < 0 || s > 59) {
                    valida = false;//Segundo no esta en el rango permitido                           
                }
            }
        } else {
            valida = false;//Cuando la hora no tiene los tres parametros hh:mm:ss
        }
//        } else {
//            valida = false;//Cuando la hora no tiene 8 caracteres
//        }
        return valida;
    }

    public boolean verificaDouble(String distancia) {

        boolean valida = true;
        double dis = 0;
        try {
            dis = Double.parseDouble(distancia);
        } catch (NumberFormatException e) {
            valida = false;//Cuando la distancia no es un número
        }
        if (valida) {
            if (dis < 0) {
                valida = false;//Cuando llega distancia negativa
            }
        }
        return valida;
    }

    public boolean verificaTarifa(String tarifa) {
        boolean valida = true;
        int tar = 0;
        try {
            tar = Integer.parseInt(tarifa);
        } catch (NumberFormatException e) {
            valida = false;//Cuando la tarifa no es un número entero
        }
        if (valida) {
            if (tar != 1 && tar != 2) {
                valida = false;
            }
        }
        return valida;
    }
}

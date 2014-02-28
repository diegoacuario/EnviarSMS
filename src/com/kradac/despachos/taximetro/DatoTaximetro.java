package com.kradac.despachos.taximetro;

/**
 *
 * @author Ing. Diego
 */
public class DatoTaximetro {

    private String serial;
    private String tiquete;
    private String fecha;
    private String hora_inicial;
    private String hora_final;
    private double distancia;
    private double costo;
    private double paga;
    private String valido;
    private String tarifa;
    private double banderaso;
    private int tiempo_seg;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getTiquete() {
        return tiquete;
    }

    public void setTiquete(String tiquete) {
        this.tiquete = tiquete;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fec) {
        this.fecha = transFecha(fec);
    }

    public String getHora_inicial() {
        return hora_inicial;
    }

    public void setHora_inicial(String hora_inicial) {
        this.hora_inicial = hora_inicial;
    }

    public String getHora_final() {
        return hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPaga() {
        return paga;
    }

    public void setPaga(double paga) {
        this.paga = paga;
    }

    public String getValido() {
        return valido;
    }

    public void setValido(String valido) {
        this.valido = transFecha(valido);
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        String tar;
        if (tarifa == 1) {
            tar = "DIURNO";
        } else {
            tar = "NOCTURNO";
        }
        this.tarifa = tar;
    }

    public double getBanderaso() {
        return banderaso;
    }

    public void setBanderaso(double banderaso) {
        this.banderaso = banderaso;
    }

    public int getTiempo_seg() {
        return tiempo_seg;
    }

    public void setTiempo_seg(int tiempo_seg) {
        this.tiempo_seg = tiempo_seg;
    }

    private String transFecha(String fec) {

        int a;
        String m = "";
        String d;
        if (fec.length() == 7) {
            d = fec.substring(0, 2);
        } else {
            d = fec.substring(0, 1);
        }
        if (Integer.parseInt(d) < 10) {
            d = "0" + d;
        }
        a = Integer.parseInt(fec.substring(fec.length() - 2, fec.length()));
        String mes = fec.substring(fec.length() - 5, fec.length() - 2);
        switch (mes) {
            case "ENE":
                m = "01";
                break;
            case "FEB":
                m = "02";
                break;
            case "MAR":
                m = "03";
                break;
            case "ABR":
                m = "04";
                break;
            case "MAY":
                m = "05";
                break;
            case "JUN":
                m = "06";
                break;
            case "JUL":
                m = "07";
                break;
            case "AGO":
                m = "08";
                break;
            case "SEP":
                m = "09";
                break;
            case "OCT":
                m = "10";
                break;
            case "NOV":
                m = "11";
                break;
            case "DIC":
                m = "12";
                break;
        }
        fec = (a + 2000) + "-" + m + "-" + d;
        return fec;

    }
}

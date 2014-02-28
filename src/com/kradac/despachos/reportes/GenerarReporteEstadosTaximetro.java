/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.reportes;

import com.kradac.despachos.administration.Company;
import com.kradac.despachos.database.DataBase;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author root
 */
public class GenerarReporteEstadosTaximetro {

    private DataBase bd;
    private HashMap campos;
    private String[] sesion;
    private Company empresa;
    private String usuario;
    private InputStream RutaJasper;

    public GenerarReporteEstadosTaximetro(DataBase cb, HashMap camp) {
        this.bd = cb;
        this.campos = camp;
       // this.sesion = ses;
        this.empresa = bd.loadCompany();
        //this.usuario = sesion[2];
        try {
            if (campos.get("cat").toString().equals("EstadosTaximetro")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/estados/EstadosTaximetro.jrxml");
            }
        } catch (NullPointerException ex) {
        }
    }

    /**
     * Genera segun los campos que se haya llenado
     */
    public void Generar() {
        try {

            if (campos.get("tEstado").equals("total") && campos.get("tiempo").equals("total")) {
                GenerarTodosEstadosTaxi();
                System.out.println("hola");
            } else if (campos.get("tEstado").equals("unidad") && campos.get("tiempo").equals("total")) {
                GenerarTodosEstadosTaxiUnaUnidad();
            } else {
                if (campos.get("tEstado").equals("total")) {
                    if (campos.get("tiempo").equals("dia")) {
                        GenerarReporteTodasUnidadesDia();

                    }
                } else if (campos.get("tEstado").equals("unidad")) {
                    if (campos.get("tiempo").equals("dia")) {
                        GenerarReporteUnaUnidadDia();
                    }
                }
            }

        } catch (NullPointerException ex) {
        }
    }

    /**
     * Genera el reporte de los estados de las unidades desde que se puso a
     * funcionar el sistema de todas las unidades
     */
    private void GenerarTodosEstadosTaxi() {
        String sqlCodigo = "SELECT *,COUNT(*) AS TOTAL FROM taximetro_data GROUP BY tiquete";
        System.out.println("SQL: " + sqlCodigo);
        Map parametro = new HashMap();
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);
        //parametro.put("distanciaPro", bd.promedioDistancia(sqlCodigo)+"");
        double pro[] = bd.calcularPromedio(sqlCodigo);
        String dis = pro[1] + "";
        String disSum = pro[0] + "";
        String cos = pro[3] + "";
        String cosSum = pro[2] + "";
        String pag = pro[5] + "";
        String pagSum = pro[4] + "";
        String tie = pro[7] + "";
        String tieSum = pro[6] + "";

        //System.out.println(dis);
        parametro.put("distanciaPro", dis);
        parametro.put("distanciaSum", disSum);
        parametro.put("costoPro", cos);
        parametro.put("costoSum", cosSum);
        parametro.put("pagaPro", pag);
        parametro.put("pagaSum", pagSum);
        parametro.put("tiempoPro", tie);
        parametro.put("tiempoSum", tieSum);
        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }
    /*
     * Genera el reporte de estados de todas las unidades en un determinado dia
     */

    private void GenerarReporteTodasUnidadesDia() {
        String sqlCodigo = "SELECT *,COUNT(*) AS TOTAL FROM taximetro_data WHERE "
                + "concat(fecha, ' ' ,hora_inicial) >= '$P!{dia}' "
                + "and concat(fecha, ' ' ,hora_inicial) <= '$P!{dia1}' GROUP BY tiquete";


        String sqlCodigo1 = "SELECT *,COUNT(*) AS TOTAL FROM taximetro_data WHERE "
                + "concat(fecha, ' ' ,hora_inicial) >= '" + campos.get("dia") + "' "
                + "and concat(fecha, ' ' ,hora_inicial) <= '" + campos.get("dia1") + "' GROUP BY tiquete";
        double pro[] = bd.calcularPromedio(sqlCodigo1);
        String dis = pro[1] + "";
        String disSum = pro[0] + "";
        String cos = pro[3] + "";
        String cosSum = pro[2] + "";
        String pag = pro[5] + "";
        String pagSum = pro[4] + "";
        String tie = pro[7] + "";
        String tieSum = pro[6] + "";


        Map parametro = new HashMap();
        parametro.put("sql", sqlCodigo);
        parametro.put("dia", campos.get("dia"));
        parametro.put("dia1", campos.get("dia1"));
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);
        parametro.put("distanciaPro", dis);
        parametro.put("distanciaSum", disSum);
        parametro.put("costoPro", cos);
        parametro.put("costoSum", cosSum);
        parametro.put("pagaPro", pag);
        parametro.put("pagaSum", pagSum);
        parametro.put("tiempoPro", tie);
        parametro.put("tiempoSum", tieSum);
        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Todos los estados de unidad desde que empeso a funcionar el sistema
     */
    private void GenerarTodosEstadosTaxiUnaUnidad() {
        String sqlCodigo = "SELECT *,COUNT(*) AS TOTAL FROM taximetro_data "
                + "WHERE taxi= $P!{unidad} GROUP BY tiquete";
        String sqlCodigo1 = "SELECT *,COUNT(*) AS TOTAL FROM taximetro_data "
                + "WHERE taxi= " + campos.get("unidad") + " GROUP BY tiquete";



        System.out.println("SQL: " + sqlCodigo1);

        Map parametro = new HashMap();
        double pro[] = bd.calcularPromedio(sqlCodigo1);
        String dis = pro[1] + "";
        String disSum = pro[0] + "";
        String cos = pro[3] + "";
        String cosSum = pro[2] + "";
        String pag = pro[5] + "";
        String pagSum = pro[4] + "";
        String tie = pro[7] + "";
        String tieSum = pro[6] + "";

        //System.out.println(dis);
        parametro.put("distanciaPro", dis);
        parametro.put("distanciaSum", disSum);
        parametro.put("costoPro", cos);
        parametro.put("costoSum", cosSum);
        parametro.put("pagaPro", pag);
        parametro.put("pagaSum", pagSum);
        parametro.put("tiempoPro", tie);
        parametro.put("tiempoSum", tieSum);
        parametro.put("sql", sqlCodigo);
        parametro.put("unidad", campos.get("unidad"));
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);
        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera el reporte de lose stados de una unidad en un dia determinado
     */
    private void GenerarReporteUnaUnidadDia() {
        String sqlCodigo = "SELECT *,COUNT(*) AS TOTAL FROM taximetro_data WHERE "
                + "concat(fecha, ' ' ,hora_inicial) >= '$P!{dia}' "
                + "and concat(fecha, ' ' ,hora_inicial) <= '$P!{dia1}' "
                + "and taxi= $P!{unidad} GROUP BY tiquete";

        String sqlCodigo1 = "SELECT *,COUNT(*) AS TOTAL FROM taximetro_data WHERE "
                + "concat(fecha, ' ' ,hora_inicial) >= '" + campos.get("dia") + "' "
                + "and concat(fecha, ' ' ,hora_inicial) <= '" + campos.get("dia1") + "' "
                + "and taxi= " + campos.get("unidad") + " GROUP BY tiquete";

        Map parametro = new HashMap();
        parametro.put("sql", sqlCodigo);
        parametro.put("dia", campos.get("dia"));
        parametro.put("dia1", campos.get("dia1"));
        parametro.put("unidad", campos.get("unidad"));
        parametro.put("empresa", empresa);
        double pro[] = bd.calcularPromedio(sqlCodigo1);
        String dis = pro[1] + "";
        String disSum = pro[0] + "";
        String cos = pro[3] + "";
        String cosSum = pro[2] + "";
        String pag = pro[5] + "";
        String pagSum = pro[4] + "";
        String tie = pro[7] + "";
        String tieSum = pro[6] + "";

        //System.out.println(dis);
        parametro.put("distanciaPro", dis);
        parametro.put("distanciaSum", disSum);
        parametro.put("costoPro", cos);
        parametro.put("costoSum", cosSum);
        parametro.put("pagaPro", pag);
        parametro.put("pagaSum", pagSum);
        parametro.put("tiempoPro", tie);
        parametro.put("tiempoSum", tieSum);
        parametro.put("usuario", usuario);
        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }
}

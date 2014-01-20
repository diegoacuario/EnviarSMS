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
public class GenerarReporteEstadosTaxi {

    private DataBase bd;
    private HashMap campos;
    private String[] sesion;
    private Company empresa;
    private String usuario;
    private InputStream RutaJasper;

    public GenerarReporteEstadosTaxi(DataBase cb, HashMap camp) {
        this.bd = cb;
        this.campos = camp;
        //this.sesion = ses;
        this.empresa = bd.loadCompany();
       // this.usuario = sesion[2];
        try {
            if (campos.get("cat").toString().equals("EstadosTaxi")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/estados/EstadosTaxi.jrxml");
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
            } else if (campos.get("tEstado").equals("unidad") && campos.get("tiempo").equals("total")) {
                GenerarTodosEstadosTaxiUnaUnidad();
            } else {
                if (campos.get("tEstado").equals("total")) {
                    if (campos.get("tiempo").equals("dia")) {
                        GenerarReporteTodasUnidadesDia();
                    } else if (campos.get("tiempo").equals("mes")) {
                        GenerarReporteTodasUnidadesMes();
                    }
                } else if (campos.get("tEstado").equals("unidad")) {
                    if (campos.get("tiempo").equals("dia")) {
                        GenerarReporteUnaUnidadDia();
                    } else if (campos.get("tiempo").equals("mes")) {
                        GenerarReporteUnaUnidadMes();
                    }
                }
            }

        } catch (NullPointerException ex) {
        }
    }

    /**
     * Genera el reporte de los estados de las unidades desde que se puso a funcionar el sistema
     * de todas las unidades
     */
    private void GenerarTodosEstadosTaxi() {
        Map parametro = new HashMap();
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /*
     * Genera el reporte de estados de todas las unidades en un determinado dia
     */
    private void GenerarReporteTodasUnidadesDia() {
        String sqlCodigo = "SELECT "
                + "REGCODESTTAXI.`N_UNIDAD` AS REGCODESTTAXI_N_UNIDAD,"
                + "REGCODESTTAXI.`USUARIO` AS REGCODESTTAXI_USUARIO,"
                + "REGCODESTTAXI.`ID_CODIGO` AS REGCODESTTAXI_ID_CODIGO,"
                + "TIME(REGCODESTTAXI.`FECHA_HORA`) AS REGCODESTTAXI_HORA,"
                + "DATE(REGCODESTTAXI.`FECHA_HORA`) AS REGCODESTTAXI_FECHA,"
                + "CODESTTAXI.`ETIQUETA` AS CODESTTAXI_ETIQUETA "
                + "FROM "
                + "`REGCODESTTAXI` REGCODESTTAXI,"
                + "`CODESTTAXI` CODESTTAXI "
                + "WHERE "
                + "REGCODESTTAXI.`ID_CODIGO` = CODESTTAXI.`ID_CODIGO` "
                + "AND REGCODESTTAXI.`FECHA_HORA` = '$P!{dia}'";

        System.out.println("SQL: " + sqlCodigo);

        Map parametro = new HashMap();
        parametro.put("sql", sqlCodigo);
        parametro.put("dia", campos.get("dia"));
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera el reporte de estados de todas las unidades en un determinado mes
     */
    private void GenerarReporteTodasUnidadesMes() {
        String sqlCodigo = "SELECT "
                + "REGCODESTTAXI.`N_UNIDAD` AS REGCODESTTAXI_N_UNIDAD,"
                + "REGCODESTTAXI.`USUARIO` AS REGCODESTTAXI_USUARIO,"
                + "REGCODESTTAXI.`ID_CODIGO` AS REGCODESTTAXI_ID_CODIGO,"
                + "TIME(REGCODESTTAXI.`FECHA_HORA`) AS REGCODESTTAXI_HORA,"
                + "DATE(REGCODESTTAXI.`FECHA_HORA`) AS REGCODESTTAXI_FECHA,"
                + "CODESTTAXI.`ETIQUETA` AS CODESTTAXI_ETIQUETA "
                + "FROM "
                + "`REGCODESTTAXI` REGCODESTTAXI,"
                + "`CODESTTAXI` CODESTTAXI "
                + "WHERE "
                + "REGCODESTTAXI.`ID_CODIGO` = CODESTTAXI.`ID_CODIGO` "
                + "AND MONTH(REGCODESTTAXI.`FECHA_HORA`) = '$P!{mes}'";

        System.out.println("SQL: " + sqlCodigo);

        Map parametro = new HashMap();
        parametro.put("sql", sqlCodigo);
        parametro.put("mes", campos.get("mes"));
        parametro.put("NombreMes", campos.get("NombreMes"));
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Todos los estados de unidad desde que empeso a funcionar el sistema
     */
    private void GenerarTodosEstadosTaxiUnaUnidad() {
        String sqlCodigo = "SELECT "
                + "REGCODESTTAXI.`N_UNIDAD` AS REGCODESTTAXI_N_UNIDAD,"
                + "REGCODESTTAXI.`USUARIO` AS REGCODESTTAXI_USUARIO,"
                + "REGCODESTTAXI.`ID_CODIGO` AS REGCODESTTAXI_ID_CODIGO,"
                + "TIME(REGCODESTTAXI.`FECHA_HORA`) AS REGCODESTTAXI_HORA,"
                + "DATE(REGCODESTTAXI.`FECHA_HORA`) AS REGCODESTTAXI_FECHA,"
                + "CODESTTAXI.`ETIQUETA` AS CODESTTAXI_ETIQUETA "
                + "FROM "
                + "`REGCODESTTAXI` REGCODESTTAXI,"
                + "`CODESTTAXI` CODESTTAXI "
                + "WHERE "
                + "REGCODESTTAXI.`ID_CODIGO` = CODESTTAXI.`ID_CODIGO` "
                + "AND REGCODESTTAXI.`N_UNIDAD` = $P!{unidad}";

        System.out.println("SQL: " + sqlCodigo);

        Map parametro = new HashMap();
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
        String sqlCodigo = "SELECT "
                + "REGCODESTTAXI.`N_UNIDAD` AS REGCODESTTAXI_N_UNIDAD,"
                + "REGCODESTTAXI.`USUARIO` AS REGCODESTTAXI_USUARIO,"
                + "REGCODESTTAXI.`ID_CODIGO` AS REGCODESTTAXI_ID_CODIGO,"
                + "TIME(REGCODESTTAXI.`FECHA_HORA`) AS REGCODESTTAXI_HORA,"
                + "DATE(REGCODESTTAXI.`FECHA_HORA`) AS REGCODESTTAXI_FECHA,"
                + "CODESTTAXI.`ETIQUETA` AS CODESTTAXI_ETIQUETA "
                + "FROM "
                + "`REGCODESTTAXI` REGCODESTTAXI,"
                + "`CODESTTAXI` CODESTTAXI "
                + "WHERE "
                + "REGCODESTTAXI.`ID_CODIGO` = CODESTTAXI.`ID_CODIGO` "
                + "AND REGCODESTTAXI.`N_UNIDAD` = $P!{unidad} "
                + "AND DATE(REGCODESTTAXI.`FECHA_HORA`)='$P!{dia}'";

        System.out.println("SQL: " + sqlCodigo);

        Map parametro = new HashMap();
        parametro.put("sql", sqlCodigo);
        parametro.put("dia", campos.get("dia"));
        parametro.put("unidad", campos.get("unidad"));
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera el reporte de los estados de una unidad en un mes determinado
     */
    private void GenerarReporteUnaUnidadMes() {
        String sqlCodigo = "SELECT "
                + "REGCODESTTAXI.`N_UNIDAD` AS REGCODESTTAXI_N_UNIDAD,"
                + "REGCODESTTAXI.`USUARIO` AS REGCODESTTAXI_USUARIO,"
                + "REGCODESTTAXI.`ID_CODIGO` AS REGCODESTTAXI_ID_CODIGO,"
                + "TIME(REGCODESTTAXI.`FECHA_HORA`) AS REGCODESTTAXI_HORA,"
                + "DATE(REGCODESTTAXI.`FECHA_HORA`) AS REGCODESTTAXI_FECHA,"
                + "CODESTTAXI.`ETIQUETA` AS CODESTTAXI_ETIQUETA "
                + "FROM "
                + "`REGCODESTTAXI` REGCODESTTAXI,"
                + "`CODESTTAXI` CODESTTAXI "
                + "WHERE "
                + "REGCODESTTAXI.`ID_CODIGO` = CODESTTAXI.`ID_CODIGO` "
                + "AND REGCODESTTAXI.`N_UNIDAD` = $P!{unidad} "
                + "AND MONTH(REGCODESTTAXI.`FECHA_HORA`)='$P!{mes}'";

        System.out.println("SQL: " + sqlCodigo);

        Map parametro = new HashMap();
        parametro.put("sql", sqlCodigo);
        parametro.put("mes", campos.get("mes"));
        parametro.put("unidad", campos.get("unidad"));
        parametro.put("NombreMes", campos.get("NombreMes"));
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }
}

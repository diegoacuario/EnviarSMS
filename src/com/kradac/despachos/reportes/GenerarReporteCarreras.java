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
public class GenerarReporteCarreras {

    private DataBase bd;
    private HashMap campos;
   // private String[] sesion;
    private Company empresa;
    //private String usuario;
    private InputStream RutaJasper;

    public GenerarReporteCarreras(DataBase cb, HashMap camp) {
        this.bd = cb;
        this.campos = camp;
        //this.sesion = ses;
        this.empresa = bd.loadCompany();
       // this.usuario = sesion[2];
        try {
            if (campos.get("radio").toString().equals("empresa")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/carreras/CarrerasEmpresa.jrxml");
            } else if (campos.get("radio").toString().equals("cliente")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/carreras/CarrerasCliente.jrxml");
            } else if (campos.get("radio").toString().equals("unidad")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/carreras/CarrerasUnidad.jrxml");
            }
        } catch (NullPointerException ex) {
        }
    }

    /**
     * Genera segun los campos que se haya llenado
     */
    public void Generar() {
        try {
            if (campos.get("radio").toString().equals("empresa")) {
                if (campos.get("todo").toString().equals("true")) {
                    GenerarTotalCarreras();
                } else {
                    GenerarTotalCarrerasMes();
                }
            } else if (campos.get("radio").toString().equals("cliente")) {
                if (campos.get("todo").toString().equals("true")) {
                    GenerarTotalCarrerasCliente();
                } else {
                    GenerarTotalCarrerasClienteMes();
                }
            } else if (campos.get("radio").toString().equals("unidad")) {
                if (campos.get("todo").toString().equals("true")) {
                    GenerarTotalCarrerasUnidad();
                } else {
                    GenerarTotalCarrerasUnidadMes();
                }
            }
        } catch (NullPointerException ex) {
        }
    }

    /*
     * Genera un reporte diario del total de carreras desde que empeso a funcionar
     * el sistema
     */
    private void GenerarTotalCarreras() {

        String sql = "SELECT "
                + "assigs.`date` AS ASIGNADOS_FECHA,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`assigs` assigs "
                + "GROUP BY ASIGNADOS_FECHA";

        System.out.println(sql);

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("empresa", empresa.getCompany());
//        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera un reporte de todos los dias de un mes determinado
     */
    private void GenerarTotalCarrerasMes() {

        String sql = "SELECT "
                + "assigs.`date` AS ASIGNADOS_FECHA,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`assigs` assigs "
                + "WHERE "
                + "MONTH(assigs.`date`) = '$P!{mes}' "
                + "AND "
                + "YEAR(assigs.`date`) = '$P!{anio}' "
                + "GROUP BY ASIGNADOS_FECHA";

        System.out.println(sql);

        Map parametro = new HashMap();
        parametro.put("sql", sql);

        parametro.put("mes", campos.get("mes"));
        parametro.put("anio", campos.get("anio"));
        parametro.put("NombreMes", campos.get("NombreMes"));

        parametro.put("empresa", empresa.getCompany());
//        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    private void GenerarTotalCarrerasCliente() {

        String sql = "SELECT "
                + "assigs.`code` AS CODIGO,"
                + "clients.`name` AS NOMBRE,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`assigs` assigs, `clients` clients "
                + "WHERE assigs.`code` = clients.`code` "
                + "GROUP BY clients.`name` "
                + "ORDER BY TOTAL DESC";

        System.out.println(sql);

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("empresa", empresa.getCompany());
//        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    private void GenerarTotalCarrerasClienteMes() {
        String sql = "SELECT "
                + "assigs.`code` AS CODIGO,"
                + "clients.`name` AS NOMBRE,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`assigs` assigs, `clients` clients "
                + "WHERE assigs.`code` = clients.`code` "
                + "AND MONTH(assigs.`date`) = '$P!{mesCli}' "
                + "AND "
                + "YEAR(assigs.`date`) = '$P!{anio}' "
                + "GROUP BY clients.`name` "
                + "ORDER BY TOTAL DESC";
        System.out.println(sql);

        Map parametro = new HashMap();
        parametro.put("sql", sql);

        parametro.put("mesCli", campos.get("mesCli"));
        parametro.put("anio", campos.get("anio"));
        parametro.put("NombreMesCli", campos.get("NombreMesCli"));

        parametro.put("empresa", empresa.getCompany());
//        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    private void GenerarTotalCarrerasUnidad() {
        String sql = "SELECT "
                + "assigs.`vehiculo` AS N_UNIDAD,"
                + "cond.`name` AS NOMBRE,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`assigs` assigs, `vehiculos` veh, `persons` cond "
                + "WHERE assigs.`id_vehiculo` = veh.`id_vehiculo` "
                + "AND veh.`id_conductor`=cond.`id_person` "
                + "GROUP BY assigs.`id_vehiculo` "
                + "ORDER BY assigs.`vehiculo` ASC";

        System.out.println(sql);

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("empresa", empresa.getCompany());
       // parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    private void GenerarTotalCarrerasUnidadMes() {
        String sql = "SELECT "
                + "assigs.`vehiculo` AS N_UNIDAD,"
                + "cond.`name` AS NOMBRE,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`assigs` assigs, `vehiculos` veh, `persons` cond "
                + "WHERE MONTH(assigs.`date`) = '$P!{mesUni}' "
                + "AND "
                + "YEAR(assigs.`date`) = '$P!{anio}' "
                + "AND assigs.`id_vehiculo` = veh.`id_vehiculo` "
                + "AND veh.`id_conductor`=cond.`id_person` "
                + "GROUP BY assigs.`id_vehiculo` "
                + "ORDER BY assigs.`vehiculo` ASC";

        System.out.println(sql);

        Map parametro = new HashMap();
        parametro.put("sql", sql);

        parametro.put("mesUni", campos.get("mesUni"));
        parametro.put("anio", campos.get("anio"));
        parametro.put("NombreMesUni", campos.get("NombreMesUni"));

        parametro.put("empresa", empresa.getCompany());
//        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }
}

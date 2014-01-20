/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.reportes;

import com.kradac.despachos.administration.Company;
import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
import com.kradac.despachos.methods.Functions;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author
 */
public class GenerarReporteDespachos {

    private DataBase bd = new DataBase();
    private HashMap campos;    
    private InputStream RutaJasper;

    public GenerarReporteDespachos(DataBase cb, HashMap camp) {
        this.bd = cb;
        this.campos = camp;
        RutaJasper = getClass().getResourceAsStream("plantillas/Despachados.jrxml");
    }

    /**
     * Genera segun los campos que se haya llenado
     */
    public void Generar() {
        if (campos.get("todo").equals(true)) {
            GenerarTodosLosDespachosFinalizados();
        } else {
            if (campos.get("entre").equals(true)) {
                GenerarDespachosFechHor();
            } else {
                if (campos.get("turno").equals(true)) {
                    RutaJasper = getClass().getResourceAsStream("plantillas/DespachadosTurnos.jrxml");
                    GenerarDespachosUserFechHor();
                } else {
                    /*if (!campos.get("user").equals("")) {
                        System.out.println("Usuario");
                        RutaJasper = getClass().getResourceAsStream("plantillas/DespachadosTurnos.jrxml");
                        GenerarTodosLosDespachosPorTurno();
                    } else {*/
                        if (!campos.get("uni").equals("")) {
                            GenerarPorUnidad();
                        } else {
                            if (!campos.get("cod").equals("")) {
                                GenerarPorCod();
                            }
                        }
                    //}
                }
            }

        }
    }

    /**
     * Genera la lista de todos los despachos
     */
    public void GenerarTodosLosDespachosFinalizados() {
        String sql = "SELECT "
                + "assigs.`code` AS ASIGNADOS_COD_CLIENTE,"
                + "assigs.`date` AS ASIGNADOS_FECHA,"
                + "assigs.`time` AS ASIGNADOS_HORA,"
                + "assigs.`phone` AS ASIGNADOS_TELEFONO,"
                + "assigs.`client` AS ASIGNADOS_NOMBRE_APELLIDO_CLI,"
                + "assigs.`direction` AS ASIGNADOS_DIRECCION_CLI,"
                + "assigs.`note` AS ASIGNADOS_NOTA,"
                + "assigs.`vehiculo` AS ASIGNADOS_N_UNIDAD,"
                + "assigs.`minute` AS ASIGNADOS_MINUTOS,"
                + "assigs.`sector` AS ASIGNADOS_SECTOR,"
                + "assigs.`atraso` AS ASIGNADOS_ATRASO "
                + "FROM `assigs` assigs "
                + "WHERE assigs.`date`= '$P!{fechaIniDes}' AND "
                + "assigs.`time` BETWEEN '00:00:00' AND '23:59:59'";

        String txt = "por todas las unidades:";

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("fechaIniDes", campos.get("fechaIniDes"));
        parametro.put("fechaFinDes", campos.get("fechaIniDes"));
        parametro.put("quien", txt);
        parametro.put("empresa", Principal.company.getCompany());

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }
    
      public void GenerarPorCod() {
        String cod = campos.get("cod").toString();
        String sqlUnidad = "SELECT "
                + "assigs.`code` AS ASIGNADOS_COD_CLIENTE,"
                + "assigs.`date` AS ASIGNADOS_FECHA,"
                + "assigs.`time` AS ASIGNADOS_HORA,"
                + "assigs.`phone` AS ASIGNADOS_TELEFONO,"
                + "assigs.`client` AS ASIGNADOS_NOMBRE_APELLIDO_CLI,"
                + "assigs.`direction` AS ASIGNADOS_DIRECCION_CLI,"
                + "assigs.`note` AS ASIGNADOS_NOTA,"
                + "assigs.`vehiculo` AS ASIGNADOS_N_UNIDAD,"
                + "assigs.`minute` AS ASIGNADOS_MINUTOS,"
                + "assigs.`sector` AS ASIGNADOS_SECTOR,"
                + "assigs.`atraso` AS ASIGNADOS_ATRASO "
                + "FROM `assigs` assigs "
                + "WHERE assigs.`code` = $P{cod} "
                + "AND CONCAT(assigs.`date`, ' ', assigs.`time`) BETWEEN '"+campos.get("fechaIniDes")+" "+campos.get("horaIni")+"' "
                + "AND '"+campos.get("fechaFinDes")+" "+campos.get("horaFin")+"'";

        String txt = "por el cliente: " + cod;

        Map parametro = new HashMap();
        parametro.put("sql", sqlUnidad);
        parametro.put("cod", Integer.parseInt(cod));
        parametro.put("fechaIniDes", campos.get("fechaIniDes"));
        parametro.put("fechaFinDes", campos.get("fechaFinDes"));
        parametro.put("horaIni", campos.get("horaIni"));
        parametro.put("horaFin", campos.get("horaFin"));
        parametro.put("quien", txt);
        parametro.put("empresa", Principal.company.getCompany());
        parametro.put("usuario", Principal.userLogin.getUser());

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    public void GenerarPorUnidad() {
        String unidad = campos.get("uni").toString();
        String sqlUnidad = "SELECT "
                + "assigs.`code` AS ASIGNADOS_COD_CLIENTE,"
                + "assigs.`date` AS ASIGNADOS_FECHA,"
                + "assigs.`time` AS ASIGNADOS_HORA,"
                + "assigs.`phone` AS ASIGNADOS_TELEFONO,"
                + "assigs.`client` AS ASIGNADOS_NOMBRE_APELLIDO_CLI,"
                + "assigs.`direction` AS ASIGNADOS_DIRECCION_CLI,"
                + "assigs.`note` AS ASIGNADOS_NOTA,"
                + "assigs.`vehiculo` AS ASIGNADOS_N_UNIDAD,"
                + "assigs.`minute` AS ASIGNADOS_MINUTOS,"
                + "assigs.`sector` AS ASIGNADOS_SECTOR,"
                + "assigs.`atraso` AS ASIGNADOS_ATRASO"
                + " FROM `assigs` assigs "
                + "WHERE assigs.`vehiculo` = $P{uni} "
                + "AND CONCAT(assigs.`date`, ' ', assigs.`time`) BETWEEN '"+campos.get("fechaIniDes")+" "+campos.get("horaIni")+"' "
                + "AND '"+campos.get("fechaFinDes")+" "+campos.get("horaFin")+"'";

        String txt = "por la unidad vehiculo: " + unidad;

        Map parametro = new HashMap();
        parametro.put("sql", sqlUnidad);
        parametro.put("uni", Integer.parseInt(unidad));
        parametro.put("fechaIniDes", campos.get("fechaIniDes"));
        parametro.put("fechaFinDes", campos.get("fechaFinDes"));
        parametro.put("horaIni", campos.get("horaIni"));
        parametro.put("horaFin", campos.get("horaFin"));
        parametro.put("quien", txt);
        parametro.put("empresa", Principal.company.getCompany());
        parametro.put("usuario", Principal.userLogin.getUser());

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    public void GenerarDespachosFechHor() {
        String sql = "SELECT "
                + "assigs.`code` AS ASIGNADOS_COD_CLIENTE,"
                + "assigs.`date` AS ASIGNADOS_FECHA,"
                + "assigs.`time` AS ASIGNADOS_HORA,"
                + "assigs.`phone` AS ASIGNADOS_TELEFONO,"
                + "assigs.`client` AS ASIGNADOS_NOMBRE_APELLIDO_CLI,"
                + "assigs.`direction` AS ASIGNADOS_DIRECCION_CLI,"
                + "assigs.`note` AS ASIGNADOS_NOTA,"
                + "assigs.`vehiculo` AS ASIGNADOS_N_UNIDAD,"
                + "assigs.`minute` AS ASIGNADOS_MINUTOS,"
                + "assigs.`sector` AS ASIGNADOS_SECTOR,"
                + "assigs.`atraso` AS ASIGNADOS_ATRASO "
                + "FROM `assigs` assigs "
                + "WHERE CONCAT(assigs.`date`, ' ', assigs.`time`) BETWEEN '"+campos.get("fechaIniDes")+" "+campos.get("horaIni")+"' "
                + "AND '"+campos.get("fechaFinDes")+" "+campos.get("horaFin")+"'";
        
        String txt = "por todas las unidades:";

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("fechaIniDes", campos.get("fechaIniDes"));
        parametro.put("fechaFinDes", campos.get("fechaFinDes"));
        parametro.put("horaIni", campos.get("horaIni"));
        parametro.put("horaFin", campos.get("horaFin"));
        parametro.put("quien", txt);
        parametro.put("empresa", Principal.company.getCompany());

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    public void GenerarDespachosUserFechHor() {
        String sql = "SELECT "
                + "assigs.`code` AS ASIGNADOS_COD_CLIENTE,"
                + "assigs.`date` AS ASIGNADOS_FECHA,"
                + "assigs.`time` AS ASIGNADOS_HORA,"
                + "assigs.`phone` AS ASIGNADOS_TELEFONO,"
                + "assigs.`client` AS ASIGNADOS_NOMBRE_APELLIDO_CLI,"
                + "assigs.`direction` AS ASIGNADOS_DIRECCION_CLI,"
                + "assigs.`note` AS ASIGNADOS_NOTA,"
                + "assigs.`vehiculo` AS ASIGNADOS_N_UNIDAD,"
                + "assigs.`minute` AS ASIGNADOS_MINUTOS,"
                + "assigs.`sector` AS ASIGNADOS_SECTOR,"
                + "assigs.`atraso` AS ASIGNADOS_ATRASO "
                + "FROM `assigs` assigs "
                + "WHERE assigs.`id_user` = '$P!{user}' "
                + "AND CONCAT(assigs.`date`, ' ', assigs.`time`) BETWEEN '"+campos.get("fechaIniDes")+" "+campos.get("horaIni")+"' "
                + "AND '"+campos.get("fechaFinDes")+" "+campos.get("horaFin")+"'";
        
        String txt = "por todas las unidades:";

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("fechaIniDes", campos.get("fechaIniDes"));
        parametro.put("fechaFinDes", campos.get("fechaFinDes"));
        parametro.put("horaIni", campos.get("horaIni"));
        parametro.put("horaFin", campos.get("horaFin"));
        parametro.put("quien", txt);
        parametro.put("user", campos.get("user"));
        parametro.put("empresa", Principal.company.getCompany());

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera la lista de todos los Despachos Finalizados de la empresa
     */
    public void GenerarTodosLosDespachosPorTurno() {
        String sql = "SELECT "
                + "assigs.`code` AS ASIGNADOS_COD_CLIENTE,"
                + "assigs.`date` AS ASIGNADOS_FECHA,"
                + "assigs.`time` AS ASIGNADOS_HORA,"
                + "assigs.`phone` AS ASIGNADOS_TELEFONO,"
                + "assigs.`client` AS ASIGNADOS_NOMBRE_APELLIDO_CLI,"
                + "assigs.`direction` AS ASIGNADOS_DIRECCION_CLI,"
                + "assigs.`note` AS ASIGNADOS_NOTA,"
                + "assigs.`vehiculo` AS ASIGNADOS_N_UNIDAD,"
                + "assigs.`minute` AS ASIGNADOS_MINUTOS,"
                + "assigs.`sector` AS ASIGNADOS_SECTOR,"
                + "assigs.`atraso` AS ASIGNADOS_ATRASO"
                + " FROM `assigs` assigs "
                + "WHERE assigs.`date`='$P!{fechaIniDes}'"
                //+ "AND CONCAT(ASIGNADOS.`FECHA`,'-',ASIGNADOS.`HORA`) "
                //+ "BETWEEN CONCAT('$P!{fechaIni}','-',(SELECT TURNOS.`HORA_INI` from `TURNOS` TURNOS where TURNOS.`ID_TURNO` = $P!{turno})) "
                //+ "AND CONCAT(IF((SELECT TURNOS.`HORA_INI` from `TURNOS` TURNOS where TURNOS.`ID_TURNO` = $P!{turno}) > (SELECT TURNOS.`HORA_FIN` from `TURNOS` TURNOS where TURNOS.`ID_TURNO` = $P!{turno}),"                
                //+ "'$P!{fechaIni}' + INTERVAL 1 DAY,'$P!{fechaIni}'),'-',(SELECT TURNOS.`HORA_FIN` from `TURNOS` TURNOS where TURNOS.`ID_TURNO` = $P!{turno})) "
                //+ "AND ASIGNADOS.`ID_TURNO`= $P!{turno} "
                + "AND assigs.`id_user` = '$P!{user}' AND "
                + "assigs.`time` BETWEEN '00:00:00' AND '23:59:59'";

        String txt = "por todas las unidades:";

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("fechaIniDes", campos.get("fechaIniDes"));
        parametro.put("fechaFinDes", campos.get("fechaFinDes"));
        parametro.put("quien", txt);
        parametro.put("empresa", Principal.company.getCompany());
        parametro.put("user", campos.get("user"));
        parametro.put("nombre_user", campos.get("nombre_user"));

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }
}

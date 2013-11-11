/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.reportes;

import com.kradac.despachos.administration.Company;
import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.methods.Functions;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author christmo
 */
public class GenerarReporteClientes {

    private DataBase bd = new DataBase();
    private HashMap campos;
    //private String[] sesion;
    private Company empresa;
    private String usuario;
    private InputStream RutaJasper;

    public GenerarReporteClientes(DataBase cb, HashMap camp) {
        this.bd = cb;
        this.campos = camp;
        this.empresa = bd.loadCompany();
        RutaJasper = getClass().getResourceAsStream("plantillas/Clientes.jrxml");
    }

    /**
     * Genera segun los campos que se haya llenado
     */
    public void Generar() {

        if (campos.get("todo").equals(true)) {
            GenerarTodo();
        } else {
            if (!campos.get("cod").equals("")) {
                GenerarPorCodigo();
            } else {
                if (!campos.get("tel").equals("")) {
                    GenerarPorTelefono();
                } else {
                    if (!campos.get("nom").equals("")) {
                        GenerarPorNombre();
                    }
                }
            }
        }

    }

    /**
     * Generar el reporte de los clientes por un codigo determinado solo se
     * obtendra un serultado si existe...
     */
    private void GenerarTodo() {
        String sqlCodigo = "SELECT"
                + " clients.`phone` AS CLIENTES_TELEFONO,"
                + " clients.`code` AS CLIENTES_CODIGO,"
                + " clients.`name` AS CLIENTES_NOMBRE_APELLIDO_CLI,"
                + " clients.`direction` AS CLIENTES_DIRECCION_CLI,"
                + " clients.`sector` AS CLIENTES_SECTOR,"
                + " clients.`reference` AS CLIENTES_INFOR_ADICIONAL,"
                + " clients.`num_house` AS CLIENTES_NUM_CASA_CLI "
                + " FROM"
                + " `clients` clients";

        System.out.println("SQL: " + sqlCodigo);

        Map parametro = new HashMap();
        parametro.put("sql", sqlCodigo);
        parametro.put("empresa", empresa.getCompany());
        parametro.put("usuario", usuario);
        parametro.put("fechaIni",campos.get("fechaIni"));
        parametro.put("horaIni", campos.get("horaIni"));

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    private void GenerarPorCodigo() {
        String cod = campos.get("cod").toString();
        String sqlCodigo = "SELECT"
                + " clients.`phone` AS CLIENTES_TELEFONO,"
                + " clients.`code` AS CLIENTES_CODIGO,"
                + " clients.`name` AS CLIENTES_NOMBRE_APELLIDO_CLI,"
                + " clients.`direction` AS CLIENTES_DIRECCION_CLI,"
                + " clients.`sector` AS CLIENTES_SECTOR,"
                + " clients.`reference` AS CLIENTES_INFOR_ADICIONAL,"
                + " clients.`num_house` AS CLIENTES_NUM_CASA_CLI "
                + " FROM"
                + " `clients` clients"
                + " WHERE clients.`code` = $P{cod}";

        System.out.println("SQL: " + sqlCodigo);

        Map parametro = new HashMap();
        parametro.put("sql", sqlCodigo);
        parametro.put("cod", Integer.parseInt(cod));
        parametro.put("empresa", empresa.getCompany());
        parametro.put("usuario", usuario);
        parametro.put("fechaIni",campos.get("fechaIni"));
        parametro.put("horaIni", campos.get("horaIni"));

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Generar el reporte de los clientes por un telefono determinado solo se
     * obtendra un serultado si existe...
     */
    private void GenerarPorTelefono() {
        String telefono = campos.get("tel").toString();
        String sqlTelefono = "SELECT"
                + " clients.`phone` AS CLIENTES_TELEFONO,"
                + " clients.`code` AS CLIENTES_CODIGO,"
                + " clients.`name` AS CLIENTES_NOMBRE_APELLIDO_CLI,"
                + " clients.`direction` AS CLIENTES_DIRECCION_CLI,"
                + " clients.`sector` AS CLIENTES_SECTOR,"
                + " clients.`reference` AS CLIENTES_INFOR_ADICIONAL,"
                + " clients.`num_house` AS CLIENTES_NUM_CASA_CLI "
                + " FROM"
                + " `clients` clients"
                + " WHERE clients.`phone` = '$P!{tel}'";

        System.out.println("SQL: " + sqlTelefono);

        Map parametro = new HashMap();
        parametro.put("sql", sqlTelefono);
        parametro.put("tel", telefono);
        parametro.put("empresa", empresa.getCompany());
        parametro.put("usuario", usuario);
        parametro.put("fechaIni",campos.get("fechaIni"));
        parametro.put("horaIni", campos.get("horaIni"));

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera el reporte de todos los clientes que tengan ese nombre
     */
    private void GenerarPorNombre() {
        String nombre = campos.get("nom").toString();
        String sqlNombre = "SELECT"
                + " clients.`phone` AS CLIENTES_TELEFONO,"
                + " clients.`code` AS CLIENTES_CODIGO,"
                + " clients.`name` AS CLIENTES_NOMBRE_APELLIDO_CLI,"
                + " clients.`direction` AS CLIENTES_DIRECCION_CLI,"
                + " clients.`sector` AS CLIENTES_SECTOR,"
                + " clients.`reference` AS CLIENTES_INFOR_ADICIONAL,"
                + " clients.`num_house` AS CLIENTES_NUM_CASA_CLI "
                + " FROM"
                + " `clients` clients"
                + " WHERE clients.`name` LIKE '$P!{nom}%'";

        System.out.println("SQL: " + sqlNombre);

        Map parametro = new HashMap();
        parametro.put("sql", sqlNombre);
        parametro.put("nom", nombre);
        parametro.put("empresa", empresa.getCompany());
        parametro.put("usuario", usuario);
        parametro.put("fechaIni",campos.get("fechaIni"));
        parametro.put("horaIni", campos.get("horaIni"));

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera el reporte de todos los clientes que tengan ese nombre
     */
    /**
     * Genera la lista de todos los clientes de la empresa
     */
}

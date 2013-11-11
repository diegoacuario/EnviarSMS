/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.reportes;

import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author christmo
 */
public class GenerarReporte {
    private static DataBase db;

    /**
     * Genera el reporte para imprimirlo
     * @param par
     * @param ruta
     * @param bd
     */
    public static void Generar(Map par, InputStream ruta, DataBase bd) {
        db = new DataBase(Principal.fileConfig, Principal.numHost);
        
        try {
            Map parameters = par;
            //JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("plantillas/Clientes.jrxml"));
            JasperReport report = JasperCompileManager.compileReport(ruta);
            //InputStream repor GenerarReporteClientes.class.getResourceAsStream("/interfaz/Reportes/Clientes.jasper");
            //JasperReport report = JasperCompileManager.compileReport("/interfaz/Reportes/prueba.jrxml");
            JasperPrint print = JasperFillManager.fillReport(report, parameters, db.getConexion());
            // Exporta el informe a PDF
            //JasperExportManager.exportReportToPdfFile(print,"/tmp/demodos.pdf");
            //Para visualizar el pdf directamente desde java
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(GenerarReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        db.closeConexion();
    }
}

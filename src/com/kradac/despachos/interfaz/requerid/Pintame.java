/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.interfaz.requerid;

import com.kradac.despachos.interfaz.Principal;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dalton
 */
public class Pintame extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    Object valor = null;
    private Map mapCodVeh;
    private ArrayList codigo = new ArrayList();
    private ArrayList<Integer> color = new ArrayList();

    public Pintame(Map mapCodVeh, ArrayList codigo, ArrayList<Integer> color) {
        this.mapCodVeh = mapCodVeh;
        this.codigo = codigo;
        this.color = color;
    }

    @Override
    public Component getTableCellRendererComponent(final JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        table.setColumnSelectionAllowed(true);

        /*if (column == 0 || column == 2) { //EL NUMERO DE LA COLUMNA QUE DESEAMOS PINTAR
            setBackground(new Color(9, 187, 232));
        } else {
            setBackground(new Color(255, 255, 255));//EL COLOR DEL RESTO DE LAS COLUMNAS
        }*/
        
        int keyMapaCodVeh = Integer.parseInt(Principal.listVehiculos.getEncabezadosTablaVehiculosArrayList().get(column).toString());
        String codColor = mapCodVeh.get(keyMapaCodVeh).toString();
        if (codColor != null) {
            int ind = codigo.indexOf(codColor);
            if (ind != -1) {
                int colRGB = color.get(ind);
                Color cl = new Color(colRGB);
                setBackground(cl);
            }
        }
        /*try {
            if (codColor.equals("AC")) {
                setBackground(new Color(51, 255, 51));
            } else if (codColor.equals("OCU")) {
                setBackground(new Color(255, 9, 0));
            } else if (codColor.equals("ASI")) {
                setBackground(new Color(215, 156, 255));
            } else if (codColor.equals("FIJ")) {
                setBackground(new Color(255, 128, 0));
            } else if (codColor.equals("INI")) {
                setBackground(new Color(255, 255, 0));
            } else if (codColor.equals("DES")) {
                setBackground(new Color(115, 192, 255));
            } else if (codColor.equals("RD")) {
                setBackground(new Color(173, 255, 186));
            } else if (codColor.equals("ST")) {
                setBackground(new Color(102, 102, 102));
            }
        } catch (ClassCastException e) {
            System.out.println("Error");
        }*/
        /**
         * **********************************
         */
        setForeground(Color.black);
        return this;
    }
}

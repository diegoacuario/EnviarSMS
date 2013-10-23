package com.kradac.despachos.interfaz.requerid;

import java.awt.Component;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class FormatTable extends DefaultTableCellRenderer {

    private ArrayList<String> vehiculos = new ArrayList();
    private ArrayList codigo = new ArrayList();
    private ArrayList<Integer> color = new ArrayList();
    private Map mapCodVeh;
    int[] codigoInt;

    public FormatTable(int[] c) {
        this.codigoInt = c;
    }

    public FormatTable(ArrayList<String> vehiculos, Map mapCodVeh, ArrayList codigo,  ArrayList<Integer> color) {
        this.vehiculos = vehiculos;
        this.mapCodVeh = mapCodVeh;
        this.codigo = codigo;
        this.color = color;
        
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected,
            boolean hasFocus,
            int row, int col) {

        int keyMapaCodVeh = Integer.parseInt(vehiculos.get(col));
        String codColor = mapCodVeh.get(keyMapaCodVeh).toString();        
        if (codColor != null) {
            int ind = codigo.indexOf(codColor);
            if (ind != -1) {
                int colRGB = color.get(ind);
                Color cl = new Color(colRGB);
                setHorizontalAlignment(SwingConstants.CENTER);
                setVerticalAlignment(SwingConstants.CENTER);
                setBackground(cl);
            }
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
    }
}

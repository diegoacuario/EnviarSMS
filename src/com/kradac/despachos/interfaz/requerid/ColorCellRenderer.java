
package com.kradac.despachos.interfaz.requerid;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author KRADAC
 */
public class ColorCellRenderer implements ListCellRenderer {

    ArrayList<String> codigo;
    ArrayList<Integer> color;
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
    // width doesn't matter as combobox will size
    private final static Dimension preferredSize = new Dimension(200, 25);
    int i = 0;

    public ColorCellRenderer(ArrayList<String> cod, ArrayList<Integer> col) {
        codigo = cod;
        color = col;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {


        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);

        if (index != -1) {
            Color auxcol = new Color(Integer.parseInt(color.get(index).toString()));
            renderer.setBackground(auxcol);
        }
        renderer.setPreferredSize(preferredSize);
        return renderer;
    }
}

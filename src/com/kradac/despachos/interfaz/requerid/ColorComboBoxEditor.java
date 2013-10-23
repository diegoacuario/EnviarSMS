package com.kradac.despachos.interfaz.requerid;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.event.EventListenerList;

/**
 *
 * @author KRADAC
 */
public class ColorComboBoxEditor implements ComboBoxEditor {

    final protected JButton editor;
    ArrayList<String> codigo;
    ArrayList<String> color;
    Map eticol;
    transient protected EventListenerList listenerList = new EventListenerList();

    public ColorComboBoxEditor(ArrayList<String> cod, ArrayList<String> col) {
        editor = new JButton("");
        codigo = cod;
        color = col;
    }

    public ColorComboBoxEditor(Map etCol) {
        editor = new JButton("");
        eticol = etCol;
    }

    @Override
    public void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }

    @Override
    public Component getEditorComponent() {
        return editor;
    }

    @Override
    public Object getItem() {
        return editor.getBackground();
    }

    @Override
    public void removeActionListener(ActionListener l) {
        listenerList.remove(ActionListener.class, l);
    }

    @Override
    public void selectAll() {
        // ignore
    }

    @Override
    public void setItem(Object newValue) {
        try {
            int codStr = (Integer) eticol.get(newValue);
            if (codStr != -1) {
                Color cl = new Color(codStr);
                editor.setBackground(cl);
                editor.setText(newValue.toString());
            }
        } catch (NullPointerException e) {
        }
    }
}

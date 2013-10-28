/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Zona;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListZona {
    private List<Zona> zonas;

    public ListZona() {
        zonas = new ArrayList<>();
    }
    
    public void addZona(Zona zona) {
        boolean existe = false;
        for (Zona z : getZonas()) {            
            if (z.getIdZona() == zona.getIdZona()) {
                existe = true;
            }
        }
        
        if (!existe)
            getZonas().add(zona);
        else {
            JOptionPane.showMessageDialog(null, "No pueden haber dos Zonas con el mismo ID");
            System.exit(0);
        }
    }
    
    public Zona getZonaById(int idZona) {
        for (Zona z : getZonas()) {
            if (z.getIdZona()== idZona) {
                return z;
            }
        }
        return null;
    }

    /**
     * @return the zonas
     */
    public List<Zona> getZonas() {
        return zonas;
    }
}

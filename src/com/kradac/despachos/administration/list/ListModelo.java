/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Modelo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListModelo {
    List<Modelo> modelos;

    public ListModelo() {
        modelos = new ArrayList<>();
    }
    
    public void addModelo(Modelo modelo) {
        boolean existe = false;
        for (Modelo m : modelos) {            
            if (m.getIdModeloVehiculo() == modelo.getIdModeloVehiculo()) {
                existe = true;
            }
        }
        
        if (!existe)
            modelos.add(modelo);
        else {
            JOptionPane.showMessageDialog(null, "No pueden haber dos Modelos con el mismo ID");
            System.exit(0);
        }
    }
    
    public Modelo getModeloById(int idModelo) {
        for (Modelo m : modelos) {
            if (m.getIdModeloVehiculo()== idModelo) {
                return m;
            }
        }
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Marca;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListMarca {
    List<Marca> marcas;

    public ListMarca() {
        marcas = new ArrayList<>();
    }
    
    public void addMarca(Marca marca) {
        boolean existe = false;
        for (Marca m : marcas) {            
            if (m.getIdMarcaVehiculo() == marca.getIdMarcaVehiculo()) {
                existe = true;
            }
        }
        
        if (!existe)
            marcas.add(marca);
        else {
            JOptionPane.showMessageDialog(null, "No pueden haber dos Marcas con el mismo ID");
            System.exit(0);
        }
    }
    
    public Marca getMarcaById(int idMarca) {
        for (Marca m : marcas) {
            if (m.getIdMarcaVehiculo()== idMarca) {
                return m;
            }
        }
        return null;
    }
}

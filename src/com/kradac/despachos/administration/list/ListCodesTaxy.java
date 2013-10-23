/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.CodesTaxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListCodesTaxy {
    private List<CodesTaxy> codesTaxys;

    public ListCodesTaxy() {
        codesTaxys = new ArrayList<>();
    }
    
    public void addCodesTaxy(CodesTaxy codesTaxy) {
        boolean existe = false;
        for (CodesTaxy c : getCodesTaxys()) {            
            if (c.getIdCodigo().equals(codesTaxy.getIdCodigo())) {
                existe = true;
            }
        }
        
        if (!existe)
            getCodesTaxys().add(codesTaxy);
        else {
            JOptionPane.showMessageDialog(null, "No pueden haber dos Codigos de Taxi con el mismo ID");
            System.exit(0);
        }
    }
    
    public CodesTaxy getCodesTaxyById(String idCodigo) {
        for (CodesTaxy c : getCodesTaxys()) {
            if (c.getIdCodigo().equals(idCodigo)) {
                return c;
            }
        }
        return null;
    }
    
    public CodesTaxy getCodeTaxyByEtiqueta(String etiqueta) {
        for (CodesTaxy c : getCodesTaxys()) {
            if (c.getEtiqueta().equals(etiqueta)) {
                return c;
            }
        }
        return null;
    }
    
    public ArrayList<String> getIdCodigo() {
        ArrayList listAux = new ArrayList();
        for (CodesTaxy c : getCodesTaxys()) {
            listAux.add(c.getIdCodigo());
        }
        return listAux;
    }
    
    public ArrayList<String> getEtiqueta() {
        ArrayList listAux = new ArrayList();
        for (CodesTaxy c : getCodesTaxys()) {
            listAux.add(c.getEtiqueta());
        }
        return listAux;
    }
    
    public ArrayList<Integer> getColor() {
        ArrayList listAux = new ArrayList();
        for (CodesTaxy c : getCodesTaxys()) {
            listAux.add(c.getColor());
        }
        return listAux;
    }
    
    public Map getMapEtiqColor() {
        Map mapEtiqColor = new HashMap();
        for (CodesTaxy c : getCodesTaxys()) {
            mapEtiqColor.put(c.getEtiqueta(), c.getColor());
        }
        return mapEtiqColor;
    }

    /**
     * @return the codesTaxys
     */
    public List<CodesTaxy> getCodesTaxys() {
        return codesTaxys;
    }
}

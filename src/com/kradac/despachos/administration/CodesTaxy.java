/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration;

/**
 *
 * @author Dalton
 */
public class CodesTaxy {
    private String idCodigo;
    private String etiqueta;
    private int color;

    public CodesTaxy(String idCodigo, String etiqueta, int color) {
        this.idCodigo = idCodigo;
        this.etiqueta = etiqueta;
        this.color = color;
    }
    

    /**
     * @return the idCodigo
     */
    public String getIdCodigo() {
        return idCodigo;
    }

    /**
     * @param idCodigo the idCodigo to set
     */
    public void setIdCodigo(String idCodigo) {
        this.idCodigo = idCodigo;
    }

    /**
     * @return the etiqueta
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * @param etiqueta the etiqueta to set
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(int color) {
        this.color = color;
    }
    
    
}

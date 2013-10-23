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
public class StateCivil {
    int idStateCivil;
    String stateCivil;

    public StateCivil(int idStateCivil, String stateCivil) {
        this.idStateCivil = idStateCivil;
        this.stateCivil = stateCivil;
    }

    public int getIdStateCivil() {
        return idStateCivil;
    }

    public void setIdStateCivil(int idStateCivil) {
        this.idStateCivil = idStateCivil;
    }

    public String getStateCivil() {
        return stateCivil;
    }

    public void setStateCivil(String stateCivil) {
        this.stateCivil = stateCivil;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.StateCivil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dalton
 */
public class ListStateCivil {
    private List<StateCivil> stateCivils;

    public ListStateCivil() {
        stateCivils = new ArrayList<>();
    }
    
    public void addStateCivil(StateCivil stateCivil) {
        getStateCivils().add(stateCivil);
    }

    /**
     * @return the stateCivils
     */
    public List<StateCivil> getStateCivils() {
        return stateCivils;
    }
    
    public StateCivil getStateCivilById(int id) {
        for (StateCivil sc : stateCivils) {
            if (sc.getIdStateCivil() == id) {
                return sc;
            }
        }
        return null;
    }
    
    public StateCivil getStateCivilByState(String state) {
        for (StateCivil sc : stateCivils) {
            if (sc.getStateCivil().equals(state)) {
                return sc;
            }
        }
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Turn;
import com.kradac.despachos.methods.Functions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dalton
 */
public class ListTurn {
    private List<Turn> turns;

    public ListTurn() {
        turns = new ArrayList<>();
    }
    
    public void addTurn(Turn turn) {
        getTurns().add(turn);
    }
    
    public Turn getTurnById(int idTurn) {
        for (Turn t : getTurns()) {
            if (t.getIdTurn()== idTurn) {
                return t;
            }
        }
        return null;
    }
    
    public Turn getTurnByTimeNow() {
        String timeNow = Functions.getTime();
        for (Turn t : getTurns()) {
            if (t.getTimeStart().compareTo(t.getTimeFinish()) >= 0) {
                if (timeNow.compareTo(t.getTimeStart()) >= 0 && timeNow.compareTo(t.getTimeFinish()) >= 0 || timeNow.compareTo(t.getTimeFinish()) <= 0) {
                    return t;
                }
            } else {
                if (timeNow.compareTo(t.getTimeStart()) >= 0 && timeNow.compareTo(t.getTimeFinish()) <= 0) {
                    return t;
                }
            }
            
        }
        return null;
    }

    /**
     * @return the turns
     */
    public List<Turn> getTurns() {
        return turns;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.ByDispatch;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dalton
 */
public class ListByDispatch {
    private List<ByDispatch> byDispatchs;

    public ListByDispatch() {
        byDispatchs = new ArrayList<>();
    }
    
    public void addByDispatch(ByDispatch dispatch) {
        getByDispatchs().add(dispatch);
    }
    

    /**
     * @return the byDispatchs
     */
    public List<ByDispatch> getByDispatchs() {
        return byDispatchs;
    }
    
    public void deleteByDispatch(int code) {
        for (ByDispatch p : byDispatchs) {
            if (p.getCode() == code) {
                byDispatchs.remove(p);
            }
        }
    }
    
    public ByDispatch getLastDispatch(){
        ByDispatch d = null;
        int cantDispatch = byDispatchs.size();
        if (cantDispatch > 0) {
            d = byDispatchs.get(byDispatchs.size()-1); 
        }
        return d;
    }
}

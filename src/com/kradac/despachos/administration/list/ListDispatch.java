/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Dispatch;
import com.kradac.despachos.interfaz.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dalton
 */
public class ListDispatch {
    private List<Dispatch> dispatchs;

    public ListDispatch() {
        dispatchs = new ArrayList<>();
    }
    
    public void addDispatch(Dispatch dispatch) {
        getDispatchs().add(dispatch);
    }
    

    /**
     * @return the dispatchs
     */
    public List<Dispatch> getDispatchs() {
        return dispatchs;
    }
    
    public Dispatch getLastDispatch(){
        Dispatch d = null;
        int cantDispatch = dispatchs.size();
        if (cantDispatch > 0) {
            d = dispatchs.get(dispatchs.size()-1); 
        }
        return d;
    }
    
    public void loadDispatch(){
        for (Dispatch p : dispatchs) {
            Principal.modelTableDispatch.insertRow(0, changeToArrayDispatch(p));
        }
    }
    
    public String[] changeToArrayDispatch(Dispatch dispatch) {
        String[] dataDispatch = {
            dispatch.getTime(),
            dispatch.getPhone(),
            "" + dispatch.getCode(),
            dispatch.getClient(),
            dispatch.getSector(),
            dispatch.getDirection(),
            "" + dispatch.getVehiculo(),
            "" + dispatch.getMinute(),
            dispatch.getTimeAsig(),
            dispatch.getPlaca(),
            "" + dispatch.getAtraso(),
            dispatch.getNote()
        };
        return dataDispatch;
    }
}

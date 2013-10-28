/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Dispatch;
import com.kradac.despachos.interfaz.Principal;
import com.kradac.despachos.methods.Functions;
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
    
    public void loadDispatchByClient(String client){
        clearTableDispatch();
        for (Dispatch p : dispatchs) {
            if (p.getClient().toUpperCase().contains(client.toUpperCase())) {
                Principal.modelTableDispatch.insertRow(0, changeToArrayDispatch(p));
            }
        }
    }
    
    public void loadDispatchByCode(int code){
        clearTableDispatch();
        for (Dispatch p : dispatchs) {
            if (p.getCode() == code) {
                Principal.modelTableDispatch.insertRow(0, changeToArrayDispatch(p));
            }
        }
    }
    
    public void loadDispatchByPhone(String phone){
        clearTableDispatch();
        for (Dispatch p : dispatchs) {
            if (p.getPhone().equals(phone)) {
                Principal.modelTableDispatch.insertRow(0, changeToArrayDispatch(p));
            }
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
    
    public int getDispatchByDayVeh(int vehiculo) {
        int c = 0;
        String dateNow = Functions.getDate();
        for (Dispatch d : dispatchs) {
            if (vehiculo == d.getVehiculo() && d.getDate().equals(dateNow)) {
                c++;
            }
        }
        return c;
    }
    
    public Dispatch getDispatchByCPT(int code, String phone, String time, String client) {
        for (Dispatch d : dispatchs) {
            if (d.getCode() == code && d.getPhone().equals(phone) && d.getTime().equals(time) && d.getClient().equals(client)) {
                return d;
            }
        }
        return null;
    }
    
    private void clearTableDispatch() {
        int n_filas = Principal.tblDispatchs.getRowCount();
        for (int i = 0; i < n_filas; i++) {
            Principal.modelTableDispatch.removeRow(0);
        }
    }
}

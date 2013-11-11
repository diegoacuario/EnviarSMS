/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Call;
import com.kradac.despachos.interfaz.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dalton
 */
public class ListCall {

    private List<Call> calls;

    public ListCall() {
        calls = new ArrayList<>();
    }

    public void addCall(Call call) {
        calls.add(call);
        Principal.modelTableCall.insertRow(0, changeToArrayCall(call));
    }
    
    public void addCallClient(Call call) {
        boolean existe = false;
        for (Call c : calls) {
            if ((c.getDate() + " " + c.getTime()).equals(call.getDate() + " " + call.getTime())) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            calls.add(call);
            Principal.modelTableCall.insertRow(0, changeToArrayCall(call));
        }
    }

    /**
     * @return the calls
     */
    public List<Call> getCalls() {
        return calls;
    }
    
    public Call getCallByPhone(String phone) {
        for (Call call : calls) {
            if (call.getPhone().equals(phone)) {
                return call;
            }
        }
        return null;
    }
    
    public String[] changeToArrayCall(Call c) {
        String [] dataCall = {
            c.getLine(),
            c.getTime(),
            c.getPhone(),
            c.getClient(),
            c.getDirection(),
            c.getReference()
        };
        return dataCall;
    }
}

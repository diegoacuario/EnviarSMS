/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Pending;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListPending {
    private List<Pending> pendings;

    public ListPending() {
        pendings = new ArrayList<>();
    }
    
    public void addPending(Pending pending) {
        getPendings().add(pending);
    }

    /**
     * @return the pendings
     */
    public List<Pending> getPendings() {
        return pendings;
    }
}

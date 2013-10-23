/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.interfaz.requerid;

import com.kradac.despachos.methods.Functions;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

/**
 *
 * @author Dalton
 */
public class Time extends TimerTask{
    private JLabel lblDate;
    private JLabel lblTime;
    
    public void start (JLabel lblDate, JLabel lblTime) {
        this.lblDate = lblDate;
        this.lblTime = lblTime;
        Timer timer = new Timer();
        timer.schedule(this, 0, 1000);
    }
    
    @Override
    public void run() {
        this.lblTime.setText(Functions.getTime());
        this.lblDate.setText(Functions.getDate());
    }
}

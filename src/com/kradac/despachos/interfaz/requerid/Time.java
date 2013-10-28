/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.interfaz.requerid;

import com.kradac.despachos.interfaz.Principal;
import com.kradac.despachos.methods.Functions;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

/**
 *
 * @author Dalton
 */
public class Time extends TimerTask{
    
    public void start (JLabel lblDate, JLabel lblTime) {
        Timer timer = new Timer();
        timer.schedule(this, 0, 1000);
    }
    
    @Override
    public void run() {
        String date = Functions.getDate();
        String time = Functions.getTime();
        Principal.lblTime.setText(time);
        Principal.lblDate.setText(date);
        
        Principal.listPending.existePending(date, time);
        updateTimeByDispatch(date, time);
    }
    
    public void updateTimeByDispatch (String date, String time){
        for (int fila = 0; fila < Principal.tblByDispatch.getRowCount(); fila++) {
            for (int columna = 0; columna < Principal.tblByDispatch.getColumnCount(); columna++) {
                if (columna == 10) {
                    String atraso = Principal.modelTableByDispatch.getValueAt(fila, columna).toString();
                    if (!atraso.equals("")) {
                        String timeDispatch = Principal.modelTableByDispatch.getValueAt(fila, 8).toString();
                        String [] date1 = date.split("-");
                        String [] time1 = time.split(":");
                        String [] time2 = timeDispatch.split(":");
                        long dateTimeActual = new GregorianCalendar(
                                Integer.parseInt(date1[0]), 
                                Integer.parseInt(date1[1]),
                                Integer.parseInt(date1[2]),
                                Integer.parseInt(time1[0]),
                                Integer.parseInt(time1[1]),
                                Integer.parseInt(time1[2])).getTimeInMillis();
                        
                        long dateTimeDispatch = new GregorianCalendar(
                                Integer.parseInt(date1[0]), 
                                Integer.parseInt(date1[1]),
                                Integer.parseInt(date1[2]),
                                Integer.parseInt(time2[0]),
                                Integer.parseInt(time2[1]),
                                Integer.parseInt(time2[2])).getTimeInMillis();
                        
                        long diferencia = dateTimeActual - dateTimeDispatch;
                        int minute = Integer.parseInt(new java.text.SimpleDateFormat("ss").format(new Date(diferencia)).toString());
                        if (minute == 59) {
                            int difAtraso = Integer.parseInt(atraso) + 1;
                            Principal.modelTableByDispatch.setValueAt(difAtraso, fila, 10);
                        }
                    }
                }
            }
        }
    }
}

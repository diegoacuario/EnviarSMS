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
public class Turn {
    private int idTurn;
    private String timeStart;
    private String timeFinish;

    public Turn(int idTurn, String timeStart, String timeFinish) {
        this.idTurn = idTurn;
        this.timeStart = timeStart;
        this.timeFinish = timeFinish;
    }
    
    /**
     * @return the idTurn
     */
    public int getIdTurn() {
        return idTurn;
    }

    /**
     * @param idTurn the idTurn to set
     */
    public void setIdTurn(int idTurn) {
        this.idTurn = idTurn;
    }

    /**
     * @return the timeStart
     */
    public String getTimeStart() {
        return timeStart;
    }

    /**
     * @param timeStart the timeStart to set
     */
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * @return the timeFinish
     */
    public String getTimeFinish() {
        return timeFinish;
    }

    /**
     * @param timeFinish the timeFinish to set
     */
    public void setTimeFinish(String timeFinish) {
        this.timeFinish = timeFinish;
    }
}

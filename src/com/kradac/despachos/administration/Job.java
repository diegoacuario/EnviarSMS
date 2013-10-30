/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration;

/**
 *
 * @author dalton
 */
public class Job {
    private int idJob;
    private String job;

    public Job(int idJob, String job) {
        this.idJob = idJob;
        this.job = job;
    }

    /**
     * @return the idJob
     */
    public int getIdJob() {
        return idJob;
    }

    /**
     * @param idJob the idJob to set
     */
    public void setIdJob(int idJob) {
        this.idJob = idJob;
    }

    /**
     * @return the job
     */
    public String getJob() {
        return job;
    }

    /**
     * @param job the job to set
     */
    public void setJob(String job) {
        this.job = job;
    }
    
    
}

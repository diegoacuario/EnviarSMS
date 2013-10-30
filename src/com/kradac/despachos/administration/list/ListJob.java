/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Job;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dalton
 */
public class ListJob {
    private List<Job> jobs;

    public ListJob() {
        jobs = new ArrayList<>();
    }
    
    public void addJobs(Job zona) {
        jobs.add(zona);
    }

    /**
     * @return the jobs
     */
    public List<Job> getJobs() {
        return jobs;
    }
    
    public Job getJobById(int idJob){
        for (Job j : jobs) {
            if (j.getIdJob() == idJob) {
                return j;
            }
        }
        return null;
    }
    
    public Job getJobByName(String job){
        for (Job j : jobs) {
            if (j.getJob().equals(job)) {
                return j;
            }
        }
        return null;
    }
}

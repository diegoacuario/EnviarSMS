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
public class Person {
    private String cedula;
    private String name;
    private String lastname;
    private String phone;
    private String email;
    private String direction;
    private String numHouse;
    private String typeSangre;
    private StateCivil stateCivil;
    private String conyuge;
    private String image;
    private Job job;

    public Person(String cedula, String name, String lastname, String phone, String email, String direction, String numHouse, String typeSangre, StateCivil stateCivil, String conyuge, String image, Job job) {
        this.cedula = cedula;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.direction = direction;
        this.numHouse = numHouse;
        this.typeSangre = typeSangre;
        this.stateCivil = stateCivil;
        this.conyuge = conyuge;
        this.image = image;
        this.job = job;
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * @return the numHouse
     */
    public String getNumHouse() {
        return numHouse;
    }

    /**
     * @param numHouse the numHouse to set
     */
    public void setNumHouse(String numHouse) {
        this.numHouse = numHouse;
    }

    /**
     * @return the typeSangre
     */
    public String getTypeSangre() {
        return typeSangre;
    }

    /**
     * @param typeSangre the typeSangre to set
     */
    public void setTypeSangre(String typeSangre) {
        this.typeSangre = typeSangre;
    }

    /**
     * @return the stateCivil
     */
    public StateCivil getStateCivil() {
        return stateCivil;
    }

    /**
     * @param stateCivil the stateCivil to set
     */
    public void setStateCivil(StateCivil stateCivil) {
        this.stateCivil = stateCivil;
    }

    /**
     * @return the conyuge
     */
    public String getConyuge() {
        return conyuge;
    }

    /**
     * @param conyuge the conyuge to set
     */
    public void setConyuge(String conyuge) {
        this.conyuge = conyuge;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the job
     */
    public Job getJob() {
        return job;
    }

    /**
     * @param job the job to set
     */
    public void setJob(Job job) {
        this.job = job;
    }
}

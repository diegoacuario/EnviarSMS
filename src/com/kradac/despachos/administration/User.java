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
public class User {
    private String user;
    private String password;
    private RolUser rolUser;
    private Person person;

    public User(String user, String password, RolUser rolUser, Person person) {        
        this.user = user;
        this.password = password;        
        this.rolUser = rolUser;
        this.person = person;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the rolUser
     */
    public RolUser getRolUser() {
        return rolUser;
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param rolUser the rolUser to set
     */
    public void setRolUser(RolUser rolUser) {
        this.rolUser = rolUser;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

}

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
public class Company {
    String idCompany;
    String company;
    String email;
    String phone;
    String direction;
    double latitud;
    double longitud;

    public Company(String idCompany, String company, String email, String phone, String direction, double latitud, double longitud) {
        this.idCompany = idCompany;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.direction = direction;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
    
}

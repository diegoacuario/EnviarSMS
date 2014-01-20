/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Person;
import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListPerson {
    
    private final DataBase db;
    private List<Person> persons;

    public ListPerson() { //Una ves que ya el Programa este corriendo
        this.persons = new ArrayList<>();
        this.db = new DataBase(Principal.fileConfig, Principal.numHost);
    }

    public ListPerson(Properties p, int numHost) { //Cuando va a loguearse en el Login
        this.persons = new ArrayList<>();
        this.db = new DataBase(p, numHost);
    }

    /**
     * @return the persons
     */
    public List<Person> getPersons() {
        return persons;
    }

    public void addPerson(Person person) {
        boolean existe = false;
        for (Person p : persons) {
            if (p.getCedula().equals(person.getCedula())) {
                existe = true;
            }
        }

        if (!existe) {
            persons.add(person);
        } else {
            JOptionPane.showMessageDialog(null, "La Persona Ya Existe");
        }
    }

    public int addNewPerson(Person person) {
        int existe = -1;
        for (Person p : persons) {
            if (p.getCedula().equals(person.getCedula())) {
                existe = 0;
            }
        }
        if (existe == -1) {
            if (db.insertPerson(person)) {
                existe = 1;
                persons.add(person);
            }
        }
        return existe;
    }

    public boolean deletePerson(String cedula) {
        for (Person p : persons) {
            if (p.getCedula().equals(cedula)) {
                if (db.deletePerson(cedula)) {
                    persons.remove(p);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean updatePerson(Person person, String cedula) {
        for (Person p : persons) {
            if (p.getCedula().equals(cedula)) {
                if (db.updatePerson(person, cedula)) {
                    p.setCedula(person.getCedula());
                    p.setName(person.getName());
                    p.setLastname(person.getLastname());
                    p.setEmail(person.getEmail());
                    p.setPhone(person.getPhone());
                    p.setDirection(person.getDirection());
                    p.setNumHouse(person.getNumHouse());
                    p.setTypeSangre(person.getTypeSangre());
                    p.setStateCivil(person.getStateCivil());
                    p.setConyuge(person.getConyuge());
                    p.setImage(person.getImage());
                    p.setJob(person.getJob());
                    return true;
                }
            }
        }
        return false;
    }

    public Person getPersonByCedula(String cedula) {
        for (Person p : persons) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        return null;
    }

    public Person getPersonByPhone(String phone) {
        for (Person p : persons) {
            if (p.getPhone().equals(phone)) {
                return p;
            }
        }
        return null;
    }
    
    public Person getPersonByPerson(String person) {
        for (Person p : persons) {
            if (person.equals(p.getLastname()+" "+p.getName())) {
                return p;
            }
        }
        return null;
    }

    public int getSize() {
        return persons.size();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Person;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListPerson {
    
    private List<Person> persons;

    public ListPerson() {
        persons = new ArrayList<>();
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

    public boolean addNewPerson(Person person) {
        boolean existe = false;
        for (Person p : persons) {
            if (p.getCedula().equals(person.getCedula())) {
                existe = true;
            }
        }

        persons.add(person);
        return existe;
    }

    public boolean deletePerson(String cedula) {
        for (Person p : persons) {
            if (p.getCedula().equals(cedula)) {
                persons.remove(p);
                return true;
            }
        }
        return false;
    }

    public boolean updatePerson(Person person, String cedula) {
        for (Person p : persons) {
            if (p.getCedula().equals(cedula)) {
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
                
                return true;
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

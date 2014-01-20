/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.Client;
import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListClients {

    private List<Client> clients;
    private DataBase db;

    public ListClients() {
        clients = new ArrayList<>();
        db = new DataBase(Principal.fileConfig, Principal.numHost);
    }

    /**
     * @return the clients
     */
    public List<Client> getClients() {
        return clients;
    }

    public int addNewClient(Client client) {
        int estado = -1;//ASUMO QUE NO EXISTE
        for (Client p : clients) {
            if (p.getCode() == client.getCode() && p.getPhone().equals(client.getPhone())) {
                estado = 0;//CUANDO EXISTE
            }
        }

        if (estado == -1) {
            if (db.insertClient(client)) {
                estado = 1;//CUANDO SE HA GUARDADO
                clients.add(client);
            }
        }
        return estado;
    }

    public void addClient(Client client) {
        boolean existe = false;
        for (Client p : clients) {
            if (p.getCode() == client.getCode() && p.getPhone().equals(client.getPhone())) {
                existe = true;
            }
        }

        if (!existe) {
            clients.add(client);
        } else {
            JOptionPane.showMessageDialog(null, "La Clienta Ya Existe: "+client.getCode());
        }
    }

    public void deleteClient(int code) {
        for (Client p : clients) {
            if (p.getCode() == code) {
                clients.remove(p);
                break;
            }
        }
    }

    public boolean updateClient(Client client, int code) {
        for (Client c : clients) {
            if (c.getCode() == code) {
                if (db.updateClient(client, code)) {
                    c.setPhone(client.getPhone());
                    c.setName(client.getName());
                    c.setLastname(client.getLastname());
                    c.setDirection(client.getDirection());
                    c.setNumHouse(client.getNumHouse());
                    c.setSector(client.getSector());
                    c.setReference(client.getReference());
                    c.setLatitud(client.getLatitud());
                    c.setLongitud(client.getLongitud());
                    c.setDestino(client.getDestino());

                    return true;
                }

            }
        }
        return false;
    }

    public Client getClientByCode(int code) {
        for (Client p : clients) {
            if (p.getCode() == code) {
                return p;
            }
        }
        return null;
    }

    public Client getClientByPhone(String phone) {
        for (Client p : clients) {
            if (p.getPhone().equals(phone)) {
                return p;
            }
        }
        return null;
    }

    public int getSize() {
        return clients.size();
    }

    public int getLastDispatch() {
        int sizeClient = getSize(), lastClient = 0;
        if (sizeClient > 0) {
            return clients.get(sizeClient - 1).getCode() + 1;
        }
        return lastClient;
    }
    
    public int getCodeAleatorio(){
        int start = 0;
        if (Principal.company.getIdCompany().equals("QLB")) {
            start = 27400;
        }
        
        for (int i = start; i < getSize(); i++) {
            Client c = getClientByCode(i);
            if (c == null) {
                return i;
            }
        }
        return getLastDispatch();
    }
}

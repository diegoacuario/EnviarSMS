/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kradac.despachos.administration.list;

import com.kradac.despachos.administration.CodesTaxy;
import com.kradac.despachos.administration.Vehiculo;
import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class ListVehiculos {
    private List<Vehiculo> vehiculos;
    private DataBase db;

    public ListVehiculos() {
        vehiculos = new ArrayList<>();
        db = new DataBase(Principal.fileConfig, Principal.host);
    }
    
    public void addVehiculo(Vehiculo vehiculo) {
        boolean existe = false;
        for (Vehiculo v : getVehiculos()) {            
            if (v.getPlaca().equals(vehiculo.getPlaca())) {
                existe = true;
            }
        }
        
        if (!existe)
            getVehiculos().add(vehiculo);
        else {
            JOptionPane.showMessageDialog(null, "No pueden haber dos Vehiculos con la misma Placa");
            System.exit(0);
        }
    }
    
    public void addNewVehiculo(Vehiculo vehiculo) {
        boolean existe = false;
        for (Vehiculo v : getVehiculos()) {
            if (v.getPlaca().equals(vehiculo.getPlaca())) {
                existe = true;
            }
        }

        if (!existe) {
            if (db.insertVehiculo(vehiculo)) {
                getVehiculos().add(vehiculo);
                db.closeConexion();            }

        } else {
            JOptionPane.showMessageDialog(null, "No pueden haber dos Vehiculos con la misma Placa");
            System.exit(0);
        }
    }
    
    public Vehiculo getVehiculoById(String placa) {
        for (Vehiculo v : getVehiculos()) {
            if (v.getPlaca().equals(placa)) {
                return v;
            }
        }
        return null;
    }
    
    public Vehiculo getVehiculoByVehiculo(int vehiculo) {
        for (Vehiculo v : getVehiculos()) {
            if (v.getVehiculo() == vehiculo) {
                return v;
            }
        }
        return null;
    }
    
    public int getSize() {
        return getVehiculos().size();
    }
    
    public String[] getEncabezadosTablaVehiculosArrayString() {
        ArrayList<String> listAux = new ArrayList();
        for (Vehiculo v : getVehiculos()) {
            listAux.add(""+v.getVehiculo());
        }
        
        String [] vehiculosArray = new String[listAux.size()];
        return listAux.toArray(vehiculosArray);
    }
    
    public ArrayList getEncabezadosTablaVehiculosArrayList() {
        ArrayList listAux = new ArrayList();
        for (Vehiculo v : getVehiculos()) {
            listAux.add(""+v.getVehiculo());
        }
        return listAux;
    }
    
    public String[] getFilasNumeroDespachos() {
        String[] cant = new String[getSize()];
        for (int i = 0; i < cant.length; i++) {
            cant[i] = "0";
        }
        return cant;
    }
    
    public int getMaxUnidad() {
        return getVehiculos().get(getSize()-1).getVehiculo();
    }
    
    public boolean deleteVehiculo(String placa) {
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equals(placa)) {
                if (db.deleteVehiculo(placa)) {
                    vehiculos.remove(v);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean updateVehiculo(Vehiculo vehi, String placa) {
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equals(placa)) {
               if (db.updateVehiculo(vehi, placa)) {
                v.setPlaca(vehi.getPlaca());
                v.setCodesTaxy(vehi.getCodesTaxy());
                v.setConductor(vehi.getConductor());
                v.setPropietario(vehi.getPropietario());
                v.setModelo(vehi.getModelo());
                v.setNumChasis(vehi.getNumChasis());
                v.setNumMotor(vehi.getNumMotor());
                v.setRegMunicipal(vehi.getRegMunicipal());
                v.setSoat(vehi.getSoat());
                v.setVehiculo(vehi.getVehiculo());
                v.setYear(vehi.getYear());
                v.setZona(vehi.getZona());
                return true;
                }
            }
        }
        return false;
    }

    /**
     * @return the vehiculos
     */
    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }
    
    public String getPlacaVeh(int vehiculo) {
        for (Vehiculo v : getVehiculos()) {
            if (v.getVehiculo() == vehiculo) {
                return v.getPlaca();
            }
        }
        return null;
    }
    
    public void setCodeTaxyByEtiqueta(int vehiculo, CodesTaxy ct) {
        for (Vehiculo v : getVehiculos()) {
            if (v.getVehiculo() == vehiculo) {
                v.setCodesTaxy(ct);
            }
        }
    }
    
    public void updateBloqueVehiculos(int vehiculo, boolean state) {
        for (Vehiculo v : getVehiculos()) {
            if (v.getVehiculo() == vehiculo) {
                v.setBloqueo(state);
            }
        }
    }
    
    public boolean getBloqueoVehiculo(int vehiculo) {
        for (Vehiculo v : getVehiculos()) {
            if (v.getVehiculo() == vehiculo) {
                return v.isBloqueo();
            }
        }
        return false;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.database;

import com.kradac.despachos.administration.Client;
import com.kradac.despachos.administration.CodesTaxy;
import com.kradac.despachos.administration.Company;
import com.kradac.despachos.administration.Dispatch;
import com.kradac.despachos.administration.list.ListClients;
import com.kradac.despachos.administration.list.ListCodesTaxy;
import com.kradac.despachos.administration.list.ListDispatch;
import com.kradac.despachos.administration.list.ListMarca;
import com.kradac.despachos.administration.list.ListModelo;
import com.kradac.despachos.administration.list.ListPerson;
import com.kradac.despachos.administration.list.ListRolUser;
import com.kradac.despachos.administration.list.ListStateCivil;
import com.kradac.despachos.administration.list.ListUser;
import com.kradac.despachos.administration.list.ListVehiculos;
import com.kradac.despachos.administration.list.ListZona;
import com.kradac.despachos.administration.Marca;
import com.kradac.despachos.administration.Modelo;
import com.kradac.despachos.administration.Pending;
import com.kradac.despachos.administration.Person;
import com.kradac.despachos.administration.RolUser;
import com.kradac.despachos.administration.StateCivil;
import com.kradac.despachos.administration.User;
import com.kradac.despachos.administration.Vehiculo;
import com.kradac.despachos.administration.Zona;
import com.kradac.despachos.administration.list.ListPending;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Dalton
 */
public class DataBase {

    private Properties arcConfig;
    private Connection conexion;
    private String driver, url, ip, usr, pass;
    private String bd;
    private Statement s;
    private ResultSet rs;

    public DataBase(Properties arcConfig) {
        this.arcConfig = arcConfig;
        this.ip = arcConfig.getProperty("ip_base");
        this.bd = arcConfig.getProperty("base");
        this.usr = arcConfig.getProperty("user");
        this.pass = arcConfig.getProperty("pass");
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://" + ip + "/" + bd;

        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usr, pass);
            s = conexion.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se Puede Ingresar al Servidor de la Base de Datos");
            System.exit(0);
        }
    }

    public void closeConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se Puede Cerrar la Conexion con la Base de Datos");
                //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Company loadCompany() {
        try {
            String sql = "SELECT ID_COMPANY, COMPANY, PHONE, EMAIL, DIRECTION, LATITUD, LONGITUD "
                    + "FROM COMPANY";

            rs = s.executeQuery(sql);

            rs.next();
            Company c = new Company(rs.getString("ID_COMPANY"), rs.getString("COMPANY"), rs.getString("PHONE"), rs.getString("EMAIL"),
                    rs.getString("DIRECTION"), rs.getDouble("LATITUD"), rs.getDouble("LONGITUD"));

            return c;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar Codigos de Taxi de los Vehiculos");
            System.exit(0);
        }
        return null;
    }

    public ListRolUser loadRolUser() {
        ListRolUser listAux = new ListRolUser();
        try {
            String sql = "SELECT ID_ROL_USER, ROL_USER, DESCRIPTION "
                    + "FROM ROL_USER ";

            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addRolUser(new RolUser(rs.getInt("ID_ROL_USER"), rs.getString("ROL_USER"), rs.getString("DESCRIPTION")));
            }
            return listAux;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar Roles de Usuario");
            System.exit(0);
        }
        return null;
    }

    public ListStateCivil loadStateCivil() {
        ListStateCivil listAux = new ListStateCivil();
        try {
            String sql = "SELECT ID_STATE_CIVIL, STATE_CIVIL "
                    + "FROM STATE_CIVIL ";

            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addStateCivil(new StateCivil(rs.getInt("ID_STATE_CIVIL"), rs.getString("STATE_CIVIL")));
            }
            return listAux;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar Estado Civil de las Personas");
            System.exit(0);
        }
        return null;
    }

    public ListPerson loadPersons(ListStateCivil lsc) {
        ListPerson listAux = new ListPerson();
        try {
            String sql = "SELECT ID_PERSON, NAME, LASTNAME, PHONE, EMAIL, DIRECTION, NUM_HOUSE, TYPE_SANGRE, ID_STATE_CIVIL, CONYUGUE, IMAGE "
                    + "FROM PERSONS ";

            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addPerson(new Person(rs.getString("ID_PERSON"), rs.getString("NAME"), rs.getString("LASTNAME"), rs.getString("PHONE"),
                        rs.getString("EMAIL"), rs.getString("DIRECTION"), rs.getInt("NUM_HOUSE"), rs.getString("TYPE_SANGRE"),
                        lsc.getStateCivilById(rs.getInt("ID_STATE_CIVIL")), rs.getString("CONYUGUE"), rs.getString("IMAGE")));
            }
            return listAux;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar las Personas");
        }
        return null;
    }

    public ListClients loadClients() {
        ListClients listAux = new ListClients();
        try {
            String sql = "SELECT NAME, LASTNAME, PHONE, DIRECTION, SECTOR, CODE, NUM_HOUSE, LATITUD, LONGITUD, REFERENCE "
                    + "FROM CLIENTS ORDER BY CODE";

            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addClient(new Client(rs.getString("NAME"), rs.getString("LASTNAME"), rs.getString("PHONE"),
                        rs.getString("DIRECTION"), rs.getString("SECTOR"), rs.getInt("CODE"), rs.getInt("NUM_HOUSE"),
                        rs.getDouble("LATITUD"), rs.getDouble("LONGITUD"), rs.getString("REFERENCE")));
            }
            return listAux;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar los Clientes");
        }
        return null;
    }

    public ListUser loadUser(ListPerson listPerson, ListRolUser listRolUser) {
        ListUser listAux = new ListUser();
        try {
            String sql = "SELECT ID_USER, PASSWORD, ID_ROL_USER, ID_PERSON "
                    + "FROM USERS ";

            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addUser(new User(rs.getString("ID_USER"), rs.getString("PASSWORD"),
                        listRolUser.getRolUser(rs.getInt("ID_ROL_USER")), listPerson.getPersonByCedula(rs.getString("ID_PERSON"))));
            }
            return listAux;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar las Personas");
        }
        return null;
    }

    public ListZona loadZonas() {
        ListZona listAux = new ListZona();
        try {
            String sql = "SELECT ID_ZONA, ZONA "
                    + "FROM ZONAS ";

            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addZona(new Zona(rs.getInt("ID_ZONA"), rs.getString("ZONA")));
            }
            return listAux;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar Zonas de los Vehiculos");
            System.exit(0);
        }
        return null;
    }

    public ListMarca loadMarcas() {
        ListMarca listAux = new ListMarca();
        try {
            String sql = "SELECT ID_MARCA_VEHICULO, MARCA_VEHICULO "
                    + "FROM MARCA_VEHICULO";

            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addMarca(new Marca(rs.getInt("ID_MARCA_VEHICULO"), rs.getString("MARCA_VEHICULO")));
            }
            return listAux;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar Marca de los Vehiculos");
            System.exit(0);
        }
        return null;
    }

    public ListModelo loadModelos(ListMarca listMarcas) {
        ListModelo listAux = new ListModelo();
        try {
            String sql = "SELECT ID_MODELO_VEHICULO, MODELO_VEHICULO "
                    + "FROM MODELO_VEHICULO";

            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addModelo(new Modelo(rs.getInt("ID_MODELO_VEHICULO"), listMarcas.getMarcaById(rs.getInt("ID_MODELO_VEHICULO")), rs.getString("MODELO_VEHICULO")));
            }
            return listAux;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar Modelo de los Vehiculos");
            System.exit(0);
        }
        return null;
    }

    public ListCodesTaxy loadCodesTaxy() {
        ListCodesTaxy listAux = new ListCodesTaxy();
        try {
            String sql = "SELECT ID_CODIGO, ETIQUETA, COLOR "
                    + "FROM CODES_TAXY";

            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addCodesTaxy(new CodesTaxy(rs.getString("ID_CODIGO"), rs.getString("ETIQUETA"), rs.getInt("COLOR")));
            }
            return listAux;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar Codigos de Taxi de los Vehiculos");
            System.exit(0);
        }
        return null;
    }

    public ListVehiculos loadVehiculos(ListPerson listPerson, ListModelo listModelo, ListZona listZona, ListCodesTaxy listCodesTaxy) {
        ListVehiculos listAux = new ListVehiculos();
        try {
            String sql = "SELECT ID_VEHICULO, ID_ZONA, ID_CONDUCTOR, ID_PROPIETARIO, ID_CODIGO, VEHICULO, ID_MODELO_VEHICULO, YEAR, "
                    + "NUM_MOTOR, NUM_CHASIS, REG_MUNICIPAL, SOAT "
                    + "FROM VEHICULOS ORDER BY VEHICULO";
            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addVehiculo(new Vehiculo(rs.getString("ID_VEHICULO"), listZona.getZonaById(rs.getInt("ID_ZONA")),
                        listPerson.getPersonByCedula(rs.getString("ID_CONDUCTOR")), listPerson.getPersonByCedula(rs.getString("ID_PROPIETARIO")),
                        rs.getInt("VEHICULO"), listModelo.getModeloById(rs.getInt("ID_MODELO_VEHICULO")), rs.getInt("YEAR"), rs.getString("NUM_MOTOR"),
                        rs.getString("NUM_CHASIS"), rs.getInt("REG_MUNICIPAL"), rs.getString("SOAT"), listCodesTaxy.getCodesTaxyById(rs.getString("ID_CODIGO"))
                ));
            }
            return listAux;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null, "Problemas al Cargar los Vehiculos");
        }
        return null;
    }

    public ListDispatch loadDispatch() {
        ListDispatch listAux = new ListDispatch();
        try {
            String sql = "SELECT TIME, PHONE, CODE, CLIENT, SECTOR, DIRECTION, VEHICULO, "
                    + "MINUTE, TIME_ASIG, ID_VEHICULO, ATRASO, NOTE "
                    + "FROM ASSIGS ORDER BY DATE, TIME";
            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addDispatch(new Dispatch(rs.getString("TIME"), rs.getString("PHONE"),
                        rs.getInt("CODE"), rs.getString("CLIENT"),
                        rs.getString("SECTOR"), rs.getString("DIRECTION"), rs.getInt("VEHICULO"), rs.getInt("MINUTE"),
                        rs.getString("TIME_ASIG"), rs.getString("ID_VEHICULO"), rs.getInt("ATRASO"), rs.getString("NOTE")
                ));
            }
            return listAux;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar los Despachos Realizados");
        }
        return null;
    }

    public ListPending loadPending() {
        ListPending listAux = new ListPending();
        try {
            String sql = "SELECT CODE, PHONE, CLIENT, DATE, TIME, NOTE, REMEMBER "
                    + "FROM PENDING ";

            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addPending(new Pending(rs.getInt("CODE"), rs.getString("PHONE"), rs.getString("CLIENT"),
                        rs.getString("DATE"), rs.getString("TIME"), rs.getString("NOTE"), rs.getString("REMEMBER")));
            }
            return listAux;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Problemas al Cargar Despachos Pendientes");
            System.exit(0);
        }
        return null;
    }

    /* INSERT IN THE DATABASE */
    public void insertClientMap(Client c) {
        try {
            String sql = "INSERT INTO POSITIONS_CLIENTS (CODE, CLIENT, SECTOR, PHONE, LATITUD, LONGITUD, DATE, TIME) "
                    + "VALUES(" + c.getCode() + ",'" + c.getName() + " " + c.getLastname() + "','" + c.getSector() + "','" + c.getPhone() + "',"
                    + c.getLatitud() + "," + c.getLongitud() + ",DATE(NOW()), TIME(NOW()))";
            System.out.println(sql);
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* UPDATE IN THE DATABASE */
    
    
    
    /* DELETE IN THE DATABASE */
}

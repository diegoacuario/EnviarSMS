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

    public DataBase() {
    }
    
    public DataBase(Properties arcConfig, int numHost) {
        this.arcConfig = arcConfig;
        String [] parts = this.arcConfig.getProperty("ip_base"+numHost).split(";");
        this.ip = parts[0];
        this.bd = this.arcConfig.getProperty("base");
        this.usr = this.arcConfig.getProperty("user");
        this.pass = this.arcConfig.getProperty("pass");
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

    public boolean isConectionDb(Properties arcConfig, int numHost) {
        this.arcConfig = arcConfig;
        String [] parts = this.arcConfig.getProperty("ip_base"+numHost).split(";");
        this.ip = parts[0];
        this.bd = this.arcConfig.getProperty("base");
        this.usr = this.arcConfig.getProperty("user");
        this.pass = this.arcConfig.getProperty("pass");
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://" + ip + "/" + bd;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usr, pass);
            s = conexion.createStatement();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se Puede Ingresar al Servidor de la Base de Datos: "+parts[0]);
            return false;
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
                    + "FROM company";

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
                    + "FROM rol_user ";

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
                    + "FROM state_civil ";

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

    public ListPerson loadPersons(ListStateCivil lsc, Properties p, int numHost) {
        ListPerson listAux = new ListPerson(p, numHost);
        try {
            String sql = "SELECT ID_PERSON, NAME, LASTNAME, PHONE, EMAIL, DIRECTION, NUM_HOUSE, TYPE_SANGRE, ID_STATE_CIVIL, CONYUGUE, IMAGE "
                    + "FROM persons ";

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
                    + "FROM clients ORDER BY CODE";

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

    public ListUser loadUser(ListPerson listPerson, ListRolUser listRolUser, Properties p, int numHost) {
        ListUser listAux = new ListUser(p, numHost);
        try {
            String sql = "SELECT ID_USER, PASSWORD, ID_ROL_USER, ID_PERSON "
                    + "FROM users ";

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
                    + "FROM zonas ";

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
                    + "FROM marca_vehiculo";

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
                    + "FROM modelo_vehiculo";

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
                    + "FROM codes_taxy";

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
                    + "NUM_MOTOR, NUM_CHASIS, REG_MUNICIPAL, SOAT ,IMAGE "
                    + "FROM vehiculos ORDER BY VEHICULO";
            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addVehiculo(new Vehiculo(rs.getString("ID_VEHICULO"), listZona.getZonaById(rs.getInt("ID_ZONA")),
                        listPerson.getPersonByCedula(rs.getString("ID_CONDUCTOR")), listPerson.getPersonByCedula(rs.getString("ID_PROPIETARIO")),
                        rs.getInt("VEHICULO"), listModelo.getModeloById(rs.getInt("ID_MODELO_VEHICULO")), rs.getInt("YEAR"), rs.getString("NUM_MOTOR"),
                        rs.getString("NUM_CHASIS"), rs.getInt("REG_MUNICIPAL"), rs.getString("SOAT"), listCodesTaxy.getCodesTaxyById(rs.getString("ID_CODIGO")), rs.getString("IMAGE"))
                );
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
            String sql = "SELECT DATE, TIME, PHONE, CODE, CLIENT, SECTOR, DIRECTION, VEHICULO, "
                    + "MINUTE, TIME_ASIG, ID_VEHICULO, ATRASO, NOTE, REFERENCE, NUM_HOUSE, DESTINO "
                    + "FROM assigs "
                    + "WHERE DATE BETWEEN (SELECT DATE(SUBDATE(NOW(), INTERVAL 15 DAY))) AND DATE(NOW()) "
                    + "ORDER BY DATE, TIME";
            rs = s.executeQuery(sql);

            while (rs.next()) {
                listAux.addDispatch(new Dispatch(rs.getString("DATE"), rs.getString("TIME"), rs.getString("PHONE"),
                        rs.getInt("CODE"), rs.getString("CLIENT"),
                        rs.getString("SECTOR"), rs.getString("DIRECTION"), rs.getInt("VEHICULO"), rs.getInt("MINUTE"),
                        rs.getString("TIME_ASIG"), rs.getString("ID_VEHICULO"), rs.getInt("ATRASO"), rs.getString("NOTE"),
                        rs.getString("REFERENCE"), rs.getInt("NUM_HOUSE"), rs.getString("DESTINO")
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
                    + "FROM pending ";

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
            String sql = "CALL SP_CLIENTS_MAP(" + c.getCode() + ",'" + c.getName() + " " + c.getLastname() + "','" + c.getSector() + "','" + c.getPhone() + "',"
                    + c.getLatitud() + "," + c.getLongitud() + ")";
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            //Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean insertPendign(Pending p) {
        try {
            String sql = "INSERT INTO pending (CODE, PHONE, CLIENT, DATE, TIME, REMEMBER, NOTE) "
                    + "VALUES(" + p.getCode() + ",'"+ p.getPhone()+"','" + p.getClient() + "','" + p.getDate() + "','" + p.getTime() + "','"
                    + p.getRemember() + "','" + p.getNote() + "')";
            s.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param p Persona insertar en la base
     * @return si se inserto o no, esto lo hace despues de comprobar que la
     * persona no existe es decir siempre que llegue aqui debe insertar
     */
    public boolean insertPerson(Person p) {
        boolean inserto = true;
        try {
            String sql = "INSERT INTO persons (id_person,name,lastname,phone,"
                    + "email,direction,num_house,type_sangre,id_state_civil,"
                    + "conyugue,image) VALUES('" + p.getCedula() + "','"
                    + p.getName() + "','" + p.getLastname() + "','" + p.getPhone()
                    + "','" + p.getEmail() + "','" + p.getDirection() + "',"
                    + p.getNumHouse() + ",'" + p.getTypeSangre() + "','"
                    + p.getStateCivil().getIdStateCivil() + "','" + p.getConyuge()
                    + "','" + p.getImage() + "')";
            
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            inserto = false;
        }
        return inserto;
    }

    public boolean insertUser(User u) {
        boolean inserto = true;
        try {
            String sql = "INSERT INTO users (id_user,id_rol_user,id_person,"
                    + "password) VALUES('" + u.getUser() + "','"
                    + u.getRolUser().getIdRolUser() + "','"
                    + u.getPerson().getCedula() + "','" + u.getPassword() + "')";
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            inserto = false;
        }
        return inserto;
    }
    
    public boolean insertVehiculo(Vehiculo v) {
        boolean inserto = true;
        try {
            String sql = "INSERT INTO vehiculos (id_vehiculo,id_zona,id_conductor,"
                    + "id_propietario,id_codigo,vehiculo,id_modelo_vehiculo,"
                    + "year,num_motor,num_chasis,reg_municipal,soat,image) "
                    + "VALUES('" + v.getPlaca() + "',"
                    + v.getZona().getIdZona() + ","
                    + "'" + v.getConductor().getCedula() + "',"
                    + "'" + v.getPropietario().getCedula() + "',"
                    + "'AC',"
                    + v.getVehiculo() + ","
                    + v.getModelo().getIdModeloVehiculo() + ","
                    + v.getYear() + ","
                    + "'" + v.getNumMotor() + "',"
                    + "'" + v.getNumChasis() + "',"
                    + v.getRegMunicipal() + ","
                    + "'" + v.getSoat() + "',"
                    + "'')";
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            inserto = false;
        }
        return inserto;
    }

    public boolean insertClient(Client c) {
        boolean inserto = true;

        try {
            String sql = "INSERT INTO clients (name,lastname,phone,"
                    + "direction,sector,code,num_house,latitud,longitud,"
                    + "reference) VALUES('" + c.getName() + "',"
                    + "'" + c.getLastname() + "',"
                    + "'" + c.getPhone() + "',"
                    + "'" + c.getDirection() + "',"
                    + "'" + c.getSector() + "',"
                    + c.getCode() + ","
                    + c.getNumHouse() + ","
                    + c.getLatitud() + ","
                    + c.getLongitud() + ","
                    + "'" + c.getReference() + "')";
            
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            inserto = false;
        }
        return inserto;
    }

    public boolean insertLifeAssigns(String id_user, String time, int code,
            String phone, String id_codigo, int respaldo, String client,
            String sector, String direction, String destino, int vehiculo, int minute,
            String id_vehiculo, int atraso, String reference, String note,
            int tiquete, double latitud, double longitud) {
        boolean inserto = true;
        String sql = "";
        try {
            sql = "INSERT INTO life_assigns (id_user,date,time,code,"
                    + "phone,id_codigo,respaldo,client,sector,direction,"
                    + "destino,vehiculo,minute,id_vehiculo,atraso,reference,"
                    + "note,tiquete,latitud,longitud) VALUES("
                    + "'" + id_user + "',Date(Now()),"
                    + "'" + time + "'," + code + ","
                    + "'" + phone + "','" + id_codigo + "',"
                    + respaldo + ",'" + client + "',"
                    + "'" + sector + "','" + direction + "',"
                    + "'" + destino + "','" + vehiculo + "',"
                    + "'" + minute + "','" + id_vehiculo + "',"
                    + atraso + "," + "'" + reference + "',"
                    + "'" + note + "'," + tiquete + ","
                    + latitud + "," + longitud + ")";
            
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            inserto = false;
        }
        return inserto;
    }

    public boolean insertAssigns(String id_user, String time, int code,
            String phone, String client,
            String sector, String direction, String destino, int vehiculo, int minute, String time_asig,
            String id_vehiculo, int atraso, String reference, String note,
            int tiquete, double latitud, double longitud) {
        boolean inserto = true;
        String sql = "";
        try {
            sql = "INSERT INTO assigs (id_user,date,time,phone,code,client,"
                    + "sector,direction,destino,vehiculo,minute,time_asig,"
                    + "id_vehiculo,atraso,reference,note,tiquete,latitud,longitud) VALUES("
                    + "'" + id_user + "',Date(Now()),"
                    + "'" + time + "'," + phone + ","
                    + "'" + code + "','" + client + "',"
                    + "'" + sector + "','" + direction + "',"
                    + "'" + destino + "'," + vehiculo + ","
                    + "'" + minute + "','" + time_asig + "','" + id_vehiculo + "',"
                    + atraso + "," + "'" + reference + "',"
                    + "'" + note + "'," + tiquete + ","+latitud+","+longitud+")";
            
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            inserto = false;
        }
        return inserto;
    }
    
    public void insertLastGps(String unidad, String latitud, String longitud,
            String fecha, String hora, String vel, String G1, String G2, String activo) {
        try {
            String sql = "CALL SP_INSERT_LAST_GPS("
                    + Integer.parseInt(unidad) + ","
                    + Double.parseDouble(latitud) + ","
                    + Double.parseDouble(longitud) + ",'"
                    + fecha + "','"
                    + hora + "',"
                    + Double.parseDouble(vel) + ","
                    + G1 + ","
                    + G2 + ","
                    + Integer.parseInt(activo)
                    + ")";
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* UPDATE IN THE DATABASE */
    public boolean updatePerson(Person p, String cedula) {
        boolean actualizo = true;
        try {
            String sql = "UPDATE persons  SET id_person='" + p.getCedula() + "',"
                    + "name='" + p.getName() + "',"
                    + "lastname='" + p.getLastname() + "',"
                    + "phone='" + p.getPhone() + "',"
                    + "email='" + p.getEmail() + "',"
                    + "direction='" + p.getDirection() + "',"
                    + "num_house=" + p.getNumHouse() + ","
                    + "type_sangre='" + p.getTypeSangre() + "',"
                    + "id_state_civil=" + p.getStateCivil().getIdStateCivil() + ","
                    + "conyugue='" + p.getConyuge() + "',"
                    + "image='" + p.getImage() + "' WHERE id_person='"
                    + cedula + "'";
            
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            actualizo = false;
        }
        return actualizo;
    }

    public boolean updateUser(User user, String id_user) {
        boolean actualizo = true;
        try {
            String sql = "UPDATE users  SET id_user='" + user.getUser() + "',"
                    + "id_rol_user=" + user.getRolUser().getIdRolUser() + ","
                    + "id_person='" + user.getPerson().getCedula() + "',"
                    + "password='" + user.getPassword() + "' WHERE id_user='"
                    + id_user + "'";
            
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            actualizo = false;
        }
        return actualizo;
    }

    public boolean updateClient(Client client, int code) {
        boolean actualizo = true;
        try {
            String sql = "UPDATE clients  SET name='" + client.getName() + "',"
                    + "lastname='" + client.getLastname() + "',"
                    + "phone='" + client.getPhone() + "',"
                    + "direction='" + client.getDirection() + "',"
                    + "sector='" + client.getSector() + "',"
                    + "code=" + client.getCode() + ","
                    + "num_house=" + client.getNumHouse() + ","
                    + "latitud=" + client.getLatitud() + ","
                    + "longitud=" + client.getLongitud() + ","
                    + "reference='" + client.getReference() + "' WHERE code="
                    + client.getCode();
            
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            actualizo = false;
        }
        return actualizo;
    }
    
    public boolean updateVehiculo(Vehiculo v, String placa) {
        boolean actualizo = true;
        try {
            String sql = "UPDATE users  SET id_vehiculo='" + v.getPlaca() + "',"
                    + "id_zona=" + v.getZona().getIdZona() + ","
                    + "id_conductor='" + v.getConductor().getCedula() + "',"
                    + "id_propietario='" + v.getPropietario().getCedula() + "',"
                    + "id_codigo=" + v.getCodesTaxy().getIdCodigo() + ","
                    + "vehiculo=" + v.getVehiculo() + ","
                    + "id_modelo_vehiculo=" + v.getModelo().getIdModeloVehiculo() + ","
                    + "year='" + v.getYear() + "',"
                    + "num_motor='" + v.getNumMotor() + "',"
                    + "num_chasis='" + v.getNumChasis() + "',"
                    + "reg_municipal=" + v.getRegMunicipal() + ","
                    + "soat='" + v.getSoat() + "',"
                    + "image='" + v.getImage() + "' WHERE id_vehiculo='"
                    + placa + "'";

            System.out.println(sql);
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            System.err.println(ex);
            actualizo = false;
        }
        return actualizo;
    }

    /* DELETE IN THE DATABASE */
    public void deleteClientMap(int code) {
        try {
            String sql = "DELETE FROM positions_clients WHERE code = "+code;
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAllClientMap() {
        try {
            String sql = "DELETE FROM positions_clients";
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean deletePerson(String cedula) {
        boolean elimino = true;
        try {
            String sql = "DELETE FROM persons WHERE id_person='" + cedula + "'";
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            elimino = false;
        }
        return elimino;
    }

    public boolean deleteUser(String user) {
        boolean elimino = true;
        try {
            String sql = "DELETE FROM users WHERE id_user='" + user + "'";
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            elimino = false;
        }
        return elimino;
    }
    
    public boolean deletePending(String date, String time) {
        try {
            String sql = "DELETE FROM pending WHERE date = '"+date+"' AND time = '"+time+"'";
            s.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteVehiculo(String placa) {
        boolean elimino = true;
        try {
            String sql = "DELETE FROM vehiculos WHERE id_vehiculo='" + placa + "'";
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            elimino = false;
        }
        return elimino;
    }
}

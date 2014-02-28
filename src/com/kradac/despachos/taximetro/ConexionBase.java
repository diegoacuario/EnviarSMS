package com.kradac.despachos.taximetro;

import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.interfaz.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ConexionBase {

    private Connection conexion;
    private PreparedStatement PRTaxim;
    private ResultSet r;
    private Statement st;

    public ConexionBase() {
        DataBase db = new DataBase(Principal.fileConfig, Principal.numHost);
        this.conexion = db.getConexion();
    }

    public boolean CerrarConexion() {
        try {
            if (PRTaxim != null) {
                PRTaxim.close();
            }
            if (this.conexion != null) {
                this.conexion.close();
            }
        } catch (SQLException ex) {
            return false;
        }
        this.conexion = null;
        return true;
    }

    public boolean insertDataTaximetro(String serial, String tiquete,
            String fecha, String hora_inicial, String hora_final,
            double distancia, double costo, double paga, String valido,
            String tarifa, double banderaso, int tiempo_seg) {
        boolean res;
        try {

            if (PRTaxim == null) {
                try {
                    String insert = "INSERT INTO taximetro_data (serial, "
                            + "tiquete, fecha, hora_inicial, hora_final, distancia,"
                            + " costo, paga, valido, tarifa, banderaso, tiempo_seg)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                    PRTaxim = conexion.prepareStatement(insert);
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }

            PRTaxim.setString(1, serial);
            PRTaxim.setString(2, tiquete);
            PRTaxim.setString(3, fecha);
            PRTaxim.setString(4, hora_inicial);
            PRTaxim.setString(5, hora_final);
            PRTaxim.setDouble(6, distancia);
            PRTaxim.setDouble(7, costo);
            PRTaxim.setDouble(8, paga);
            PRTaxim.setString(9, valido);
            PRTaxim.setString(10, tarifa);
            PRTaxim.setDouble(11, banderaso);
            PRTaxim.setInt(12, tiempo_seg);
            PRTaxim.executeUpdate();
            res = true;

        } catch (SQLException ex) {
            System.out.println(ex);
            res = false;
        }
        return res;
    }

    public int[] insertaVectorBase(List<DatoTaximetro> d) {
        int res[] = new int[3];
        int noIns = 0, inser = 0, dupli = 0;
        DatoTaximetro dato;
        boolean v;
        for (int i = 0; i < d.size(); i++) {
            dato = d.get(i);
            v = verificarDatoExiste(dato);
            if (!v) {
                boolean in = insertDataTaximetro(dato.getSerial(),
                        dato.getTiquete(), dato.getFecha(),
                        dato.getHora_inicial(), dato.getHora_final(),
                        dato.getDistancia(), dato.getCosto(),
                        dato.getPaga(), dato.getValido(),
                        dato.getTarifa(), dato.getBanderaso(),
                        dato.getTiempo_seg());
                if (!in) {
                    inser++;
                } else {
                    noIns++;
                }
            } else {
                dupli++;
            }
        }
        res[0] = noIns;
        res[1] = inser;
        res[2] = dupli;
        return res;
    }

    public boolean verificarDatoExiste(DatoTaximetro dato) {
        boolean res = false;

        String serial = dato.getSerial();
        String tiquet = dato.getTiquete();
        //System.out.println(tiquet);
        int c = 0;
        try {
            String consulta = "SELECT COUNT(*) FROM taximetro_data where serial = '" + serial + "' and tiquete ='" + tiquet + "'";
            st = conexion.createStatement();
            r = st.executeQuery(consulta);
            r.next();
            c = Integer.parseInt(r.getString("COUNT(*)"));
            //    System.out.println(c);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        if (c >= 1) {
            res = true;
        }
        return res;
    }
}

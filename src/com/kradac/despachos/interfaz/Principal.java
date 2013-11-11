/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.interfaz;

import com.kradac.despachos.interfaz.requerid.Time;
import com.kradac.despachos.interfaz.requerid.ColorComboBoxEditor;
import com.kradac.despachos.interfaz.requerid.FormatTable;
import com.kradac.despachos.interfaz.requerid.ColorCellRenderer;
import com.kradac.despachos.administration.Client;
import com.kradac.despachos.administration.CodesTaxy;
import com.kradac.despachos.administration.ByDispatch;
import com.kradac.despachos.administration.Call;
import com.kradac.despachos.administration.Company;
import com.kradac.despachos.administration.Dispatch;
import com.kradac.despachos.administration.MessageToAndroid;
import com.kradac.despachos.administration.MessageToGarmin;
import com.kradac.despachos.administration.list.ListClients;
import com.kradac.despachos.administration.list.ListCodesTaxy;
import com.kradac.despachos.administration.list.ListByDispatch;
import com.kradac.despachos.administration.list.ListDispatch;
import com.kradac.despachos.administration.list.ListPerson;
import com.kradac.despachos.administration.list.ListRolUser;
import com.kradac.despachos.administration.list.ListStateCivil;
import com.kradac.despachos.administration.list.ListUser;
import com.kradac.despachos.administration.list.ListVehiculos;
import com.kradac.despachos.administration.User;
import com.kradac.despachos.administration.Vehiculo;
import com.kradac.despachos.administration.list.ListCall;
import com.kradac.despachos.administration.list.ListJob;
import com.kradac.despachos.administration.list.ListMessageToServer;
import com.kradac.despachos.administration.list.ListModelo;
import com.kradac.despachos.administration.list.ListPending;
import com.kradac.despachos.administration.list.ListTurn;
import com.kradac.despachos.administration.list.ListZona;
import com.kradac.despachos.comm.central.CentralTelefonica;
import com.kradac.despachos.conections.ChannelLastGps;
import com.kradac.despachos.conections.ChannelMessageFromServer;
import com.kradac.despachos.conections.ChannelMessageToServer;
import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.methods.Functions;
import com.kradac.despachos.threads.ThreadBloqueos;
import com.kradac.despachos.threads.ThreadCalls;
import com.kradac.despachos.threads.ThreadClient;
import com.kradac.despachos.threads.ThreadDispatch;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Dalton
 */
public class Principal extends javax.swing.JFrame {

    public static ListPerson listPerson;
    public static ListUser listUser;
    public static ListClients listClient;
    public static ListVehiculos listVehiculos;
    public static ListCodesTaxy listCodesTaxy;
    public static ListModelo listModels;
    public static ListZona listZonas;
    public static ListStateCivil listStateCivil;
    public static ListJob listJob;
    public static ListTurn listTurn;
    public static ListByDispatch listByDispatch = new ListByDispatch();
    public static ListDispatch listDispatch = new ListDispatch();
    public static ListRolUser listRolUser = new ListRolUser();
    public static ListPending listPending = new ListPending();
    public static ListCall listCall = new ListCall();
    public static ListMessageToServer listMessageToServer = new ListMessageToServer();
    public static DataBase bd;
    public static Properties fileConfig;
    public static User userLogin;
    public static Company company;
    public static int numHost;
    public static int numDispatchs;
    public static boolean isByTurn;
    public static boolean isConextion;
    public static DefaultTableModel modelTableByDispatch;
    public static DefaultTableModel modelTableDispatch;
    public static DefaultTableModel modelTableDispatch2;
    public static DefaultTableModel modelTableCall;
    public static DefaultTableModel modelTableTrash;
    public static DefaultListModel modelListEvents;
    public static ArrayList<String> codeStateVeh;
    public static ArrayList<String> etiquetaStateVeh;
    public static ArrayList<Integer> colorStateVeh;

    private Menu menu;
    private FrameClients fc;
    private FramePending fp;
    private SearchClients sc;

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        configStart();
    }

    public Principal(ListPerson lp, DataBase b, Properties p, User u, ListStateCivil lsc,
            ListRolUser lru, ListUser lu, ListJob lj, ListTurn lt, int h) {
        listPerson = lp;
        bd = b;
        fileConfig = p;
        userLogin = u;
        listStateCivil = lsc;
        listRolUser = lru;
        listUser = lu;
        listJob = lj;
        listTurn = lt;
        numHost = h;
    }

    public void configStart() {
        company = bd.loadCompany();
        this.setTitle("Despachos KRADAC - " + company.getCompany() + " || " + userLogin.getUser());
        listPending = bd.loadPending();
        listCodesTaxy = bd.loadCodesTaxy();
        listModels = bd.loadModelos(bd.loadMarcas());
        listZonas = bd.loadZonas();
        listVehiculos = bd.loadVehiculos(listPerson, listModels, listZonas, listCodesTaxy);
        listDispatch = bd.loadDispatch();
        numDispatchs = listDispatch.getDispatchs().size();

        modelTableByDispatch = (DefaultTableModel) tblByDispatch.getModel();
        modelTableDispatch = (DefaultTableModel) tblDispatchs.getModel();
        modelTableDispatch2 = (DefaultTableModel) tblDispatchs2.getModel();
        modelTableCall = (DefaultTableModel) tblCall.getModel();
        modelTableTrash = (DefaultTableModel) tblTrash.getModel();
        modelListEvents = new DefaultListModel();
        ltEvents.setModel(modelListEvents);

        listDispatch.loadDispatch();
        Time t = new Time();
        t.start(lblDate, lblTime);

        ThreadClient tc = new ThreadClient();
        tc.start();

        ThreadBloqueos tb = new ThreadBloqueos();
        tb.start();

        ChannelMessageFromServer cmfs = new ChannelMessageFromServer();
        cmfs.start();

        ChannelLastGps cos = new ChannelLastGps();
        cos.start();

        ChannelMessageToServer cmts = new ChannelMessageToServer();
        cmts.start();

        CentralTelefonica ct = new CentralTelefonica();
        ct.start();

        setLocationRelativeTo(null);
        codeStateVeh = listCodesTaxy.getIdCodigo();
        etiquetaStateVeh = listCodesTaxy.getEtiqueta();
        colorStateVeh = listCodesTaxy.getColor();
        redimencionarTableVehiculos();
        loadComboStates();
        keyPressed();
        showDispatch();
        
        bd.loadTrash();

        /*int host = Integer.parseInt(fileConfig.getProperty("host"));
         String [] parts = fileConfig.getProperty("ip_base"+host).split(";");
         String server = parts[1];
         if (server.equals("C")) {*/
        ThreadDispatch th = new ThreadDispatch();
        th.start();

        ThreadCalls tcll = new ThreadCalls();
        tcll.start();
        //}

        jpSlope.setVisible(false);
        lblSlope.setVisible(false);
        btnImport.setVisible(false);
        btnHistorical.setVisible(false);
    }

    public void keyPressed() {
        /**
         * Despachar con la tecla F12
         */
        tblByDispatch.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "Despachar");
        tblByDispatch.getActionMap().put("Despachar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Metodo generico para despachar se lo utiliza en todos los
                 * lugares donde se despacha carreras
                 */
                dispatch();
            }
        });

        tblByDispatch.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), "EliminarFila");
        tblByDispatch.getActionMap().put("EliminarFila", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Metodo generico para borrar las filas de una manera segura
                 * llamarlo desde todos los lugares donde se hagan borrado de
                 * filas
                 */
                deleteRowByDispatch();
            }
        });
    }

    public static void redimencionarTableVehiculos() {
        TableModel tm = new AbstractTableModel() {
            String headers[] = listVehiculos.getEncabezadosTablaVehiculosArrayString();
            String rows[] = listVehiculos.getFilasNumeroDespachos();

            @Override
            public int getColumnCount() {
                return headers.length;
            }

            @Override
            public int getRowCount() {
                return 1;
            }

            @Override
            public String getColumnName(int col) {
                return headers[col];
            }

            /**
             * Trae el valor actual
             */
            @Override
            public Object getValueAt(int row, int col) {
                return rows[col];
            }

            @Override
            public void setValueAt(Object a, int row, int col) {
                rows[col] = a.toString();
                fireTableDataChanged();
            }
        };

        tblStateVeh.setModel(tm);

        //Ajustar el ancho de las columnas de la tabla de vehiculos
        String[] strEncColum = listVehiculos.getEncabezadosTablaVehiculosArrayString();

        int unidad = listVehiculos.getMaxUnidad();

        for (String col : strEncColum) {
            TableColumn tc = tblStateVeh.getColumn(col);

            if (unidad <= 99) {
                tc.setMaxWidth(30);
                tblStateVeh.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            } else if (unidad <= 999) {
                tc.setMaxWidth(32);
                tblStateVeh.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            } else if (unidad <= 9999) {
                tc.setMaxWidth(39);
                tblStateVeh.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            }
        }

        paintStateTaxy(listVehiculos.getEncabezadosTablaVehiculosArrayList());
    }

    public static void showDispatch() {
        lblDispatchG.setText("" + listDispatch.getDispatchsGeneral());
        lblDispatchD.setText("" + listDispatch.getDispatchsByDay());
        lblDispatchT.setText("" + listDispatch.getDispatchByTurn());
    }

    public static void paintStateTaxy(ArrayList encab) {
        DataBase newdb = new DataBase(fileConfig, numHost);
        //Clave valor
        //[n_unidad]==>[id_codigo]
        Map unidadCodigoBD = new HashMap();
        for (Vehiculo v : listVehiculos.getVehiculos()) {
            unidadCodigoBD.put(v.getVehiculo(), v.getCodesTaxy().getIdCodigo());
            newdb.updateStateVehiculo(v.getVehiculo(), v.getCodesTaxy().getIdCodigo());
            setNumeroCarrerasRealizadasPorTaxi(v.getVehiculo());
        }
        newdb.closeConexion();

        try {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            tcr.setVerticalAlignment(SwingConstants.CENTER);

            for (int i = 0, len = tblStateVeh.getColumnCount(); i < len; i++) {
                TableColumn column = tblStateVeh.getColumn(tblStateVeh.getColumnName(i));
                column.setCellRenderer(new FormatTable(encab, unidadCodigoBD, codeStateVeh, colorStateVeh));
            }
            tblStateVeh.repaint();
        } catch (NullPointerException ex) {
            System.err.println("Null al pintar estado del Taxi");
        }
    }

    public static void loadComboStates() {
        cbxStateVeh.setModel(new javax.swing.DefaultComboBoxModel(etiquetaStateVeh.toArray()));
        cbxStateVeh.setEditable(true);
        cbxStateVeh.setRenderer(new ColorCellRenderer(codeStateVeh, colorStateVeh));
        ColorComboBoxEditor editor = new ColorComboBoxEditor(listCodesTaxy.getMapEtiqColor());
        cbxStateVeh.setEditor(editor);
    }

    public static String[] changeToArrayClient(Client client) {
        if (client.getCode() == 0) {
            client.setDirection("");
            client.setName("");
            client.setLastname("");
            client.setSector("");
        }
        String[] dataPerson = {
            Functions.getTime(),
            client.getPhone(),
            "" + client.getCode(),
            client.getName() + " " + client.getLastname(),
            client.getSector(),
            client.getDirection(),
            "",
            "",
            "", "", "", ""
        };
        return dataPerson;
    }

    public String[] addNewClient(String phone) {
        String[] dataClient = {Functions.getTime(), phone, "0", "", "", "", "", "", "", "", "", ""};
        return dataClient;
    }

    public String[] changeToArrayDispatch(ByDispatch dispatch) {
        String[] dataDispatch = {
            dispatch.getTime(),
            dispatch.getPhone(),
            "" + dispatch.getCode(),
            dispatch.getClient(),
            dispatch.getSector(),
            dispatch.getDirection(),
            "" + dispatch.getVehiculo(),
            "" + dispatch.getMinute(),
            dispatch.getTimeAsig(),
            dispatch.getPlaca(),
            "" + dispatch.getAtraso(),
            dispatch.getNote()
        };
        return dataDispatch;
    }

    public String[] changeToArrayTrash(ByDispatch bd) {
        String[] dataTrash = {
            bd.getTime(),
            bd.getPhone(),
            bd.getCode()+"",
            bd.getClient(),
            bd.getSector(),
            bd.getDirection()
        };
        return dataTrash;
    }

    public void dispatch() {
        int rowSelected = tblByDispatch.getSelectedRow();
        if (rowSelected != -1) {
            String timeLastDipatch, dateLastDispatch;
            try {
                timeLastDipatch = listByDispatch.getLastDispatch().getTime();
                dateLastDispatch = listByDispatch.getLastDispatch().getDate();
            } catch (NullPointerException e) {
                timeLastDipatch = Functions.getTime();
                dateLastDispatch = Functions.getDate();
            }

            if (Functions.getTime().compareTo(timeLastDipatch) < 0 && Functions.getDate().compareTo(dateLastDispatch) < 0) {
                JOptionPane.showMessageDialog(this, "Iguale la Hora de Su Reloj Para Realizar Despachos", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                if (!tblByDispatch.getValueAt(rowSelected, 3).toString().equals("")) {
                    try {
                        int vehiculo = Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 6).toString());
                        int code = Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 2).toString());
                        Client c = Principal.listClient.getClientByCode(code);

                        ByDispatch auxByDispatch = new ByDispatch(
                                Functions.getDate(),
                                tblByDispatch.getValueAt(rowSelected, 0).toString(),
                                tblByDispatch.getValueAt(rowSelected, 1).toString(),
                                code,
                                tblByDispatch.getValueAt(rowSelected, 3).toString(),
                                tblByDispatch.getValueAt(rowSelected, 4).toString(),
                                tblByDispatch.getValueAt(rowSelected, 5).toString(),
                                vehiculo,
                                Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 7).toString()),
                                tblByDispatch.getValueAt(rowSelected, 8).toString(),
                                tblByDispatch.getValueAt(rowSelected, 9).toString(),
                                Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 10).toString()),
                                tblByDispatch.getValueAt(rowSelected, 11).toString()
                        );

                        Dispatch auxDispatch = new Dispatch(Functions.getDate(),
                                tblByDispatch.getValueAt(rowSelected, 0).toString(),
                                tblByDispatch.getValueAt(rowSelected, 1).toString(), code,
                                tblByDispatch.getValueAt(rowSelected, 3).toString(),
                                tblByDispatch.getValueAt(rowSelected, 4).toString(),
                                tblByDispatch.getValueAt(rowSelected, 5).toString(),
                                vehiculo, Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 7).toString()),
                                tblByDispatch.getValueAt(rowSelected, 8).toString(),
                                tblByDispatch.getValueAt(rowSelected, 9).toString(),
                                Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 10).toString()),
                                tblByDispatch.getValueAt(rowSelected, 11).toString(),
                                c.getReference(), c.getNumHouse(), c.getDestino());

                        listByDispatch.addByDispatch(auxByDispatch);
                        listDispatch.addDispatch(auxDispatch);

                        numDispatchs++;

                        bd.insertLifeAssigns(
                                userLogin.getUser(),//user
                                auxByDispatch.getTime(),//hora
                                auxByDispatch.getCode(),//code
                                auxByDispatch.getPhone(),//telefono
                                "OCU",
                                0, auxByDispatch.getClient(),//Cliente
                                auxByDispatch.getSector(),//Sector
                                auxByDispatch.getDirection(),//Dirección
                                c.getDestino(),//destino
                                vehiculo,//vehiculo
                                auxByDispatch.getMinute(),//minute
                                auxByDispatch.getPlaca(),//id_vehiculo
                                auxByDispatch.getAtraso(),//atraso
                                c.getReference(),
                                auxByDispatch.getNote(),//NOTA
                                numDispatchs, c.getLatitud(), c.getLongitud()
                        );
                        bd.insertAssigns(
                                userLogin.getUser(),//user
                                auxDispatch.getTime(),//hora
                                code,//code
                                auxDispatch.getPhone(),//telefono                            
                                auxDispatch.getClient(),//Cliente
                                auxDispatch.getSector(),//Sector
                                auxDispatch.getDirection(),//Dirección
                                c.getDestino(),//destino
                                vehiculo,//vehiculo
                                auxDispatch.getMinute(),//minute
                                auxDispatch.getTimeAsig(),//hora asgi
                                auxDispatch.getPlaca(),//id_vehiculo
                                auxDispatch.getAtraso(),//atraso
                                c.getNumHouse(),
                                c.getReference(),
                                auxDispatch.getNote(),//NOTA
                                numDispatchs, c.getLatitud(), c.getLongitud()
                        );

                        modelTableByDispatch.removeRow(rowSelected);
                        modelTableDispatch.insertRow(0, changeToArrayDispatch(auxByDispatch));
                        modelTableDispatch2.insertRow(0, changeToArrayDispatch(auxByDispatch));
                        bd.deleteClientMap(code);

                        listVehiculos.setCodeTaxyByEtiqueta(vehiculo, listCodesTaxy.getCodesTaxyById("OCU"));
                        paintStateTaxy(listVehiculos.getEncabezadosTablaVehiculosArrayList());
                        //redimencionarTableVehiculos();
                        showDispatch();
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Llene los Campos Necesarios para Despachar");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Llene los Datos del Cliente para Despachar");
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "No ha Seleccionado una Fila");
        }
    }

    public void deleteRowByDispatch() {
        int rowSelected = tblByDispatch.getSelectedRow();
        if (rowSelected == -1) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado una Fila.");
        } else {
            String vehiculo = tblByDispatch.getValueAt(rowSelected, 6).toString();
            int code = Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 2).toString());
            if (!vehiculo.equals("")) {
                listVehiculos.setCodeTaxyByEtiqueta(Integer.parseInt(vehiculo), listCodesTaxy.getCodesTaxyById("AC"));
                paintStateTaxy(listVehiculos.getEncabezadosTablaVehiculosArrayList());

                if (bd.insertLifeAssigns(
                        userLogin.getUser(),//user
                        tblByDispatch.getValueAt(rowSelected, 0) + "",//hora
                        Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 2) + ""),//code
                        tblByDispatch.getValueAt(rowSelected, 1) + "",//telefono
                        "AC", 0,
                        "",
                        "",
                        "",
                        "",
                        Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 6) + ""),//vehiculo
                        0,//Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 7)),//minute
                        tblByDispatch.getValueAt(rowSelected, 9) + "",//id_vehiculo
                        0,//Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 10)),//atraso
                        "",
                        "",
                        0,
                        0.0, 0.0
                )) {//si se ingresa en la base se borra en la tabla}
                    modelTableByDispatch.removeRow(rowSelected);
                    bd.deleteClientMap(code);
                    //listByDispatch.deleteByDispatch(code);
                }
            } else {
                ByDispatch bds = new ByDispatch("", tblByDispatch.getValueAt(rowSelected, 0).toString(),
                        tblByDispatch.getValueAt(rowSelected, 1).toString(), code,
                        tblByDispatch.getValueAt(rowSelected, 3).toString(), tblByDispatch.getValueAt(rowSelected, 4).toString(),
                        tblByDispatch.getValueAt(rowSelected, 5).toString(), -1, 0, "", "", 0, "");

                if (bd.insertLifeAssigns(
                        userLogin.getUser(),//user
                        bds.getTime(),//hora
                        code,//code
                        bds.getPhone() + "",//telefono
                        "AC", 0, "", "", "", "", -1,//vehiculo
                        0,//Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 7)),//minute
                        "",//id_vehiculo
                        0,//Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 10)),//atraso
                        "", "", 0, 0.0, 0.0
                )) {//si se ingresa en la base se borra en la tabla}
                    bd.deleteClientMap(code);
                    bd.insertTrashAssign(userLogin.getUser(), bds.getTime() + "",
                            code,//code
                            bds.getPhone(),//telefono
                            bds.getClient(),
                            bds.getSector(),
                            bds.getDirection()
                    );
                    modelTableTrash.insertRow(0, changeToArrayTrash(bds));
                    modelTableByDispatch.removeRow(rowSelected);
                    //listByDispatch.deleteByDispatch(code);
                }
            }
        }
    }

    public static void updateTableByDispatch(Client client, String phoneOld, String note) {
        for (int fila = 0; fila < tblByDispatch.getRowCount(); fila++) {
            for (int columna = 0; columna < tblByDispatch.getColumnCount(); columna++) {
                if (columna == 1) {
                    String phoneRow = modelTableByDispatch.getValueAt(fila, columna).toString();
                    if (phoneRow.equals(phoneOld)) {
                        modelTableByDispatch.setValueAt(client.getPhone(), fila, 1);
                        modelTableByDispatch.setValueAt(client.getCode(), fila, 2);
                        modelTableByDispatch.setValueAt(client.getName() + " " + client.getLastname(), fila, 3);
                        modelTableByDispatch.setValueAt(client.getSector(), fila, 4);
                        modelTableByDispatch.setValueAt(client.getDirection(), fila, 5);
                        modelTableByDispatch.setValueAt(note, fila, 11);
                        bd.insertClientMap(client);
                    }
                }
            }
        }
    }

    public boolean vehiculoExisteTableByDispatch(String veh) {
        int rep = 0;
        for (int fila = 0; fila < tblByDispatch.getRowCount(); fila++) {
            for (int columna = 0; columna < tblByDispatch.getColumnCount(); columna++) {
                if (columna == 6) {
                    String vehiculo = modelTableByDispatch.getValueAt(fila, columna).toString();
                    if (vehiculo.equals(veh)) {
                        rep++;
                        if (rep == 2) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void clearStateVehiculo(String veh) {
        int rep = 0;
        for (int fila = 0; fila < tblByDispatch.getRowCount(); fila++) {
            for (int columna = 0; columna < tblByDispatch.getColumnCount(); columna++) {
                if (columna == 6) {
                    String vehiculo = modelTableByDispatch.getValueAt(fila, columna).toString();
                    if (vehiculo.equals(veh)) {
                        modelTableByDispatch.setValueAt("", fila, 6);
                        modelTableByDispatch.setValueAt("", fila, 7);
                        modelTableByDispatch.setValueAt("", fila, 8);
                        modelTableByDispatch.setValueAt("", fila, 9);
                        modelTableByDispatch.setValueAt("", fila, 10);
                        modelTableByDispatch.setValueAt("", fila, 11);
                    }
                }
            }
        }
    }

    private static void setNumeroCarrerasRealizadasPorTaxi(int vehiculo) {
        int col = tblStateVeh.getColumn(vehiculo + "").getModelIndex();
        if (cbByTurn.isSelected()) {
            tblStateVeh.setValueAt(listDispatch.getDispatchByTurnVeh(vehiculo), 0, col);
        } else {
            tblStateVeh.setValueAt(listDispatch.getDispatchByDayVeh(vehiculo), 0, col);
        }
    }

    private void closeSystem() {
        int r = JOptionPane.showConfirmDialog(this, "\n<html><b>¿Esta seguro que Desea Salir del Sistema?</b></html>", "Mensaje", 0);

        if (r == 0) {
            bd.deleteAllClientMap();
            bd.closeConexion();
            System.exit(0);
        } else {
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStateVeh = new javax.swing.JTable();
        cbxZonas = new javax.swing.JComboBox();
        cbByTurn = new javax.swing.JCheckBox();
        panelCenter = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        txtCode = new javax.swing.JTextField();
        lblCall = new javax.swing.JLabel();
        jpSlope = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblClientSlope = new javax.swing.JLabel();
        lblMinuteSlope = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblTime = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblDispatchG = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblDispatchD = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblDispatchT = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbxStateVeh = new javax.swing.JComboBox();
        btnChangeState = new javax.swing.JButton();
        tabPanel = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCall = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblByDispatch = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        btnDispatch2 = new javax.swing.JButton();
        btnDelete2 = new javax.swing.JButton();
        btnNoDispatch2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        ltEvents = new javax.swing.JList();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblDispatchs = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtSearchClient = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSearchPhone = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSearchCode = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDispatchs2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblTrash = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnSlope = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnSearchClients = new javax.swing.JButton();
        btnAddClients = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnHistorical = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        lblDate = new javax.swing.JLabel();
        lblConection = new javax.swing.JLabel();
        lblSlope = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISTEMA DE DESPACHOS. V 3.0");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/logo.png")).getImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tblStateVeh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblStateVeh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblStateVeh.setCellSelectionEnabled(true);
        jScrollPane1.setViewportView(tblStateVeh);
        tblStateVeh.getAccessibleContext().setAccessibleDescription("");

        cbxZonas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Zona Norte", "Zona Centro", "Zona Sur" }));
        cbxZonas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxZonasActionPerformed(evt);
            }
        });

        cbByTurn.setText("Por Turno");
        cbByTurn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbByTurnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbByTurn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbxZonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbByTurn)
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("Telefono:");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setText("Codigo:");

        txtPhone.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        txtPhone.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneKeyTyped(evt);
            }
        });

        txtCode.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        txtCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodeActionPerformed(evt);
            }
        });
        txtCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodeKeyTyped(evt);
            }
        });

        lblCall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/nollamada.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 111, Short.MAX_VALUE))
                    .addComponent(txtPhone))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblCall, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPhone)
                            .addComponent(txtCode))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblCall, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel5.setText("Se debe Despachar al Cliente a las:");

        lblClientSlope.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblClientSlope.setText("Cliente");

        lblMinuteSlope.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMinuteSlope.setText("Minutos");

        javax.swing.GroupLayout jpSlopeLayout = new javax.swing.GroupLayout(jpSlope);
        jpSlope.setLayout(jpSlopeLayout);
        jpSlopeLayout.setHorizontalGroup(
            jpSlopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSlopeLayout.createSequentialGroup()
                .addGroup(jpSlopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpSlopeLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMinuteSlope))
                    .addComponent(lblClientSlope))
                .addGap(0, 73, Short.MAX_VALUE))
        );
        jpSlopeLayout.setVerticalGroup(
            jpSlopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSlopeLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jpSlopeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblMinuteSlope))
                .addGap(18, 18, 18)
                .addComponent(lblClientSlope)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTime.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        lblTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/reloj.png"))); // NOI18N
        lblTime.setText("Hora");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Carreras General:");

        lblDispatchG.setText("######");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Carreras Por Dia:");

        lblDispatchD.setText("######");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Carreras Por Turno:");

        lblDispatchT.setText("######");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDispatchG, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDispatchD, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDispatchT, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTime))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblDispatchG))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblDispatchD))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(lblDispatchT)))
                    .addComponent(lblTime))
                .addContainerGap())
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Cambiar Estado:");

        cbxStateVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxStateVehActionPerformed(evt);
            }
        });

        btnChangeState.setText("Cambiar");
        btnChangeState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeStateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(cbxStateVeh, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChangeState))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxStateVeh, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangeState))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblCall.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Linea", "Hora", "Telefono", "Cliente", "Direccion", "Referencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCall.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCallMousePressed(evt);
            }
        });
        tblCall.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblCallPropertyChange(evt);
            }
        });
        jScrollPane4.setViewportView(tblCall);
        if (tblCall.getColumnModel().getColumnCount() > 0) {
            tblCall.getColumnModel().getColumn(0).setPreferredWidth(15);
            tblCall.getColumnModel().getColumn(1).setPreferredWidth(10);
            tblCall.getColumnModel().getColumn(2).setPreferredWidth(10);
        }

        tblByDispatch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Telefono", "Codigo", "Cliente", "Barrio o Sector", "Direccion", "Unidad", "Min.", "H. Asig.", "Placa", "Atr.", "Nota"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true, true, true, true, true, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblByDispatch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblByDispatchMousePressed(evt);
            }
        });
        tblByDispatch.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblByDispatchPropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(tblByDispatch);
        if (tblByDispatch.getColumnModel().getColumnCount() > 0) {
            tblByDispatch.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblByDispatch.getColumnModel().getColumn(1).setPreferredWidth(65);
            tblByDispatch.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblByDispatch.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblByDispatch.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblByDispatch.getColumnModel().getColumn(5).setPreferredWidth(200);
            tblByDispatch.getColumnModel().getColumn(6).setPreferredWidth(40);
            tblByDispatch.getColumnModel().getColumn(7).setPreferredWidth(25);
            tblByDispatch.getColumnModel().getColumn(10).setPreferredWidth(25);
            tblByDispatch.getColumnModel().getColumn(11).setPreferredWidth(100);
        }

        btnDispatch2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/enviar.png"))); // NOI18N
        btnDispatch2.setToolTipText("Despachar => 'F12'");
        btnDispatch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDispatch1ActionPerformed(evt);
            }
        });

        btnDelete2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/no_despacho.png"))); // NOI18N
        btnDelete2.setToolTipText("No Despachar => 'F8'");
        btnDelete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });

        btnNoDispatch2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/eliminar.png"))); // NOI18N
        btnNoDispatch2.setToolTipText("Eliminar Llamada Entrante");
        btnNoDispatch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNoDispatch2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(0, 249, Short.MAX_VALUE)
                .addComponent(btnNoDispatch2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDispatch2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNoDispatch2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(btnDelete2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(btnDispatch2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        ltEvents.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane3.setViewportView(ltEvents);

        tblDispatchs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Telefono", "Codigo", "Cliente", "Barrio o Sector", "Direccion", "Unidad", "Min.", "H. Asig.", "Placa", "Atr.", "Nota"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDispatchs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDispatchsMousePressed(evt);
            }
        });
        tblDispatchs.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblDispatchsPropertyChange(evt);
            }
        });
        jScrollPane6.setViewportView(tblDispatchs);
        if (tblDispatchs.getColumnModel().getColumnCount() > 0) {
            tblDispatchs.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblDispatchs.getColumnModel().getColumn(1).setPreferredWidth(65);
            tblDispatchs.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblDispatchs.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblDispatchs.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblDispatchs.getColumnModel().getColumn(5).setPreferredWidth(200);
            tblDispatchs.getColumnModel().getColumn(6).setPreferredWidth(40);
            tblDispatchs.getColumnModel().getColumn(7).setPreferredWidth(25);
            tblDispatchs.getColumnModel().getColumn(10).setPreferredWidth(25);
            tblDispatchs.getColumnModel().getColumn(11).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
            .addComponent(jScrollPane6)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabPanel.addTab("Principal", jPanel8);

        jLabel10.setText("Cliente:");

        txtSearchClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchClientActionPerformed(evt);
            }
        });

        jLabel11.setText("Telefono:");

        txtSearchPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchPhoneActionPerformed(evt);
            }
        });
        txtSearchPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchPhoneKeyTyped(evt);
            }
        });

        jLabel12.setText("Codigo:");

        txtSearchCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchCodeActionPerformed(evt);
            }
        });
        txtSearchCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchCodeKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchClient, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchCode, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 44, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSearchClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtSearchPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtSearchCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 727, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tblDispatchs2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Telefono", "Codigo", "Cliente", "Barrio", "Direccion", "Unidad", "Min.", "H. Asig.", "Placa", "Atr.", "Nota"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDispatchs2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDispatchs2MousePressed(evt);
            }
        });
        jScrollPane5.setViewportView(tblDispatchs2);
        if (tblDispatchs2.getColumnModel().getColumnCount() > 0) {
            tblDispatchs2.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblDispatchs2.getColumnModel().getColumn(1).setPreferredWidth(65);
            tblDispatchs2.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblDispatchs2.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblDispatchs2.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblDispatchs2.getColumnModel().getColumn(5).setPreferredWidth(200);
            tblDispatchs2.getColumnModel().getColumn(6).setPreferredWidth(40);
            tblDispatchs2.getColumnModel().getColumn(6).setHeaderValue("Unidad");
            tblDispatchs2.getColumnModel().getColumn(7).setPreferredWidth(25);
            tblDispatchs2.getColumnModel().getColumn(7).setHeaderValue("Min.");
            tblDispatchs2.getColumnModel().getColumn(8).setHeaderValue("H. Asig.");
            tblDispatchs2.getColumnModel().getColumn(9).setHeaderValue("Placa");
            tblDispatchs2.getColumnModel().getColumn(10).setPreferredWidth(25);
            tblDispatchs2.getColumnModel().getColumn(10).setHeaderValue("Atr.");
            tblDispatchs2.getColumnModel().getColumn(11).setPreferredWidth(100);
            tblDispatchs2.getColumnModel().getColumn(11).setHeaderValue("Nota");
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane5)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
        );

        tabPanel.addTab("Despachos", jPanel9);

        tblTrash.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Telefono", "Codigo", "Cliente", "Barrio", "Direccion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTrash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblTrashMousePressed(evt);
            }
        });
        jScrollPane7.setViewportView(tblTrash);
        if (tblTrash.getColumnModel().getColumnCount() > 0) {
            tblTrash.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblTrash.getColumnModel().getColumn(1).setPreferredWidth(65);
            tblTrash.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblTrash.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblTrash.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblTrash.getColumnModel().getColumn(5).setPreferredWidth(200);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(460, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))
        );

        tabPanel.addTab("No Despachados", jPanel2);

        javax.swing.GroupLayout panelCenterLayout = new javax.swing.GroupLayout(panelCenter);
        panelCenter.setLayout(panelCenterLayout);
        panelCenterLayout.setHorizontalGroup(
            panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabPanel)
                    .addGroup(panelCenterLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpSlope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        panelCenterLayout.setVerticalGroup(
            panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCenterLayout.createSequentialGroup()
                .addGroup(panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCenterLayout.createSequentialGroup()
                        .addGroup(panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCenterLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpSlope, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addComponent(tabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnSlope.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/pendientes.png"))); // NOI18N
        btnSlope.setText("Pendientes");
        btnSlope.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSlopeActionPerformed(evt);
            }
        });

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/menu.png"))); // NOI18N
        btnMenu.setText("Menu");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/salir.png"))); // NOI18N
        btnExit.setText("Salir");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnSearchClients.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/buscar.png"))); // NOI18N
        btnSearchClients.setText("Clientes");
        btnSearchClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchClientsActionPerformed(evt);
            }
        });

        btnAddClients.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/llamada.png"))); // NOI18N
        btnAddClients.setText("Añadir Cliente");
        btnAddClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClientsActionPerformed(evt);
            }
        });

        btnImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/guardar.png"))); // NOI18N
        btnImport.setText("Importar");
        btnImport.setEnabled(false);

        btnHistorical.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/carreras.png"))); // NOI18N
        btnHistorical.setText("Historico");
        btnHistorical.setEnabled(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(btnExit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSlope)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearchClients)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddClients)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHistorical)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnHistorical, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSlope, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAddClients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSearchClients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(btnImport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        lblDate.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        lblDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/calendario.png"))); // NOI18N
        lblDate.setText("Fecha");

        lblConection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/nosenal.png"))); // NOI18N

        lblSlope.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/alarma.png"))); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSlope, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblConection, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSlope, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblConection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCenter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodeActionPerformed
        try {
            if (tblByDispatch.isEditing()) {
                tblByDispatch.getCellEditor().cancelCellEditing();
            } else {
                int code = Integer.parseInt(txtCode.getText());
                Client c = listClient.getClientByCode(code);
                modelTableByDispatch.insertRow(0, changeToArrayClient(c));
                bd.insertClientMap(c);
                txtCode.setText("");
                if (code == 0) {
                    tblByDispatch.setColumnSelectionInterval(3, 3);
                    tblByDispatch.setRowSelectionInterval(0, 0);
                    tblByDispatch.requestFocus();
                } else {
                    tblByDispatch.setColumnSelectionInterval(6, 6);
                    tblByDispatch.setRowSelectionInterval(0, 0);
                    tblByDispatch.requestFocus();
                }
                tabPanel.setSelectedIndex(0);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un Codigo");
        } catch (NullPointerException ex) {
            //JOptionPane.showMessageDialog(this, "Codigo No Existe");
        }
    }//GEN-LAST:event_txtCodeActionPerformed

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneActionPerformed
        String phone = txtPhone.getText();
        try {
            if (tblByDispatch.isEditing()) {
                tblByDispatch.getCellEditor().cancelCellEditing();
            } else {
                modelTableByDispatch.insertRow(0, changeToArrayClient(listClient.getClientByPhone(Functions.validatePhone(phone))));
                txtPhone.setText("");
                tblByDispatch.setColumnSelectionInterval(6, 6);
                tblByDispatch.setRowSelectionInterval(0, 0);
                tblByDispatch.requestFocus();
            }
        } catch (NullPointerException ex) {
            if (!Functions.validatePhone(phone).equals("")) {
                modelTableByDispatch.insertRow(0, addNewClient(phone));
                txtPhone.setText("");
                tblByDispatch.setColumnSelectionInterval(3, 3);
                tblByDispatch.setRowSelectionInterval(0, 0);
                tblByDispatch.requestFocus();
            }
        }
        tabPanel.setSelectedIndex(0);
    }//GEN-LAST:event_txtPhoneActionPerformed

    private void btnChangeStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeStateActionPerformed
        int[] numCol = tblStateVeh.getSelectedColumns();
        JButton editor = (JButton) cbxStateVeh.getEditor().getEditorComponent();
        String etiqueta = editor.getText();
        CodesTaxy ct = listCodesTaxy.getCodeTaxyByEtiqueta(etiqueta);
        if (!ct.getIdCodigo().equals("OCU") && !ct.getIdCodigo().equals("ASI")) {
            if (numCol.length > 0) {
                for (int i = 0; i < numCol.length; i++) {
                    int vehiculo = Integer.parseInt(listVehiculos.getEncabezadosTablaVehiculosArrayString()[numCol[i]]);
                    Vehiculo v = listVehiculos.getVehiculoByVehiculo(vehiculo);
                    if (v.getCodesTaxy().getIdCodigo().equals("OCU") || v.getCodesTaxy().getIdCodigo().equals("ASI")) {
                        int r = JOptionPane.showConfirmDialog(this, "\n<html><b>¿Esta seguro que quiere Activar esta unidad?</b></html>", "Error", 0);

                        if (r == 0) {
                            clearStateVehiculo(tblStateVeh.getColumnName(numCol[i]) + "");
                            listVehiculos.setCodeTaxyByEtiqueta(vehiculo, ct);
                        }
                    } else {
                        listVehiculos.setCodeTaxyByEtiqueta(vehiculo, ct);
                    }
                }
                paintStateTaxy(listVehiculos.getEncabezadosTablaVehiculosArrayList());
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una Unidad primero", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Este estado no se puede asignar desde aqui...", "Error...", 0);
        }
    }//GEN-LAST:event_btnChangeStateActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        closeSystem();
    }//GEN-LAST:event_btnExitActionPerformed

    private void tblCallPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblCallPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblCallPropertyChange

    private void tblByDispatchPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblByDispatchPropertyChange
        if (evt.getPropertyName().equals("tableCellEditor")) {
            int rowSelected = tblByDispatch.getSelectedRow();
            int columnSelected = tblByDispatch.getSelectedColumn();

            if (columnSelected == 6) {  //Cuando se Produce Cambio en la Celda de la Unidad
                if (tblByDispatch.isEditing()) {
                    tblByDispatch.getCellEditor().cancelCellEditing();
                } else {
                    try {
                        int vehiculo = Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 6).toString());
                        if (!vehiculoExisteTableByDispatch("" + vehiculo)) {
                            String placa = listVehiculos.getPlacaVeh(vehiculo);
                            if (placa == null) {
                                JOptionPane.showMessageDialog(null, "Solo Puede Ingresar Numeros de Unidad Validos");
                                tblByDispatch.setValueAt("", rowSelected, 6);
                                tblByDispatch.setValueAt("", rowSelected, 7);
                                tblByDispatch.setValueAt("", rowSelected, 8);
                                tblByDispatch.setValueAt("", rowSelected, 9);
                                tblByDispatch.setValueAt("", rowSelected, 10);
                            } else {
                                if (listVehiculos.getBloqueoVehiculo(vehiculo)) {
                                    JOptionPane.showMessageDialog(this, "Esta unidad ha sido bloqueada por falta de PAGO, no se podrá despachar "
                                            + "más carreras hasta que no se comunique con KRADAC\n"
                                            + "para que sea habilitada nuevamente...", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    tblByDispatch.setValueAt("", rowSelected, 6);
                                    tblByDispatch.setValueAt("", rowSelected, 7);
                                    tblByDispatch.setValueAt("", rowSelected, 8);
                                    tblByDispatch.setValueAt("", rowSelected, 9);
                                    tblByDispatch.setValueAt("", rowSelected, 10);
                                } else {

                                    Vehiculo v = listVehiculos.getVehiculoByVehiculo(vehiculo);
                                    if (v.getCodesTaxy().getIdCodigo().equals("OCU") || v.getCodesTaxy().getIdCodigo().equals("ASI")) {
                                        int r = JOptionPane.showConfirmDialog(this, "\n<html><b>¿Esta seguro que quiere Activar esta unidad?</b></html>", "Error", 0);

                                        if (r == 0) {
                                            tblByDispatch.setValueAt(placa, rowSelected, 9);
                                            tblByDispatch.setValueAt(Functions.getTime(), rowSelected, 8);

                                            listVehiculos.setCodeTaxyByEtiqueta(vehiculo, listCodesTaxy.getCodesTaxyById("AC"));
                                            paintStateTaxy(listVehiculos.getEncabezadosTablaVehiculosArrayList());
                                            tblByDispatch.setValueAt("", rowSelected, 6);
                                            tblByDispatch.setValueAt("", rowSelected, 7);
                                            tblByDispatch.setValueAt("", rowSelected, 8);
                                            tblByDispatch.setValueAt("", rowSelected, 9);
                                            tblByDispatch.setValueAt("", rowSelected, 10);
                                        } else {
                                            tblByDispatch.setValueAt("", rowSelected, 6);
                                            tblByDispatch.setValueAt("", rowSelected, 7);
                                            tblByDispatch.setValueAt("", rowSelected, 8);
                                            tblByDispatch.setValueAt("", rowSelected, 9);
                                            tblByDispatch.setValueAt("", rowSelected, 10);
                                        }
                                    } else {
                                        tblByDispatch.setValueAt(placa, rowSelected, 9);
                                        tblByDispatch.setValueAt(Functions.getTime(), rowSelected, 8);

                                        listVehiculos.setCodeTaxyByEtiqueta(vehiculo, listCodesTaxy.getCodesTaxyById("ASI"));
                                        paintStateTaxy(listVehiculos.getEncabezadosTablaVehiculosArrayList());

                                        Principal.listMessageToServer.addMessageGarmin(new MessageToGarmin(vehiculo,
                                                tblByDispatch.getValueAt(rowSelected, 3).toString(),
                                                tblByDispatch.getValueAt(rowSelected, 4).toString(),
                                                tblByDispatch.getValueAt(rowSelected, 5).toString(),
                                                numDispatchs, Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 2)),
                                                false)
                                        );

                                        Call c = listCall.getCallByPhone(tblByDispatch.getValueAt(rowSelected, 1).toString());

                                        if (c != null) {
                                            Principal.listMessageToServer.addMessageAndroid(new MessageToAndroid(c.getIdSolicitud(),
                                                    Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 6).toString()),
                                                    false)
                                            );
                                        }

                                        bd.insertLifeAssigns(
                                                userLogin.getUser(),//user
                                                (String) tblByDispatch.getValueAt(rowSelected, 0),//hora
                                                Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 2)),//code
                                                (String) tblByDispatch.getValueAt(rowSelected, 1),//telefono
                                                "ASI", 0,
                                                "",
                                                "",
                                                "",
                                                "",
                                                Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 6)),//vehiculo
                                                0,//Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 7)),//minute
                                                (String) tblByDispatch.getValueAt(rowSelected, 9),//id_vehiculo
                                                0,//Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 10)),//atraso
                                                "",
                                                "",
                                                0,
                                                0.0, 0.0
                                        );
                                    }

                                }
                            }
                        } else {
                            int r = JOptionPane.showConfirmDialog(this,
                                    "El Vehiculo ya ha sido asignado a otra carrera"
                                    + "\n<html><b>¿Activar esta unidad?</b></html>", "Error", 0);

                            if (r == 0) {
                                clearStateVehiculo(vehiculo + "");
                                listVehiculos.setCodeTaxyByEtiqueta(vehiculo, listCodesTaxy.getCodesTaxyById("AC"));
                                paintStateTaxy(listVehiculos.getEncabezadosTablaVehiculosArrayList());
                            } else {
                                tblByDispatch.setValueAt("", rowSelected, 6);
                            }
                        }
                    } catch (NumberFormatException e) {
                        try {
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 6).toString());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Solo Puede Ingresar Numeros de Unidad Validos", "ERROR", JOptionPane.ERROR_MESSAGE);
                            tblByDispatch.setValueAt("", rowSelected, 6);
                        } finally {
                            tblByDispatch.setValueAt("", rowSelected, 7);
                            tblByDispatch.setValueAt("", rowSelected, 8);
                            tblByDispatch.setValueAt("", rowSelected, 9);
                            tblByDispatch.setValueAt("", rowSelected, 10);
                        }
                    }
                }
            }

            if (columnSelected == 7) { //Cuando se Produce Cambio en la Celda de Minutos de Llegada
                if (tblByDispatch.isEditing()) {
                    tblByDispatch.getCellEditor().cancelCellEditing();
                } else {
                    if (!tblByDispatch.getValueAt(rowSelected, 6).toString().equals("")) {
                        try {
                            int minute = Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 7).toString());
                            tblByDispatch.setValueAt(minute * -1, rowSelected, 10);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Solo Puede Ingresar Numeros", "ERROR", JOptionPane.ERROR_MESSAGE);
                            tblByDispatch.setValueAt("", rowSelected, 7);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Asigne Unidad al Despacho");
                        tblByDispatch.setValueAt("", rowSelected, 7);
                    }
                }
            }
        }
    }//GEN-LAST:event_tblByDispatchPropertyChange

    private void cbxStateVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxStateVehActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxStateVehActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        if ((menu == null) || (!menu.isDisplayable())) {
            menu = new Menu();
        }
        menu.setVisible(true);
        menu.setLocationRelativeTo(this);
    }//GEN-LAST:event_btnMenuActionPerformed

    private void tblByDispatchMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblByDispatchMousePressed
        int intClicks = evt.getClickCount();
        int intAntiClicks = evt.getButton();

        if (intClicks == 1 && intAntiClicks == 3) {
            int rowSelected = tblByDispatch.getSelectedRow();
            int columnSelected = tblByDispatch.getSelectedColumn();

            String value = tblByDispatch.getValueAt(rowSelected, 9).toString();
            if (columnSelected == 9 && !value.equals("")) {
                System.out.println("Vehiculo");
            } else {
                if ((fc == null) || (!fc.isDisplayable())) {
                    fc = new FrameClients(tblByDispatch.getValueAt(rowSelected, 1).toString(),
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 2).toString()),
                            tblByDispatch.getValueAt(rowSelected, 3).toString(),
                            tblByDispatch.getValueAt(rowSelected, 4).toString(),
                            tblByDispatch.getValueAt(rowSelected, 5).toString(),
                            tblByDispatch.getValueAt(rowSelected, 11).toString());
                }
                fc.setVisible(true);
                fc.setLocationRelativeTo(this);
            }
        }
    }//GEN-LAST:event_tblByDispatchMousePressed

    private void cbxZonasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxZonasActionPerformed
        System.out.println(cbxZonas.getSelectedItem());
    }//GEN-LAST:event_cbxZonasActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeSystem();
    }//GEN-LAST:event_formWindowClosing

    private void txtPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtPhoneKeyTyped

    private void txtCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtCodeKeyTyped

    private void btnSlopeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSlopeActionPerformed
        if ((fp == null) || (!fp.isDisplayable())) {
            fp = new FramePending();
        }
        fp.setVisible(true);
        fp.setLocationRelativeTo(this);
    }//GEN-LAST:event_btnSlopeActionPerformed

    private void btnSearchClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchClientsActionPerformed
        if ((sc == null) || (!sc.isDisplayable())) {
            sc = new SearchClients();
        }
        sc.setVisible(true);
        sc.setLocationRelativeTo(this);
    }//GEN-LAST:event_btnSearchClientsActionPerformed

    private void tblDispatchs2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDispatchs2MousePressed
        int intClicks = evt.getClickCount();

        if (intClicks == 2) {
            int rowSelected = tblDispatchs2.getSelectedRow();

            if ((fc == null) || (!fc.isDisplayable())) {
                fc = new FrameClients(tblDispatchs2.getValueAt(rowSelected, 1).toString(),
                        Integer.parseInt(tblDispatchs2.getValueAt(rowSelected, 2).toString()),
                        tblDispatchs2.getValueAt(rowSelected, 3).toString(),
                        tblDispatchs2.getValueAt(rowSelected, 0).toString());
            }
            fc.setVisible(true);
            fc.setLocationRelativeTo(this);
        }
    }//GEN-LAST:event_tblDispatchs2MousePressed

    private void txtSearchClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchClientActionPerformed
        listDispatch.loadDispatchByClient(txtSearchClient.getText());
    }//GEN-LAST:event_txtSearchClientActionPerformed

    private void txtSearchPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchPhoneActionPerformed
        listDispatch.loadDispatchByPhone(txtSearchPhone.getText());
    }//GEN-LAST:event_txtSearchPhoneActionPerformed

    private void txtSearchCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchCodeActionPerformed
        try {
            listDispatch.loadDispatchByCode(Integer.parseInt(txtSearchCode.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un Codigo Para Inicia la Busqueda");
        }

    }//GEN-LAST:event_txtSearchCodeActionPerformed

    private void txtSearchPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchPhoneKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtSearchPhoneKeyTyped

    private void txtSearchCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchCodeKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtSearchCodeKeyTyped

    private void btnAddClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddClientsActionPerformed
        if ((fc == null) || (!fc.isDisplayable())) {
            fc = new FrameClients();
        }
        fc.setVisible(true);
        fc.setLocationRelativeTo(this);
    }//GEN-LAST:event_btnAddClientsActionPerformed

    private void tblDispatchsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDispatchsMousePressed
        int intClicks = evt.getClickCount();

        if (intClicks == 2) {
            int rowSelected = tblDispatchs.getSelectedRow();

            if ((fc == null) || (!fc.isDisplayable())) {
                fc = new FrameClients(tblDispatchs.getValueAt(rowSelected, 1).toString(),
                        Integer.parseInt(tblDispatchs.getValueAt(rowSelected, 2).toString()),
                        tblDispatchs.getValueAt(rowSelected, 3).toString(),
                        tblDispatchs.getValueAt(rowSelected, 0).toString());
            }
            fc.setVisible(true);
            fc.setLocationRelativeTo(this);
        }
    }//GEN-LAST:event_tblDispatchsMousePressed

    private void tblDispatchsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblDispatchsPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDispatchsPropertyChange

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        deleteRowByDispatch();
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btnDispatch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDispatch1ActionPerformed
        dispatch();
    }//GEN-LAST:event_btnDispatch1ActionPerformed

    private void cbByTurnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbByTurnActionPerformed
        redimencionarTableVehiculos();
    }//GEN-LAST:event_cbByTurnActionPerformed

    private void tblCallMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCallMousePressed
        int intClicks = evt.getClickCount();

        if (intClicks == 2) {
            int rowSelected = tblCall.getSelectedRow();

            Call c = listCall.getCallByPhone(tblCall.getValueAt(rowSelected, 2).toString());
            try {
                modelTableCall.removeRow(rowSelected);
            } catch (NullPointerException e) {
            }

            Client cl = listClient.getClientByPhone(Functions.validatePhone(c.getPhone()));

            if (cl == null) {
                cl = listClient.getClientByCode(0);
            }

            cl.setLatitud(c.getLatitud());
            cl.setLongitud(c.getLongitud());

            modelTableByDispatch.insertRow(0, changeToArrayClient(new Client(c.getClient(), "", c.getPhone(), c.getDirection(), "",
                    cl.getCode(), 0, cl.getLatitud(), cl.getLongitud(), c.getReference(), "")));
        }
    }//GEN-LAST:event_tblCallMousePressed

    private void btnNoDispatch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNoDispatch2ActionPerformed
        int rowSelected = tblCall.getSelectedRow();
        if (rowSelected == -1) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado una Fila. En la Tabla de Llamadas");
        } else {
            modelTableCall.removeRow(rowSelected);
        }
    }//GEN-LAST:event_btnNoDispatch2ActionPerformed

    private void tblTrashMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTrashMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblTrashMousePressed

    /**
     */
    public void main() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddClients;
    private javax.swing.JButton btnChangeState;
    private javax.swing.JButton btnDelete2;
    private javax.swing.JButton btnDispatch2;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHistorical;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnNoDispatch2;
    private javax.swing.JButton btnSearchClients;
    private javax.swing.JButton btnSlope;
    public static javax.swing.JCheckBox cbByTurn;
    public static javax.swing.JComboBox cbxStateVeh;
    private javax.swing.JComboBox cbxZonas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    public static javax.swing.JPanel jpSlope;
    public static javax.swing.JLabel lblCall;
    public static javax.swing.JLabel lblClientSlope;
    public static javax.swing.JLabel lblConection;
    public static javax.swing.JLabel lblDate;
    public static javax.swing.JLabel lblDispatchD;
    public static javax.swing.JLabel lblDispatchG;
    public static javax.swing.JLabel lblDispatchT;
    public static javax.swing.JLabel lblMinuteSlope;
    public static javax.swing.JLabel lblSlope;
    public static javax.swing.JLabel lblTime;
    private javax.swing.JList ltEvents;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JTabbedPane tabPanel;
    public static javax.swing.JTable tblByDispatch;
    public static javax.swing.JTable tblCall;
    public static javax.swing.JTable tblDispatchs;
    public static javax.swing.JTable tblDispatchs2;
    public static javax.swing.JTable tblStateVeh;
    public static javax.swing.JTable tblTrash;
    private javax.swing.JTextField txtCode;
    public static javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearchClient;
    private javax.swing.JTextField txtSearchCode;
    private javax.swing.JTextField txtSearchPhone;
    // End of variables declaration//GEN-END:variables
}

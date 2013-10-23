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
import com.kradac.despachos.administration.Company;
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
import com.kradac.despachos.administration.list.ListPending;
import com.kradac.despachos.database.DataBase;
import com.kradac.despachos.methods.Functions;
import com.kradac.despachos.threads.ThreadClient;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.AbstractAction;
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
    public static ListClients listClient;
    public static ListVehiculos listVehiculos;
    public static ListCodesTaxy listCodesTaxy;
    public static ListStateCivil listStateCivil;
    public static ListByDispatch listByDispatch = new ListByDispatch();
    public static ListDispatch listDispatch = new ListDispatch();
    public static ListRolUser listRolUser = new ListRolUser();
    public static ListPending listPending = new ListPending();
    public static ListUser listUser = new ListUser();
    public static DataBase bd;
    public static Properties fileConfig;
    public static User userLogin;
    public static Company company;
    public static DefaultTableModel modelTableByDispatch;
    public static DefaultTableModel modelTableDispatch;
    public static ArrayList<String> codeStateVeh;
    public static ArrayList<String> etiquetaStateVeh;
    public static ArrayList<Integer> colorStateVeh;

    private Menu menu;
    private FrameClients c;

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        configStart();
    }

    public Principal(ListPerson lp, DataBase b, Properties p, User u, ListStateCivil lsc, ListRolUser lru, ListUser lu) {
        listPerson = lp;
        bd = b;
        fileConfig = p;
        userLogin = u;
        listStateCivil = lsc;
        listRolUser = lru;
        listUser = lu;
    }

    public void configStart() {
        company = bd.loadCompany();
        this.setTitle("Despachos KRADAC - " + company.getCompany() + " || " + userLogin.getUser());
        listPending = bd.loadPending();
        listCodesTaxy = bd.loadCodesTaxy();
        listVehiculos = bd.loadVehiculos(listPerson, bd.loadModelos(bd.loadMarcas()), bd.loadZonas(), listCodesTaxy);
        listDispatch = bd.loadDispatch();
        ThreadClient tc = new ThreadClient();
        tc.start();
        modelTableByDispatch = (DefaultTableModel) tblByDispatch.getModel();
        modelTableDispatch = (DefaultTableModel) tblDespachados.getModel();
        listDispatch.loadDispatch();
        Time t = new Time();
        t.start(lblDate, lblTime);
        setLocationRelativeTo(null);
        codeStateVeh = listCodesTaxy.getIdCodigo();
        etiquetaStateVeh = listCodesTaxy.getEtiqueta();
        colorStateVeh = listCodesTaxy.getColor();
        redimencionarTableVehiculos();
        loadComboStates();
        keyPressed();
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

    public void redimencionarTableVehiculos() {
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
                tc.setMaxWidth(25);
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

    public void paintStateTaxy(ArrayList encab) {
        //Clave valor
        //[n_unidad]==>[id_codigo]
        Map unidadCodigoBD = new HashMap();
        for (Vehiculo v : listVehiculos.getVehiculos()) {
            unidadCodigoBD.put(v.getVehiculo(), v.getCodesTaxy().getIdCodigo());
        }

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

    public void loadComboStates() {
        cbxStateVeh.setModel(new javax.swing.DefaultComboBoxModel(etiquetaStateVeh.toArray()));
        cbxStateVeh.setEditable(true);
        cbxStateVeh.setRenderer(new ColorCellRenderer(codeStateVeh, colorStateVeh));
        ColorComboBoxEditor editor = new ColorComboBoxEditor(listCodesTaxy.getMapEtiqColor());
        cbxStateVeh.setEditor(editor);
    }

    public String[] changeToArrayClient(Client client) {
        String[] dataPerson = {
            Functions.getTime(),
            client.getPhone(),
            "" + client.getCode(),
            client.getName() + " " + client.getLastname(),
            client.getSector(),
            client.getDirection(),
            "",
            "",
            "", "", "",
            client.getReference()
        };
        return dataPerson;
    }

    public String[] addNewClient(String phone) {
        String[] dataClient = {Functions.getTime(), phone, "0", "", "", "", "", "", "", "", "", ""};
        return dataClient;
    }

    public String[] changeToArrayByDispatch(ByDispatch dispatch) {
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

    public void dispatch() {
        int rowSelected = tblByDispatch.getSelectedRow();
        if (rowSelected != -1) {
            String timeLastSipatch;
            try {
                timeLastSipatch = listByDispatch.getLastDispatch().getTime();
            } catch (NullPointerException e) {
                timeLastSipatch = Functions.getTime();
            }

            if (Functions.getTime().compareTo(timeLastSipatch) < 0) {
                JOptionPane.showMessageDialog(this, "Iguale la Hora de Su Reloj Para Realizar Despachos", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int unidad = Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 6).toString());
                    ByDispatch auxDispatch = new ByDispatch(
                            tblByDispatch.getValueAt(rowSelected, 0).toString(),
                            tblByDispatch.getValueAt(rowSelected, 1).toString(),
                            unidad,
                            tblByDispatch.getValueAt(rowSelected, 3).toString(),
                            tblByDispatch.getValueAt(rowSelected, 4).toString(),
                            tblByDispatch.getValueAt(rowSelected, 5).toString(),
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 6).toString()),
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 7).toString()),
                            tblByDispatch.getValueAt(rowSelected, 8).toString(),
                            tblByDispatch.getValueAt(rowSelected, 9).toString(),
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 10).toString()),
                            tblByDispatch.getValueAt(rowSelected, 11).toString());

                    listByDispatch.addDispatch(auxDispatch);
                    
                    Client c = Principal.listClient.getClientByCode(Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 2)));
                    bd.insertLifeAssigns(
                            userLogin.getUser(),//user
                            (String) tblByDispatch.getValueAt(rowSelected, 0),//hora
                            Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 2)),//code
                            (String) tblByDispatch.getValueAt(rowSelected, 1),//telefono
                            "OCU",
                            0, (String) tblByDispatch.getValueAt(rowSelected, 3),//Cliente
                            (String) tblByDispatch.getValueAt(rowSelected, 4),//Sector
                            (String) tblByDispatch.getValueAt(rowSelected, 5),//Dirección
                            "",//destino
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 6) + ""),//vehiculo
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 7) + ""),//minute
                            (String) tblByDispatch.getValueAt(rowSelected, 9),//id_vehiculo
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 10) + ""),//atraso
                            c.getReference(),
                            (String) tblByDispatch.getValueAt(rowSelected, 11),//NOTA
                            1,
                            0,
                            0
                    );
                    bd.insertAssigns(
                            userLogin.getUser(),//user
                            (String) tblByDispatch.getValueAt(rowSelected, 0),//hora
                            Integer.parseInt((String) tblByDispatch.getValueAt(rowSelected, 2)),//code
                            (String) tblByDispatch.getValueAt(rowSelected, 1),//telefono                            
                            (String) tblByDispatch.getValueAt(rowSelected, 3),//Cliente
                            (String) tblByDispatch.getValueAt(rowSelected, 4),//Sector
                            (String) tblByDispatch.getValueAt(rowSelected, 5),//Dirección
                            "",//destino
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 6) + ""),//vehiculo
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 7) + ""),//minute
                            tblByDispatch.getValueAt(rowSelected, 8) + "",//hora asgi
                            (String) tblByDispatch.getValueAt(rowSelected, 9),//id_vehiculo
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 10) + ""),//atraso
                            c.getReference(),
                            (String) tblByDispatch.getValueAt(rowSelected, 11),//NOTA
                            1
                    );
                    
                    modelTableByDispatch.removeRow(rowSelected);
                    modelTableDispatch.insertRow(0, changeToArrayByDispatch(auxDispatch));

                    listVehiculos.setCodeTaxyByEtiqueta(unidad, listCodesTaxy.getCodesTaxyById("OCU"));
                    paintStateTaxy(listVehiculos.getEncabezadosTablaVehiculosArrayList());

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Llene los Campos Necesarios para Despachar");
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
            String strUnidad = tblByDispatch.getValueAt(rowSelected, 6).toString();
            if (!strUnidad.equals("")) {
                listVehiculos.setCodeTaxyByEtiqueta(Integer.parseInt(strUnidad), listCodesTaxy.getCodesTaxyById("AC"));
                paintStateTaxy(listVehiculos.getEncabezadosTablaVehiculosArrayList());
            }
            modelTableByDispatch.removeRow(rowSelected);
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
                        modelTableByDispatch.setValueAt(client.getName()+" "+client.getLastname(), fila, 3);
                        modelTableByDispatch.setValueAt(client.getSector(), fila, 4);
                        modelTableByDispatch.setValueAt(client.getDirection(), fila, 5);
                        modelTableByDispatch.setValueAt(note, fila, 11);
                    }
                }
            }
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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        txtCode = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblCall = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblClientSlope = new javax.swing.JLabel();
        lblMinuteSlope = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblTime = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbxStateVeh = new javax.swing.JComboBox();
        btnChangeState = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCall = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblByDispatch = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        btnDispatch1 = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        btnNoDispatch1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        ltEvents = new javax.swing.JList();
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
        tblDespachados = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnSlope = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnClients = new javax.swing.JButton();
        btnClaims = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnHistorical = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        lblDate = new javax.swing.JLabel();
        lblConection = new javax.swing.JLabel();
        lblSlope = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISTEMA DE DESPACHOS. V 3.0");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/logo.png")).getImage());

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(0, 38, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel2.setText("Telefono");

        jLabel3.setText("Codigo");

        txtPhone.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        txtPhone.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });

        txtCode.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        txtCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodeActionPerformed(evt);
            }
        });

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
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPhone)
                    .addComponent(txtCode))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblCall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/nollamada.png"))); // NOI18N

        jLabel5.setText("Se debe Despachar al Cliente a las:");

        lblClientSlope.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblClientSlope.setText("Cliente");

        lblMinuteSlope.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMinuteSlope.setText("Minutos");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCall, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMinuteSlope))
                    .addComponent(lblClientSlope))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblMinuteSlope))
                .addGap(18, 18, 18)
                .addComponent(lblClientSlope)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTime.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        lblTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/reloj.png"))); // NOI18N
        lblTime.setText("Hora");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblTime)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblTime)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(0, 0, Short.MAX_VALUE)
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
                "Linea", "Hora", "Telefono", "Codigo", "Cliente", "Barrio o Sector", "Direccion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCall.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblCallPropertyChange(evt);
            }
        });
        jScrollPane4.setViewportView(tblCall);
        if (tblCall.getColumnModel().getColumnCount() > 0) {
            tblCall.getColumnModel().getColumn(0).setPreferredWidth(25);
            tblCall.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblCall.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblCall.getColumnModel().getColumn(3).setPreferredWidth(25);
            tblCall.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblCall.getColumnModel().getColumn(6).setPreferredWidth(200);
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

        btnDispatch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/enviar.png"))); // NOI18N
        btnDispatch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDispatch1ActionPerformed(evt);
            }
        });

        btnDelete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/eliminar.png"))); // NOI18N
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });

        btnNoDispatch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/no_despacho.png"))); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(0, 249, Short.MAX_VALUE)
                .addComponent(btnNoDispatch1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDispatch1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNoDispatch1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDelete1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDispatch1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ltEvents.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(ltEvents);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Inicio", jPanel8);

        jLabel10.setText("Cliente:");

        jLabel11.setText("Telefono:");

        jLabel12.setText("Codigo:");

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
                .addGap(0, 638, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tblDespachados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblDespachados);
        if (tblDespachados.getColumnModel().getColumnCount() > 0) {
            tblDespachados.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblDespachados.getColumnModel().getColumn(1).setPreferredWidth(65);
            tblDespachados.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblDespachados.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblDespachados.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblDespachados.getColumnModel().getColumn(5).setPreferredWidth(200);
            tblDespachados.getColumnModel().getColumn(6).setPreferredWidth(40);
            tblDespachados.getColumnModel().getColumn(7).setPreferredWidth(25);
            tblDespachados.getColumnModel().getColumn(10).setPreferredWidth(25);
            tblDespachados.getColumnModel().getColumn(11).setPreferredWidth(100);
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
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Despachos", jPanel9);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        btnSlope.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/pendientes.png"))); // NOI18N
        btnSlope.setText("Pendientes");

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

        btnClients.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/buscar.png"))); // NOI18N
        btnClients.setText("Clientes");

        btnClaims.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/bechofer.png"))); // NOI18N
        btnClaims.setText("Reclamos");

        btnImport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/guardar.png"))); // NOI18N
        btnImport.setText("Importar");

        btnHistorical.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/carreras.png"))); // NOI18N
        btnHistorical.setText("Historico");

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
                .addComponent(btnClients)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClaims)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHistorical)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImport)
                .addContainerGap(19, Short.MAX_VALUE))
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
                    .addComponent(btnClaims, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodeActionPerformed
        try {
            if (tblByDispatch.isEditing()) {
                tblByDispatch.getCellEditor().cancelCellEditing();
            } else {
                Client c = listClient.getClientByCode(Integer.parseInt(txtCode.getText()));
                modelTableByDispatch.insertRow(0, changeToArrayClient(c));
                //bd.insertClientMap(c);
                txtCode.setText("");
                tblByDispatch.setColumnSelectionInterval(6, 6);
                tblByDispatch.setRowSelectionInterval(0, 0);
                tblByDispatch.requestFocus();
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
    }//GEN-LAST:event_txtPhoneActionPerformed

    private void btnChangeStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeStateActionPerformed
        int[] numCol = tblStateVeh.getSelectedColumns();
        JButton editor = (JButton) cbxStateVeh.getEditor().getEditorComponent();
        String etiqueta = editor.getText();
        CodesTaxy ct = listCodesTaxy.getCodeTaxyByEtiqueta(etiqueta);
        if (!ct.getIdCodigo().equals("OCU") && !ct.getIdCodigo().equals("ASI")) {
            if (numCol.length > 0) {
                for (int i = 0; i < numCol.length; i++) {
                    listVehiculos.setCodeTaxyByEtiqueta(Integer.parseInt(listVehiculos.getEncabezadosTablaVehiculosArrayString()[numCol[i]]), ct);
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
        bd.closeConexion();
        System.exit(0);
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
                        int unidad = Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 6).toString());
                        String placa = listVehiculos.getPlacaVeh(unidad);
                        if (placa == null) {
                            JOptionPane.showMessageDialog(null, "Solo Puede Ingresar Numeros de Unidad Validos");
                            tblByDispatch.setValueAt("", rowSelected, 6);
                            tblByDispatch.setValueAt("", rowSelected, 9);
                            tblByDispatch.setValueAt("", rowSelected, 8);
                        } else {
                            tblByDispatch.setValueAt(placa, rowSelected, 9);
                            tblByDispatch.setValueAt(Functions.getTime(), rowSelected, 8);

                            listVehiculos.setCodeTaxyByEtiqueta(unidad, listCodesTaxy.getCodesTaxyById("ASI"));
                            paintStateTaxy(listVehiculos.getEncabezadosTablaVehiculosArrayList());
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
                                    1,
                                    0, 0
                            );
                        }
                    } catch (NumberFormatException e) {
                        try {
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 6).toString());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Solo Puede Ingresar Numeros de Unidad Validos");
                            tblByDispatch.setValueAt("", rowSelected, 6);
                        } finally {
                            tblByDispatch.setValueAt("", rowSelected, 9);
                            tblByDispatch.setValueAt("", rowSelected, 8);
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
                            JOptionPane.showMessageDialog(null, "Solo Puede Ingresar Numeros");
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

    private void btnDispatch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDispatch1ActionPerformed
        dispatch();
    }//GEN-LAST:event_btnDispatch1ActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        deleteRowByDispatch();
    }//GEN-LAST:event_btnDelete1ActionPerformed

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
                if ((c == null) || (!c.isDisplayable())) {
                    c = new FrameClients(tblByDispatch.getValueAt(rowSelected, 1).toString(),
                            Integer.parseInt(tblByDispatch.getValueAt(rowSelected, 2).toString()),
                            tblByDispatch.getValueAt(rowSelected, 3).toString(),
                            tblByDispatch.getValueAt(rowSelected, 4).toString(),
                            tblByDispatch.getValueAt(rowSelected, 5).toString(),
                            tblByDispatch.getValueAt(rowSelected, 11).toString());
                }
                c.setVisible(true);
                c.setLocationRelativeTo(this);
            }
        }
    }//GEN-LAST:event_tblByDispatchMousePressed

    private void cbxZonasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxZonasActionPerformed
        System.out.println(cbxZonas.getSelectedItem());
    }//GEN-LAST:event_cbxZonasActionPerformed

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
    private javax.swing.JButton btnChangeState;
    private javax.swing.JButton btnClaims;
    private javax.swing.JButton btnClients;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnDispatch1;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHistorical;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnNoDispatch1;
    private javax.swing.JButton btnSlope;
    private javax.swing.JComboBox cbxStateVeh;
    private javax.swing.JComboBox cbxZonas;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
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
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCall;
    private javax.swing.JLabel lblClientSlope;
    private javax.swing.JLabel lblConection;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblMinuteSlope;
    private javax.swing.JLabel lblSlope;
    private javax.swing.JLabel lblTime;
    private javax.swing.JList ltEvents;
    public static javax.swing.JTable tblByDispatch;
    private javax.swing.JTable tblCall;
    private javax.swing.JTable tblDespachados;
    private javax.swing.JTable tblStateVeh;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearchClient;
    private javax.swing.JTextField txtSearchCode;
    private javax.swing.JTextField txtSearchPhone;
    // End of variables declaration//GEN-END:variables
}

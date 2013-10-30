/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.interfaz;

import com.kradac.despachos.administration.CodesTaxy;
import com.kradac.despachos.administration.Modelo;
import com.kradac.despachos.administration.Person;
import com.kradac.despachos.administration.Vehiculo;
import com.kradac.despachos.administration.Zona;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dalton
 */
public class FrameVehiculos extends javax.swing.JFrame {

    private DefaultTableModel modelTableVehiculo;

    /**
     * Creates new form Person
     */
    public FrameVehiculos() {
        initComponents();
        loadComponent();
    }

    private void loadComponent() {
        modelTableVehiculo = (DefaultTableModel) tblVehiculo.getModel();
        for (Vehiculo v : Principal.listVehiculos.getVehiculos()) {
            modelTableVehiculo.addRow(changeToArrayVehiculo(v));
            changeToArrayVehiculo(v);
        }

        for (Zona zo : Principal.listZonas.getZonas()) {
            cbxZona.addItem(zo.getZona());
        }
        for (Person con : Principal.listPerson.getPersons()) {
            cbxConductor.addItem(con.getLastname() + " " + con.getName());
        }
        for (Person con : Principal.listPerson.getPersons()) {
            cbxPropietario.addItem(con.getLastname() + " " + con.getName());
        }
        for (Modelo mod : Principal.listModels.getModelos()) {
            cbxModelo.addItem(mod.getModeloVehiculo());
        }

    }

    private String[] changeToArrayVehiculo(Vehiculo v) {
        String[] dataVeh = {
            v.getVehiculo() + "",
            v.getPlaca(),
            v.getConductor().getName()+ " " +v.getConductor().getLastname()
        };
        return dataVeh;
    }

    private void clear() {
        btnDelete.setEnabled(false);
        btnRefresh.setEnabled(false);
        txtPlaca.setText("");
        txtUnidad.setText("");
        txtAnio.setText("");
        txtNumMot.setText("");
        txtnumCha.setText("");
        txtRegMun.setText("");
        txtSoat.setText("");
        lblImage.setIcon(null);
    }

    private void delete() {
        int rowSelected = tblVehiculo.getSelectedRow();
        clear();
        if (Principal.listVehiculos.deleteVehiculo(tblVehiculo.getValueAt(rowSelected, 1)+"")) {
            modelTableVehiculo.removeRow(rowSelected);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar la Vehículo");
        }
    }

    private void add() {
        String placa = txtPlaca.getText(),
                numMoto = txtNumMot.getText(),
                soat = txtSoat.getText(),
                numChasis = txtnumCha.getText();
        int unidad, anio, regis;
        try {
            unidad = Integer.parseInt(txtUnidad.getText());
        } catch (NumberFormatException e) {
            unidad = 0;
        }
        try {
            anio = Integer.parseInt(txtAnio.getText());
        } catch (NumberFormatException e) {
            anio = 0;
        }
        try {
            regis = Integer.parseInt(txtRegMun.getText());
        } catch (NumberFormatException e) {
            regis = 0;
        }
        
        Vehiculo v = new Vehiculo(placa,
                Principal.listZonas.getZonaById(cbxZona.getSelectedIndex() + 1),
                Principal.listPerson.getPersonByPerson(cbxConductor.getSelectedItem() + ""),
                Principal.listPerson.getPersonByPerson(cbxPropietario.getSelectedItem() + ""),
                unidad,
                Principal.listModels.getModeloById(cbxModelo.getSelectedIndex() + 1),
                anio,
                numMoto,
                numChasis,
                regis, soat, Principal.listCodesTaxy.getCodesTaxyById("AC"),"");
        Principal.listVehiculos.addNewVehiculo(v);
        modelTableVehiculo.addRow(changeToArrayVehiculo(v));
        clear();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblVehiculo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUnidad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtAnio = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNumMot = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtnumCha = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtRegMun = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();
        btnLoadImage = new javax.swing.JButton();
        cbxZona = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        cbxConductor = new javax.swing.JComboBox();
        cbxPropietario = new javax.swing.JComboBox();
        cbxModelo = new javax.swing.JComboBox();
        txtSoat = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Personas");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/persons.png")).getImage());
        setResizable(false);

        tblVehiculo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Unidad", "Placa", "Conductor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVehiculo.setToolTipText("");
        tblVehiculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblVehiculoMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblVehiculo);
        if (tblVehiculo.getColumnModel().getColumnCount() > 0) {
            tblVehiculo.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblVehiculo.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblVehiculo.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Vehiculos");

        jLabel2.setText("Placa:");

        txtPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlacaActionPerformed(evt);
            }
        });
        txtPlaca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPlacaFocusLost(evt);
            }
        });
        txtPlaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPlacaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPlacaKeyTyped(evt);
            }
        });

        jLabel3.setText("Conductor:");

        jLabel4.setText("Propietario:");

        jLabel5.setText("Unidad");

        txtUnidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUnidadKeyTyped(evt);
            }
        });

        jLabel6.setText("Modelo:");

        jLabel7.setText("Año:");

        txtAnio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnioKeyTyped(evt);
            }
        });

        jLabel8.setText("Número de motor:");

        txtNumMot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumMotKeyTyped(evt);
            }
        });

        jLabel9.setText("Número de chasis:");

        jLabel10.setText("Registro Municipal");

        txtRegMun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRegMunKeyTyped(evt);
            }
        });

        jLabel11.setText("Soat");

        lblImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnLoadImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/cargar.png"))); // NOI18N
        btnLoadImage.setText("Cargar Foto");
        btnLoadImage.setEnabled(false);

        jLabel12.setText("Zona: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtRegMun, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(txtSoat)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLoadImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumMot, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAnio, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(txtnumCha)
                            .addComponent(cbxConductor, 0, 227, Short.MAX_VALUE)
                            .addComponent(cbxPropietario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(cbxModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbxZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbxConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbxPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbxModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtNumMot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnumCha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtRegMun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLoadImage)
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/actualizar.png"))); // NOI18N
        btnRefresh.setToolTipText("Actualizar");
        btnRefresh.setEnabled(false);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/agregar persona.png"))); // NOI18N
        btnAdd.setToolTipText("Agregar Persona");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/eliminar persona.png"))); // NOI18N
        btnDelete.setToolTipText("Eliminar Persona");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/limpiar.png"))); // NOI18N
        btnClear.setToolTipText("Limpiar Campos");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(313, 313, 313))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblVehiculoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVehiculoMousePressed
        int intClicks = evt.getClickCount();
        if (intClicks == 1) {
            int rowSelected = tblVehiculo.getSelectedRow();
            String placa = tblVehiculo.getValueAt(rowSelected, 1) + "";

            Vehiculo v = Principal.listVehiculos.getVehiculoById(placa);
            txtPlaca.setText(v.getPlaca());
            cbxZona.setSelectedIndex(v.getZona().getIdZona() - 1);
            cbxConductor.setSelectedItem(v.getConductor().getLastname() + " " + v.getConductor().getName());
            cbxPropietario.setSelectedItem(v.getPropietario().getLastname() + " " + v.getPropietario().getName());
            txtUnidad.setText(v.getVehiculo() + "");
            cbxModelo.setSelectedItem(v.getModelo().getModeloVehiculo());
            txtAnio.setText(v.getYear() + "");
            txtNumMot.setText(v.getNumMotor());
            txtnumCha.setText(v.getNumChasis());
            txtRegMun.setText(v.getRegMunicipal() + "");
            txtSoat.setText(v.getSoat() + "");
            btnRefresh.setEnabled(true);
            btnDelete.setEnabled(true);
        }
    }//GEN-LAST:event_tblVehiculoMousePressed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        add();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        int rowSelected = tblVehiculo.getSelectedRow();
        String placa = txtPlaca.getText(),
                numMoto = txtNumMot.getText(),
                soat = txtSoat.getText(),
                numChasis = txtnumCha.getText();
        int unidad, anio, regis;
        try {
            unidad = Integer.parseInt(txtUnidad.getText());
        } catch (NumberFormatException e) {
            unidad = 0;
        }
        try {
            anio = Integer.parseInt(txtAnio.getText());
        } catch (NumberFormatException e) {
            anio = 0;
        }
        try {
            regis = Integer.parseInt(txtRegMun.getText());
        } catch (NumberFormatException e) {
            regis = 0;
        }

        Vehiculo v = new Vehiculo(placa,
                Principal.listZonas.getZonaById(cbxZona.getSelectedIndex() + 1),
                Principal.listPerson.getPersonByPerson(cbxConductor.getSelectedItem() + ""),
                Principal.listPerson.getPersonByPerson(cbxPropietario.getSelectedItem() + ""),
                unidad,
                Principal.listModels.getModeloById(cbxModelo.getSelectedIndex() + 1),
                anio,
                numMoto,
                numChasis,
                regis, soat, Principal.listCodesTaxy.getCodesTaxyById("AC"),"");

        if (Principal.listVehiculos.updateVehiculo(v, tblVehiculo.getValueAt(rowSelected, 1).toString())) {
            modelTableVehiculo.setValueAt(unidad, rowSelected, 0);
            modelTableVehiculo.setValueAt(placa, rowSelected, 1);
            modelTableVehiculo.setValueAt(cbxConductor.getSelectedItem() + "", rowSelected, 2);
            clear();
            JOptionPane.showMessageDialog(this, "Datos Actualizados Correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualiar la Información", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlacaActionPerformed
//        if (!Functions.validateCedula(txtPlaca.getText())) {
//            JOptionPane.showMessageDialog(this, "El Numero de Cedula Es Invalido", "ERROR", JOptionPane.ERROR_MESSAGE);
//            isValidCedula = false;
//        } else {
//            isValidCedula = true;
//        }
    }//GEN-LAST:event_txtPlacaActionPerformed

    private void txtUnidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnidadKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtUnidadKeyTyped

    private void txtPlacaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlacaKeyTyped

    }//GEN-LAST:event_txtPlacaKeyTyped

    private void txtNumMotKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumMotKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtNumMotKeyTyped

    private void txtPlacaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlacaKeyPressed

    }//GEN-LAST:event_txtPlacaKeyPressed

    private void txtPlacaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlacaFocusLost
//        if (!Functions.validateCedula(txtPlaca.getText())) {
//            JOptionPane.showMessageDialog(this, "El Numero de Cedula Es Invalido", "ERROR", JOptionPane.ERROR_MESSAGE);
//            isValidCedula = false;
//        } else {
//            isValidCedula = true;
//        }
    }//GEN-LAST:event_txtPlacaFocusLost

    private void txtAnioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnioKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtAnioKeyTyped

    private void txtRegMunKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegMunKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && !Character.isISOControl(evt.getKeyChar())) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtRegMunKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(FrameVehiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameVehiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameVehiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameVehiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameVehiculos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnLoadImage;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox cbxConductor;
    private javax.swing.JComboBox cbxModelo;
    private javax.swing.JComboBox cbxPropietario;
    private javax.swing.JComboBox cbxZona;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTable tblVehiculo;
    private javax.swing.JTextField txtAnio;
    private javax.swing.JTextField txtNumMot;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JTextField txtRegMun;
    private javax.swing.JTextField txtSoat;
    private javax.swing.JTextField txtUnidad;
    private javax.swing.JTextField txtnumCha;
    // End of variables declaration//GEN-END:variables
}
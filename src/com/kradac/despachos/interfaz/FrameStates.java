/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kradac.despachos.interfaz;

import com.kradac.despachos.administration.CodesTaxy;
import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author KRC
 */
public final class FrameStates extends javax.swing.JFrame {

    private String etiqueta;
    private Color col = Color.BLUE;
    private Color color = Color.BLUE;
    private CodesTaxy ct;

    /**
     * Creates new form FrameStates
     */
    public FrameStates() {
        initComponents();
        loadComboStates();
        btnEli.setVisible(false);
    }

    public void loadComboStates() {
        cbxStateVeh.setModel(new javax.swing.DefaultComboBoxModel(Principal.etiquetaStateVeh.toArray()));
    }

    public void reloadComboStates() {
        Principal.codeStateVeh = Principal.listCodesTaxy.getIdCodigo();
        Principal.etiquetaStateVeh = Principal.listCodesTaxy.getEtiqueta();
        Principal.colorStateVeh = Principal.listCodesTaxy.getColor();

        Principal.loadComboStates();
        loadComboStates();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblCod = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtEtiqueta = new javax.swing.JTextField();
        lblCod1 = new javax.swing.JLabel();
        lblCod2 = new javax.swing.JLabel();
        txtColor = new javax.swing.JTextField();
        btnSeleccionar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        cbxStateVeh = new javax.swing.JComboBox();
        btnEli = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administrar Estados de vehículos");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Estados");

        lblCod.setText("Código:");

        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });

        txtEtiqueta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEtiquetaFocusLost(evt);
            }
        });

        lblCod1.setText("Estado");

        lblCod2.setText("Color");

        txtColor.setEditable(false);
        txtColor.setBackground(new java.awt.Color(51, 255, 51));

        btnSeleccionar.setText("Seleccionar");
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/salir.png"))); // NOI18N
        btnCancelar.setText("Cerrar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        cbxStateVeh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxStateVehActionPerformed(evt);
            }
        });

        btnEli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/eliminar.png"))); // NOI18N
        btnEli.setText("Eliminar");
        btnEli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(cbxStateVeh, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblCod2)
                        .addGap(18, 18, 18)
                        .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnSeleccionar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnEli, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCod)
                            .addComponent(lblCod1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(38, 38, 38))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(9, 9, 9)
                        .addComponent(cbxStateVeh, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(lblCod))
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCod1)
                            .addComponent(txtEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblCod2))
                            .addComponent(btnSeleccionar))
                        .addGap(36, 36, 36)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEli, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        txtCodigo.setText(txtCodigo.getText().toUpperCase());
    }//GEN-LAST:event_txtCodigoFocusLost

    private void txtEtiquetaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEtiquetaFocusLost
        txtEtiqueta.setText(txtEtiqueta.getText().toUpperCase());
    }//GEN-LAST:event_txtEtiquetaFocusLost

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed

        color = JColorChooser.showDialog(this, "Seleccione un color", col);
        if (color != null) {
            txtColor.setBackground(new Color(color.getRGB()));
        }

    }//GEN-LAST:event_btnSeleccionarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed


    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (!txtCodigo.getText().isEmpty()) {
            if (!txtEtiqueta.getText().isEmpty()) {
                if (btnGuardar.getText().equals("Guardar")) {
                    if (Principal.bd.crearCodigo(txtCodigo.getText(), txtEtiqueta.getText(), String.valueOf(txtColor.getBackground().getRGB()))) {
                        Principal.listCodesTaxy.addCodesTaxy(new CodesTaxy(txtCodigo.getText(), txtEtiqueta.getText(), Integer.parseInt(String.valueOf(txtColor.getBackground().getRGB()))));

                        reloadComboStates();

                        JOptionPane.showMessageDialog(this, "REGISTRO GUARDADO");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "NO SE PUDO GUARDAR EL REGISTRO");
                    }
                } else {
                    if (ct != null) {
                        if (Principal.bd.updateCodigo(ct.getIdCodigo(), txtCodigo.getText(), txtEtiqueta.getText(),
                                String.valueOf(txtColor.getBackground().getRGB()))) {

                            JOptionPane.showMessageDialog(this, "ESTADO ACTUALIZADO GUARDADO");

                            dispose();

                        } else {
                            JOptionPane.showMessageDialog(this, "NO SE PUDO ACTUALIZAR EL ESTADO");
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "DEBE ESPECIFICAR UNA ETIQUETA");
            }
        } else {
            JOptionPane.showMessageDialog(this, "DEBE ESPECIFICAR UN CODIGO");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cbxStateVehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxStateVehActionPerformed
        etiqueta = cbxStateVeh.getSelectedItem() + "";
        ct = Principal.listCodesTaxy.getCodeTaxyByEtiqueta(etiqueta);
        System.out.println(ct);
        if (ct != null) {
            txtCodigo.setText(ct.getIdCodigo());
            txtEtiqueta.setText(ct.getEtiqueta());
            col = new Color(ct.getColor());
            txtColor.setBackground(col);
        }
        btnGuardar.setText("Actualizar");
        btnEli.setVisible(true);
    }//GEN-LAST:event_cbxStateVehActionPerformed

    private void btnEliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliActionPerformed
        if (ct.getIdCodigo().equals("AC") || ct.getIdCodigo().equals("ASI") || ct.getIdCodigo().equals("OCU") || ct.getIdCodigo().equals("FIJ")
                || ct.getIdCodigo().equals("INI") || ct.getIdCodigo().equals("RD") || ct.getIdCodigo().equals("DES")) {
            JOptionPane.showMessageDialog(this, "No puede Eliminar los Codigos por Defecto", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int x = JOptionPane.showConfirmDialog(this, "SEGURO DESEA BORRAR EL ESTADO");
            if (x == 0) {
                if (Principal.bd.deleteState(ct.getIdCodigo())) {
                    /*Principal.listCodesTaxy.deleteCodesTaxy(ct.getIdCodigo());
                    reloadComboStates();*/
                    JOptionPane.showMessageDialog(this, "ESTADO BORRADO CORRECTAMENTE");
                } else {
                    JOptionPane.showMessageDialog(this, "NO SE PUDO ELIMINAR EL ESTADO");
                }
            }
        }
    }//GEN-LAST:event_btnEliActionPerformed

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
            java.util.logging.Logger.getLogger(FrameStates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameStates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameStates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameStates.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameStates().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEli;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JComboBox cbxStateVeh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblCod1;
    private javax.swing.JLabel lblCod2;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtEtiqueta;
    // End of variables declaration//GEN-END:variables
}

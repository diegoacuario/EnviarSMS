package com.kradac.despachos.interfaz;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConsultaClientes.java
 *
 * Created on 01/09/2010, 10:11:54 AM
 */
import com.kradac.despachos.administration.Client;
import com.kradac.despachos.administration.list.ListClients;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public class SearchClients extends javax.swing.JDialog {

    private static DefaultTableModel modelTableClients;

    /**
     * Creates new form ConsultaClientes
     */
    public SearchClients() {
        initComponents();
        this.setVisible(true);
        modelTableClients = (DefaultTableModel) tblClients.getModel();
        jtNombreCliente.requestFocus();
    }

    public void dispatchClient(int row) {
        int code = Integer.parseInt(tblClients.getValueAt(row, 1).toString());
        Client c = Principal.listClient.getClientByCode(code);

        Principal.modelTableByDispatch.insertRow(0, Principal.changeToArrayClient(c));
        Principal.tblByDispatch.setColumnSelectionInterval(6, 6);
        Principal.tblByDispatch.setRowSelectionInterval(0, 0);
        Principal.tblByDispatch.requestFocus();
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbDespachar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblClients = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jtNombreCliente = new javax.swing.JTextField();
        jbBuscar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Busqueda de Clientes");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/buscar.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jbDespachar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/up.png"))); // NOI18N
        jbDespachar.setText("Despachar");
        jbDespachar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDespacharActionPerformed(evt);
            }
        });

        tblClients.setAutoCreateRowSorter(true);
        tblClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Teléfono", "Código", "Nombre", "Dirección", "Barrio", "# Casa", "Nota"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClients.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblClients.getTableHeader().setReorderingAllowed(false);
        tblClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblClientsMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblClients);
        if (tblClients.getColumnModel().getColumnCount() > 0) {
            tblClients.getColumnModel().getColumn(0).setMinWidth(75);
            tblClients.getColumnModel().getColumn(0).setMaxWidth(75);
            tblClients.getColumnModel().getColumn(1).setMinWidth(50);
            tblClients.getColumnModel().getColumn(1).setMaxWidth(50);
            tblClients.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblClients.getColumnModel().getColumn(3).setPreferredWidth(250);
            tblClients.getColumnModel().getColumn(4).setMinWidth(75);
            tblClients.getColumnModel().getColumn(4).setMaxWidth(75);
            tblClients.getColumnModel().getColumn(5).setMinWidth(60);
            tblClients.getColumnModel().getColumn(5).setMaxWidth(60);
            tblClients.getColumnModel().getColumn(6).setMinWidth(100);
            tblClients.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        jLabel1.setText("Nombre:");

        jtNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtNombreClienteActionPerformed(evt);
            }
        });
        jtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtNombreClienteKeyPressed(evt);
            }
        });

        jbBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/buscar.png"))); // NOI18N
        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kradac/despachos/img/cancelar.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                        .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbDespachar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbDespachar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1024, 294));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtNombreClienteActionPerformed
        String nombre = jtNombreCliente.getText().toUpperCase();
        jtNombreCliente.setText(nombre);
        FindClientByName(nombre);
    }//GEN-LAST:event_jtNombreClienteActionPerformed

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        String nombre = jtNombreCliente.getText().toUpperCase();
        jtNombreCliente.setText(nombre);
        FindClientByName(nombre);
    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jtNombreClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtNombreClienteKeyPressed
        int cod = evt.getKeyCode();
        if (cod == 27) {
            this.dispose();
        }
    }//GEN-LAST:event_jtNombreClienteKeyPressed

    private void jbDespacharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDespacharActionPerformed
        try {
            int row = tblClients.getSelectedRow();
            dispatchClient(row);
        } catch (IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente para despacharlo...", "Error...", 0);
        }
    }//GEN-LAST:event_jbDespacharActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void tblClientsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientsMousePressed
        int intClicks = evt.getClickCount();
        if (intClicks == 2) {
            try {
                int row = tblClients.getSelectedRow();
                dispatchClient(row);
            } catch (IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente para despacharlo...", "Error...", 0);
            }
        }
    }//GEN-LAST:event_tblClientsMousePressed

    /**
     * Llena la tabla de los clientes buscados
     *
     * @param nombre
     */
    private void FindClientByName(String nombre) {
        clearTableClients();

        for (Client c : Principal.listClient.getClients()) {
            String client = c.getName() + " " + c.getLastname();
            if (client.toUpperCase().contains(nombre)) {
                String[] dataClient = {
                    c.getPhone(),
                    c.getCode() + "",
                    c.getName() + " " + c.getLastname(),
                    c.getDirection(),
                    c.getSector(),
                    c.getNumHouse() + "",
                    c.getReference()
                };
                modelTableClients.insertRow(0, dataClient);
            }
        }
    }

    private void clearTableClients() {
        int n_filas = tblClients.getRowCount();
        for (int i = 0; i < n_filas; i++) {
            modelTableClients.removeRow(0);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbDespachar;
    private javax.swing.JTextField jtNombreCliente;
    private static javax.swing.JTable tblClients;
    // End of variables declaration//GEN-END:variables
}
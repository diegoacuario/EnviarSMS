package com.kradac.despachos.interfaz;

import com.kradac.despachos.taximetro.ConexionBase;
import com.kradac.despachos.taximetro.DatoTaximetro;
import com.kradac.despachos.taximetro.Metodos;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class FrameTaximetro extends javax.swing.JDialog {

    boolean importa = false;
    Metodos m = new Metodos();

    public FrameTaximetro() {
        initComponents();
        jcbUnidades.setModel(new DefaultComboBoxModel(m.listarUnidades()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jcbUnidades = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Selector de Unidad USB");
        setResizable(false);

        jPanel1.setLayout(new java.awt.BorderLayout(0, 20));

        jButton2.setText("Terminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel5.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Seleccione unidad de memoria:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel5.add(jLabel1);

        jcbUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbUnidadesActionPerformed(evt);
            }
        });
        jPanel5.add(jcbUnidades);

        jPanel1.add(jPanel5, java.awt.BorderLayout.NORTH);
        jPanel5.getAccessibleContext().setAccessibleDescription("");

        jPanel6.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        jPanel3.setLayout(new java.awt.GridLayout(4, 1, 0, 20));

        jButton1.setText("Importar Datos");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton3.setText("Actualizar dispositivos");
        jButton3.setToolTipText("Hacer clic aquí o presionar F5");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);

        jButton4.setText("Borrar Archivos Válidos");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4);

        jButton5.setText("Borrar Todos");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5);

        jPanel6.add(jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(11);
        jTextArea1.setRows(5);
        jTextArea1.setTabSize(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de archivos válidos"));
        jScrollPane1.setViewportView(jTextArea1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel4);

        jPanel1.add(jPanel6, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(410, 355));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jcbUnidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbUnidadesActionPerformed

        String uniSel = jcbUnidades.getSelectedItem() + "";
        uniSel = uniSel.substring(uniSel.length() - 3, uniSel.length() - 2) + ":\\";

        jTextArea1.setText(m.listarArchivos(uniSel));

        if (!jTextArea1.getText().isEmpty()) {
            jButton1.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Unidad no contiene nombres de archivos válidos");
        }

    }//GEN-LAST:event_jcbUnidadesActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String uniSel = jcbUnidades.getSelectedItem() + "";
        uniSel = uniSel.substring(uniSel.length() - 3, uniSel.length() - 2) + ":\\";
        if (importa == false) {
            JOptionPane.showMessageDialog(rootPane, "No se han importado datos", "Aviso", 0);
        }
        int op = JOptionPane.showConfirmDialog(rootPane, "Seguro desea borrar los archivos\n"
                + "mostrados en la lista?", "Confirmar eliminación", 0);
        if (op == 0) {
            if (m.borrarArchivos(uniSel)) {
                jTextArea1.setText("");
                JOptionPane.showMessageDialog(rootPane, "Archivos borrados correctamente");
            } else {
                JOptionPane.showMessageDialog(rootPane, "No se pudo borrar algunos archivos");
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jcbUnidades.setModel(new DefaultComboBoxModel(m.listarUnidades()));        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int arVal = m.archivosValidos.size();
        if (arVal > 0) {
            int op = JOptionPane.showConfirmDialog(rootPane, "Se han encontrado " + arVal + " nombres de archivos válidos.\n"
                    + "Desea importar a la base de datos?", "Confirmar importación", 0);
            if (op == 0) {
                ConexionBase c = new ConexionBase();
                String tra1[] = m.transArchivosToTramas();
                String tra[] = m.filtraTramas(tra1);

                List<DatoTaximetro> dat = m.listarDatos(tra);
                int l[] = c.insertaVectorBase(dat);
                int numDat = l[0] + l[1] + l[2];
                if (l[0] > 0) {
                    JOptionPane.showMessageDialog(rootPane, l[0] + " datos importados correctamente");
                }
                if (l[1] > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Hubo problemas al insertar " + l[1] + " datos");
                }
                if (l[2] > 0) {
                    JOptionPane.showMessageDialog(rootPane, "En los " + numDat + " datos encontrados en los archivos,"
                            + "Se han encontrado " + l[2] + " datos duplicados,\nestos no serán importados");
                    importa = true;
                }
                c.CerrarConexion();

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "No se han encontrado nombres de archivos válidos");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String uniSel = jcbUnidades.getSelectedItem() + "";
        uniSel = uniSel.substring(uniSel.length() - 3, uniSel.length() - 2) + ":\\";
        int op = JOptionPane.showConfirmDialog(rootPane, "Seguro desea borrar todos los archivos y directorios vacíos", "Confirmar eliminación", 0);
        if (op == 0) {
            if (m.borrarArchivosTodo(uniSel)) {
                jTextArea1.setText("");
                JOptionPane.showMessageDialog(rootPane, "Archivos borrados correctamente");
            } else {
                JOptionPane.showMessageDialog(rootPane, "No se pudo borrar algunos archivos");
            }
        }

        m.borrarArchivosTodo(uniSel);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(FrameTaximetro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameTaximetro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameTaximetro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameTaximetro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrameTaximetro().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox jcbUnidades;
    // End of variables declaration//GEN-END:variables
}

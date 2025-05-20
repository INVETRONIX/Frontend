/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.project.frontend.SYSTEMcompras.interfaz;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.project.frontend.SYSTEMcompras.controller.ControllerCompra;
import com.project.frontend.SYSTEMcompras.model.Compra;
import com.project.frontend.core.BackendException;

/**
 *
 * @author sebastian
 */
public class HistorialComprasCliente extends javax.swing.JFrame {
    private ControllerCompra controller;
    private DefaultTableModel tableModel;
    /**
     * Creates new form HistorialComprasCliente
     */
    public HistorialComprasCliente() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.controller = new ControllerCompra();
        this.tableModel = (DefaultTableModel) tablaCompras.getModel();
        
        try {
            llenarTabla();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las compras: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }   

          // Configurar listener para selección de filas
        tablaCompras.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tablaCompras.getSelectedRow();
                if (selectedRow != -1) {
                    btnModificarCompra.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    
                // Cargar datos en los campos de texto
                    txtId.setText(tablaCompras.getValueAt(selectedRow, 0).toString());
                    txtFecha.setText(tablaCompras.getValueAt(selectedRow, 1).toString());
                    txtHora.setText(tablaCompras.getValueAt(selectedRow, 2).toString());
                    txtIdUsuario.setText(tablaCompras.getValueAt(selectedRow, 3).toString());
                } else {
                    btnModificarCompra.setEnabled(false);
                    btnEliminar.setEnabled(false);
                    limpiarCampos();
                }
            }
        });

        // Inicialmente deshabilitar botones
        btnModificarCompra.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
    
    private void llenarTabla() throws IOException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Fecha", "Hora", "ID Usuario", "Producto", "Total"});
        
        List<Compra> lista = controller.getAllCompras();
        if (lista == null || lista.isEmpty()) {
            tablaCompras.setModel(model);
            return;
        }

        DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        for (Compra compra : lista) {
            model.addRow(new Object[]{
                compra.getId(),
                compra.getFecha() != null ? compra.getFecha().format(fechaFormatter) : "",
                compra.getHora() != null ? compra.getHora().format(horaFormatter) : "",
                compra.getUsuario() != null ? compra.getUsuario().getId() : "",
                compra.getProducto() != null ? compra.getProducto().getNombre() : "",
                String.format("$%,.2f", compra.getTotal())
            });
        }
        
        tablaCompras.setModel(model);
    }
    
    private void limpiarCampos() {
        txtId.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        txtIdUsuario.setText("");
        
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        String idText = txtId.getText().trim();
        String fechaText = txtFecha.getText().trim();
        String horaText = txtHora.getText().trim();
        String idUsuarioText = txtIdUsuario.getText().trim();
        
        // Si todos los campos están vacíos, recargar tabla completa
        if (idText.isEmpty() && fechaText.isEmpty() && horaText.isEmpty() && idUsuarioText.isEmpty()) {
            try {
                llenarTabla();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar las compras: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        
        try {
            List<Compra> compras = null;
            
            // Prioridad: buscar por ID si está lleno
            if (!idText.isEmpty()) {
                try {
                    Long id = Long.parseLong(idText);
                    Optional<Compra> compra = controller.getCompraById(id);
                    if (compra.isPresent()) {
                        Compra c = compra.get();
                        DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
                        
                        String mensaje = String.format(
                            "ID: %d\nFecha: %s\nHora: %s\nUsuario ID: %d\nProducto: %s\nTotal: $%,.2f",
                            c.getId(),
                            c.getFecha() != null ? c.getFecha().format(fechaFormatter) : "N/A",
                            c.getHora() != null ? c.getHora().format(horaFormatter) : "N/A",
                            c.getUsuario() != null ? c.getUsuario().getId() : 0,
                            c.getProducto() != null ? c.getProducto().getNombre() : "N/A",
                            c.getTotal()
                        );
                        
                        JOptionPane.showMessageDialog(
                            this,
                            mensaje,
                            "Detalles de la Compra",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                            this,
                            "No se encontró ninguna compra con ese ID",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                    return;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                // Buscar por otros filtros
                LocalDate fecha = null;
                LocalTime hora = null;
                Long idUsuario = null;
                
                if (!fechaText.isEmpty()) {
                    try {
                        fecha = LocalDate.parse(fechaText, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    } catch (DateTimeParseException e) {
                        JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                if (!horaText.isEmpty()) {
                    try {
                        hora = LocalTime.parse(horaText, DateTimeFormatter.ofPattern("HH:mm"));
                    } catch (DateTimeParseException e) {
                        JOptionPane.showMessageDialog(this, "Formato de hora inválido. Use HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                if (!idUsuarioText.isEmpty()) {
                    try {
                        idUsuario = Long.parseLong(idUsuarioText);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Por favor ingrese un ID de usuario válido", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                compras = controller.findByFilters(fecha, idUsuario, hora);
            }
            
            // Mostrar resultados en la tabla
            if (compras != null && !compras.isEmpty()) {
                DefaultTableModel model = (DefaultTableModel) tablaCompras.getModel();
                model.setRowCount(0); // Limpiar tabla
                
                DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
                
                for (Compra compra : compras) {
                    model.addRow(new Object[]{
                        compra.getId(),
                        compra.getFecha() != null ? compra.getFecha().format(fechaFormatter) : "",
                        compra.getHora() != null ? compra.getHora().format(horaFormatter) : "",
                        compra.getUsuario() != null ? compra.getUsuario().getId() : "",
                        compra.getProducto() != null ? compra.getProducto().getNombre() : "",
                        String.format("$%,.2f", compra.getTotal())
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron compras con los criterios especificados", "Información", JOptionPane.INFORMATION_MESSAGE);
                DefaultTableModel model = (DefaultTableModel) tablaCompras.getModel();
                model.setRowCount(0); // Limpiar tabla si no hay resultados
            }
        } catch (IOException | BackendException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar compras: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tablaCompras.getSelectedRow();
        if (selectedRow != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea eliminar esta compra?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    String id = tablaCompras.getValueAt(selectedRow, 0).toString();
                    controller.deleteCompra(Long.parseLong(id));
                    
                    DefaultTableModel currentTableModel = (DefaultTableModel) tablaCompras.getModel();
                    currentTableModel.removeRow(selectedRow);
                    
                    JOptionPane.showMessageDialog(
                        this,
                        "Compra eliminada exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    limpiarCampos();
                    
                } catch (BackendException | NumberFormatException | IOException e) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar la compra: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    private void btnModificarCompraActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tablaCompras.getSelectedRow();
        if (selectedRow != -1) {
            String id = tablaCompras.getValueAt(selectedRow, 0).toString();
            ModificacionCompra ventanaModificar = new ModificacionCompra();
            ventanaModificar.setIdCompra(Long.parseLong(id));
            ventanaModificar.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una compra para modificar", "Error", JOptionPane.ERROR_MESSAGE);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCompras = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        txtIdUsuario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnModificarCompra = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));

        jLabel1.setFont(new java.awt.Font("Liberation Sans Narrow", 2, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Historial");

        jLabel3.setFont(new java.awt.Font("URW Bookman", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Aqui podrás ver todas tus acciones!!");

        tablaCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "IdUsuario", "Hora", "Fecha", "IdProducto", "Total"
            }
        ));
        jScrollPane1.setViewportView(tablaCompras);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        txtId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("URW Bookman", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Id");

        txtHora.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("URW Bookman", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Hora");

        jLabel6.setFont(new java.awt.Font("URW Bookman", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Fecha");

        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtIdUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("URW Bookman", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Id usuario");

        btnBuscar.setBackground(new java.awt.Color(51, 51, 255));
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(51, 51, 255));
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnModificarCompra.setBackground(new java.awt.Color(51, 51, 255));
        btnModificarCompra.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarCompra.setText("Modificar");
        btnModificarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCompraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4)
                            .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(btnVolver)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(433, 433, 433)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addComponent(jLabel3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57)
                .addComponent(btnVolver)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        
    }//GEN-LAST:event_btnVolverActionPerformed

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
            java.util.logging.Logger.getLogger(HistorialComprasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistorialComprasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistorialComprasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistorialComprasCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HistorialComprasCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificarCompra;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaCompras;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdUsuario;
    // End of variables declaration//GEN-END:variables
}

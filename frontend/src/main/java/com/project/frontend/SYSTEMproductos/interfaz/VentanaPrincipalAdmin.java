/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.project.frontend.SYSTEMproductos.interfaz;

import com.project.frontend.SYSTEMcompras.interfaz.HistorialComprasAdmin;
import com.project.frontend.SYSTEMgemini.interfaz.NotificacionesAdmin;
import com.project.frontend.SYSTEMlogin.data.TokenManager;
import com.project.frontend.SYSTEMlogin.interfaz.Login;
import com.project.frontend.SYSTEMproductos.controller.ControllerProducto;
import com.project.frontend.SYSTEMproductos.model.Producto;
import com.project.frontend.core.BackendException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author sebastian
 */
public class VentanaPrincipalAdmin extends JFrame {
    private ControllerProducto controller;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTable tablaProductos;
    private JTextField txtId, txtName, txtPrecio, txtStock;
    private JButton btnAgregarProducto, btnModificarProducto, btnEliminar, btnBuscar, btnCerrarSesion, btnNotificaciones, btnCompras;

    /**
     * Creates new form VentanaPrincipalAdmin
     */
    public VentanaPrincipalAdmin() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.controller = new ControllerProducto();
        this.tableModel = (DefaultTableModel) tablaProductos.getModel();
        try {
            llenarTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Agregar listener para la selección de filas en la tabla
        tablaProductos.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tablaProductos.getSelectedRow();
                    if (selectedRow != -1) {
                        // Habilitar botones cuando hay una fila seleccionada
                        btnModificarProducto.setEnabled(true);
                        btnEliminar.setEnabled(true);
                        
                        // Cargar los datos en los campos de texto
                        txtId.setText(tablaProductos.getValueAt(selectedRow, 0).toString());
                        txtName.setText(tablaProductos.getValueAt(selectedRow, 1).toString());
                        txtPrecio.setText(tablaProductos.getValueAt(selectedRow, 3).toString());
                        txtStock.setText(tablaProductos.getValueAt(selectedRow, 4).toString());
                    } else {
                        // Deshabilitar botones cuando no hay fila seleccionada
                        btnModificarProducto.setEnabled(false);
                        btnEliminar.setEnabled(false);
                        
                        // Limpiar los campos de texto
                        txtId.setText("");
                        txtName.setText("");
                        txtPrecio.setText("");
                        txtStock.setText("");
                    }
                }
            }
        });
        
        // Inicialmente deshabilitar los botones
        btnModificarProducto.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void llenarTabla() throws IOException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id", "Nombre", "Descripcion", "Precio", "Cantidad en stock"});
        List<Producto> lista= controller.getAllProductos();
        if(lista.isEmpty()){
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            model.addRow(new Object[]{
               lista.get(i).getId(),
               lista.get(i).getNombre(),
               lista.get(i).getDescripcion(),
               lista.get(i).getPrecio(),
               lista.get(i).getStock()
            });
        }
        
        tablaProductos.setModel(model);
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Panel de Administración");
        setSize(1200, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Panel izquierdo (logo y navegación)
        leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 122, 255));
        leftPanel.setPreferredSize(new Dimension(320, 700));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        JLabel lblLogo = new JLabel("Invetronix");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(lblLogo);
        leftPanel.add(Box.createVerticalStrut(40));

        btnNotificaciones = new JButton("Notificaciones");
        btnNotificaciones.setMaximumSize(new Dimension(260, 45));
        btnNotificaciones.setBackground(new Color(41, 128, 185));
        btnNotificaciones.setForeground(Color.WHITE);
        btnNotificaciones.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnNotificaciones.setFocusPainted(false);
        btnNotificaciones.setBorderPainted(false);
        btnNotificaciones.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNotificaciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNotificaciones.addActionListener(e -> btnNotificacionesActionPerformed(null));
        leftPanel.add(btnNotificaciones);
        leftPanel.add(Box.createVerticalStrut(20));

        btnCompras = new JButton("Historial de Compras");
        btnCompras.setMaximumSize(new Dimension(260, 45));
        btnCompras.setBackground(new Color(41, 128, 185));
        btnCompras.setForeground(Color.WHITE);
        btnCompras.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCompras.setFocusPainted(false);
        btnCompras.setBorderPainted(false);
        btnCompras.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCompras.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCompras.addActionListener(e -> btnCerrarSesion1ActionPerformed(null));
        leftPanel.add(btnCompras);
        leftPanel.add(Box.createVerticalStrut(20));

        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setMaximumSize(new Dimension(260, 45));
        btnCerrarSesion.setBackground(new Color(220, 53, 69));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrarSesion.addActionListener(e -> btnCerrarSesionActionPerformed(null));
        leftPanel.add(btnCerrarSesion);
        leftPanel.add(Box.createVerticalGlue());

        // Panel derecho (gestión de productos)
        rightPanel = new JPanel();
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel lblTitulo = new JLabel("Gestión de Productos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(33, 33, 33));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(lblTitulo);
        rightPanel.add(Box.createVerticalStrut(20));

        // Tabla de productos
        String[] columnNames = {"Id", "Nombre", "Descripción", "Precio", "Cantidad en stock"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tablaProductos = new JTable(tableModel);
        tablaProductos.setRowHeight(28);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        tablaProductos.setSelectionBackground(new Color(0, 122, 255));
        tablaProductos.setSelectionForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setPreferredSize(new Dimension(700, 250));
        rightPanel.add(scrollPane);
        rightPanel.add(Box.createVerticalStrut(25));

        // Panel de campos y acciones
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(2, 4, 20, 10));
        fieldsPanel.setBackground(new Color(245, 245, 245));

        txtId = new JTextField();
        txtId.setBorder(BorderFactory.createTitledBorder("ID"));
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtName = new JTextField();
        txtName.setBorder(BorderFactory.createTitledBorder("Nombre"));
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPrecio = new JTextField();
        txtPrecio.setBorder(BorderFactory.createTitledBorder("Precio"));
        txtPrecio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtStock = new JTextField();
        txtStock.setBorder(BorderFactory.createTitledBorder("Stock"));
        txtStock.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        fieldsPanel.add(txtId);
        fieldsPanel.add(txtName);
        fieldsPanel.add(txtPrecio);
        fieldsPanel.add(txtStock);

        rightPanel.add(fieldsPanel);
        rightPanel.add(Box.createVerticalStrut(20));

        // Panel de botones de acción
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonsPanel.setBackground(new Color(245, 245, 245));

        btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarProducto.setPreferredSize(new Dimension(180, 40));
        btnAgregarProducto.setBackground(new Color(41, 128, 185));
        btnAgregarProducto.setForeground(Color.BLACK);
        btnAgregarProducto.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnAgregarProducto.setFocusPainted(false);
        btnAgregarProducto.setBorderPainted(false);
        btnAgregarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregarProducto.addActionListener(e -> btnAgregarProductoActionPerformed(null));
        buttonsPanel.add(btnAgregarProducto);

        btnModificarProducto = new JButton("Modificar Producto");
        btnModificarProducto.setPreferredSize(new Dimension(180, 40));
        btnModificarProducto.setBackground(new Color(41, 128, 185));
        btnModificarProducto.setForeground(Color.BLUE);
        btnModificarProducto.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnModificarProducto.setFocusPainted(false);
        btnModificarProducto.setBorderPainted(false);
        btnModificarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnModificarProducto.setEnabled(false);
        btnModificarProducto.addActionListener(e -> btnModificarProductoActionPerformed(null));
        buttonsPanel.add(btnModificarProducto);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setPreferredSize(new Dimension(120, 40));
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.RED);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(e -> btnEliminarActionPerformed(null));
        buttonsPanel.add(btnEliminar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(120, 40));
        btnBuscar.setBackground(new Color(41, 128, 185));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> btnBuscarActionPerformed(null));
        buttonsPanel.add(btnBuscar);

        rightPanel.add(buttonsPanel);
        rightPanel.add(Box.createVerticalGlue());

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        AgregarProductoAdmin cambio=new AgregarProductoAdmin();
        cambio.setVisible(true);
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void btnModificarProductoActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tablaProductos.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Obtener los datos del producto seleccionado
                String id = tablaProductos.getValueAt(selectedRow, 0).toString();
                String nombre = tablaProductos.getValueAt(selectedRow, 1).toString();
                String descripcion = tablaProductos.getValueAt(selectedRow, 2).toString();
                String precio = tablaProductos.getValueAt(selectedRow, 3).toString();
                String stock = tablaProductos.getValueAt(selectedRow, 4).toString();
                
                // Abrir la ventana de modificación
                ModificarProductoAdmin modificarVentana = new ModificarProductoAdmin();
                
                // Establecer los datos en la ventana de modificación
                modificarVentana.txtId.setText(id);
                modificarVentana.txtNombre.setText(nombre);
                modificarVentana.txtDescripcion.setText(descripcion);
                modificarVentana.txtPrecio.setText(precio);
                modificarVentana.txtCantidadEnStock.setText(stock);
                
                // Mostrar la ventana de modificación
                modificarVentana.setVisible(true);
                
                // Cerrar esta ventana
                this.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al abrir la ventana de modificación: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }


    private void btnNotificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotificacionesActionPerformed
        NotificacionesAdmin cambio = new NotificacionesAdmin();
        cambio.setVisible(true);
    }//GEN-LAST:event_btnNotificacionesActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        Login cambio = new Login();
        cambio.setVisible(true);
        TokenManager.getInstance().clearToken();
        this.dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnCerrarSesion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesion1ActionPerformed
       HistorialComprasAdmin cambio = new HistorialComprasAdmin();
       cambio.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnCerrarSesion1ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        String idText = txtId.getText().trim();
        String nombre = txtName.getText().trim();
        String precioText = txtPrecio.getText().trim();
        String stockText = txtStock.getText().trim();

        // Si todos los campos están vacíos, recargar la tabla completa
        if (idText.isEmpty() && nombre.isEmpty() && precioText.isEmpty() && stockText.isEmpty()) {
            try {
                llenarTabla();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        try {
            List<Producto> productos = new java.util.ArrayList<>();

            // Prioridad: buscar por ID si está lleno
            if (!idText.isEmpty()) {
                try {
                    Long id = Long.parseLong(idText);
                    Optional<Producto> producto = controller.getProductoById(id);
                    if (producto.isPresent()) {
                         Producto p = producto.get();
                            String mensaje = String.format(
                                "ID: %d\nNombre: %s\nDescripción: %s\nPrecio: %.2f\nStock: %d",
                                p.getId(),
                                p.getNombre(),
                                p.getDescripcion(),
                                p.getPrecio(),
                                p.getStock()
                            );
                            JOptionPane.showMessageDialog(
                                this,
                                mensaje,
                                "Detalles del Producto",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                    } else {
                         JOptionPane.showMessageDialog(
                                this,
                                "No se encontró ningún producto con ese ID",
                                "Información",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                    }
                     // Si se buscó por ID, no continuar con otros filtros
                    return;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else { // Si ID está vacío, buscar por otros filtros
                // Usar findByFilters si hay más de un campo lleno (o uno solo que no sea ID)
                if (!nombre.isEmpty() || !precioText.isEmpty() || !stockText.isEmpty()) {
                    Double precio = null;
                    Integer stock = null;

                    if (!precioText.isEmpty()) {
                        try {
                            precio = Double.parseDouble(precioText);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Por favor ingrese un precio válido", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    if (!stockText.isEmpty()) {
                        try {
                            stock = Integer.parseInt(stockText);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Por favor ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    
                    // Llamar a findByFilters con los campos llenos
                    productos = controller.findByFilters(nombre.isEmpty() ? null : nombre, precio, stock);
                }
            }

            // Mostrar resultados en la tabla (solo si la búsqueda no fue por ID)
            if (!idText.isEmpty()) {
                 // Already handled with JOptionPane, do nothing here for table
            } else if (productos != null && !productos.isEmpty()) {
                DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
                model.setRowCount(0); // Limpiar la tabla
                
                for (Producto producto : productos) {
                    model.addRow(new Object[]{
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getStock()
                    });
                }
            } else if (!idText.isEmpty()){
                 // Handled by JOptionPane above
            }else {
                JOptionPane.showMessageDialog(this, "No se encontraron productos con los criterios especificados", "Información", JOptionPane.INFORMATION_MESSAGE);
                 DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
                 model.setRowCount(0); // Limpiar la tabla si no hay resultados
            }
        } catch (IOException | BackendException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tablaProductos.getSelectedRow();
        if (selectedRow != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    // Obtener el ID del producto seleccionado
                    String id = tablaProductos.getValueAt(selectedRow, 0).toString();
                    
                    // Eliminar el producto usando el controlador
                    controller.deleteProducto(Long.parseLong(id));
                    
                    // Eliminar la fila de la tabla (usando el modelo actual de la tabla)
                    DefaultTableModel currentTableModel = (DefaultTableModel) tablaProductos.getModel();
                    currentTableModel.removeRow(selectedRow);
                    
                    // Mostrar mensaje de éxito
                    JOptionPane.showMessageDialog(
                        this,
                        "Producto eliminado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    // Limpiar los campos de texto
                    txtId.setText("");
                    txtName.setText("");
                    txtPrecio.setText("");
                    txtStock.setText("");
                    
                } catch (BackendException e) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar el producto: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } catch (NumberFormatException e) {
                    // Este catch no debería ser alcanzado si el ID se obtiene de la tabla,
                    // pero por si acaso, mejoramos el mensaje.
                    JOptionPane.showMessageDialog(
                        this,
                        "Error interno: formato de ID inválido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar el producto: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

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
            java.util.logging.Logger.getLogger(VentanaPrincipalAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipalAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipalAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipalAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipalAdmin().setVisible(true);
            }
        });
    }
}

package com.project.frontend.SYSTEMcompras.interfaz;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.project.frontend.SYSTEMcompras.controller.ControllerCompra;
import com.project.frontend.SYSTEMcompras.model.Compra;
import com.project.frontend.SYSTEMproductos.interfaz.VentanaPrincipalAdmin;
import com.project.frontend.core.BackendException;
import java.awt.*;

public class HistorialComprasAdmin extends JFrame {
    private ControllerCompra controller;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTable tablaCompras;
    private JTextField txtId, txtFecha, txtHora, txtIdUsuario;
    private JButton btnBuscar, btnVolver, btnModificarCompra, btnEliminar;
    
    public HistorialComprasAdmin() {
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
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Historial de Compras - Administrador");
        setSize(1200, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Panel izquierdo (logo y navegación)
        leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 122, 255));
        leftPanel.setPreferredSize(new Dimension(400, 700));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel lblLogo = new JLabel("Invetronix");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(lblLogo);
        leftPanel.add(Box.createVerticalStrut(40));

        // Panel de filtros
        JPanel filtersPanel = new JPanel();
        filtersPanel.setLayout(new BoxLayout(filtersPanel, BoxLayout.Y_AXIS));
        filtersPanel.setBackground(new Color(0, 122, 255));
        filtersPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        filtersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título de filtros
        JLabel lblFiltrosTitulo = new JLabel("Filtros de Búsqueda");
        lblFiltrosTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblFiltrosTitulo.setForeground(Color.WHITE);
        lblFiltrosTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        filtersPanel.add(lblFiltrosTitulo);
        filtersPanel.add(Box.createVerticalStrut(30));

        // Panel para ID
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.Y_AXIS));
        idPanel.setBackground(new Color(0, 122, 255));
        idPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblId = new JLabel("ID de Compra");
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblId.setForeground(Color.WHITE);
        lblId.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtId = createTextField("Ejemplo: 123");
        idPanel.add(lblId);
        idPanel.add(Box.createVerticalStrut(7));
        idPanel.add(txtId);
        filtersPanel.add(idPanel);
        filtersPanel.add(Box.createVerticalStrut(18));

        // Panel para Fecha
        JPanel fechaPanel = new JPanel();
        fechaPanel.setLayout(new BoxLayout(fechaPanel, BoxLayout.Y_AXIS));
        fechaPanel.setBackground(new Color(0, 122, 255));
        fechaPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblFecha = new JLabel("Fecha");
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtFecha = createTextField("Formato: dd/MM/yyyy");
        fechaPanel.add(lblFecha);
        fechaPanel.add(Box.createVerticalStrut(7));
        fechaPanel.add(txtFecha);
        filtersPanel.add(fechaPanel);
        filtersPanel.add(Box.createVerticalStrut(18));

        // Panel para Hora
        JPanel horaPanel = new JPanel();
        horaPanel.setLayout(new BoxLayout(horaPanel, BoxLayout.Y_AXIS));
        horaPanel.setBackground(new Color(0, 122, 255));
        horaPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblHora = new JLabel("Hora");
        lblHora.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblHora.setForeground(Color.WHITE);
        lblHora.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtHora = createTextField("Formato: HH:mm (ejemplo: 14:30)");
        horaPanel.add(lblHora);
        horaPanel.add(Box.createVerticalStrut(7));
        horaPanel.add(txtHora);
        filtersPanel.add(horaPanel);
        filtersPanel.add(Box.createVerticalStrut(18));

        // Panel para ID Usuario
        JPanel usuarioPanel = new JPanel();
        usuarioPanel.setLayout(new BoxLayout(usuarioPanel, BoxLayout.Y_AXIS));
        usuarioPanel.setBackground(new Color(0, 122, 255));
        usuarioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblUsuario = new JLabel("ID de Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtIdUsuario = createTextField("Ejemplo: 456");
        usuarioPanel.add(lblUsuario);
        usuarioPanel.add(Box.createVerticalStrut(7));
        usuarioPanel.add(txtIdUsuario);
        filtersPanel.add(usuarioPanel);
        filtersPanel.add(Box.createVerticalStrut(30));

        // Panel de botones en una sola fila, bien centrados y espaciados
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setBackground(new Color(0, 122, 255));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        btnBuscar = new JButton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(150, 40));
        btnBuscar.setMaximumSize(new Dimension(150, 40));
        btnBuscar.setBackground(new Color(41, 128, 185));
        btnBuscar.setForeground(Color.BLUE);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> btnBuscarActionPerformed(null));
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(btnBuscar);
        buttonsPanel.add(Box.createRigidArea(new Dimension(15, 0)));

        btnModificarCompra = new JButton("Modificar");
        btnModificarCompra.setPreferredSize(new Dimension(150, 40));
        btnModificarCompra.setMaximumSize(new Dimension(150, 40));
        btnModificarCompra.setBackground(new Color(46, 204, 113));
        btnModificarCompra.setForeground(Color.WHITE);
        btnModificarCompra.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnModificarCompra.setFocusPainted(false);
        btnModificarCompra.setBorderPainted(false);
        btnModificarCompra.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnModificarCompra.addActionListener(e -> btnModificarCompraActionPerformed(null));
        buttonsPanel.add(btnModificarCompra);
        buttonsPanel.add(Box.createRigidArea(new Dimension(15, 0)));

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setPreferredSize(new Dimension(150, 40));
        btnEliminar.setMaximumSize(new Dimension(150, 40));
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.addActionListener(e -> btnEliminarActionPerformed(null));
        buttonsPanel.add(btnEliminar);
        buttonsPanel.add(Box.createRigidArea(new Dimension(15, 0)));

        btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(150, 40));
        btnVolver.setMaximumSize(new Dimension(150, 40));
        btnVolver.setBackground(new Color(149, 165, 166));
        btnVolver.setForeground(Color.RED);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> btnVolverActionPerformed(null));
        buttonsPanel.add(btnVolver);
        buttonsPanel.add(Box.createHorizontalGlue());

        filtersPanel.add(buttonsPanel);
        filtersPanel.add(Box.createVerticalGlue());

        leftPanel.add(filtersPanel);
        leftPanel.add(Box.createVerticalGlue());

        // Panel derecho (tabla de compras)
        rightPanel = new JPanel();
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Título
        JLabel lblTitulo = new JLabel("Historial de Compras");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(33, 33, 33));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Administración de compras del sistema");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(100, 100, 100));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tabla de compras
        String[] columnNames = {"ID", "Fecha", "Hora", "ID Usuario", "Producto", "Total"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tablaCompras = new JTable(tableModel);
        tablaCompras.setRowHeight(28);
        tablaCompras.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaCompras.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaCompras.setSelectionBackground(new Color(0, 122, 255));
        tablaCompras.setSelectionForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(tablaCompras);
        scrollPane.setPreferredSize(new Dimension(700, 400));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        rightPanel.add(lblTitulo);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(lblSubtitulo);
        rightPanel.add(Box.createVerticalStrut(30));
        rightPanel.add(scrollPane);
        rightPanel.add(Box.createVerticalGlue());

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField();
        Dimension size = new Dimension(300, 40);
        textField.setPreferredSize(size);
        textField.setMaximumSize(size);
        textField.setMinimumSize(size);
        textField.setBackground(Color.WHITE);
        textField.setForeground(new Color(33, 33, 33));
        textField.setCaretColor(new Color(0, 122, 255));
        textField.setSelectedTextColor(Color.WHITE);
        textField.setSelectionColor(new Color(0, 122, 255));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.putClientProperty("JTextField.placeholderText", placeholder);
        return textField;
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
                        if (!horaText.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                            JOptionPane.showMessageDialog(this, "Formato de hora inválido. Use HH:mm (ejemplo: 14:30)", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        hora = LocalTime.parse(horaText, DateTimeFormatter.ofPattern("HH:mm"))
                                      .withSecond(0)
                                      .withNano(0);
                    } catch (DateTimeParseException e) {
                        JOptionPane.showMessageDialog(this, "Formato de hora inválido. Use HH:mm (ejemplo: 14:30)", "Error", JOptionPane.ERROR_MESSAGE);
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
            
            if (compras != null && !compras.isEmpty()) {
                DefaultTableModel model = (DefaultTableModel) tablaCompras.getModel();
                model.setRowCount(0);
                
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
                model.setRowCount(0);
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

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaPrincipalAdmin cambio = new VentanaPrincipalAdmin();
        cambio.setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            new HistorialComprasAdmin().setVisible(true);
        });
    }
} 
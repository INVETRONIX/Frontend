package com.project.frontend.comprasSystem.vistas;

import com.formdev.flatlaf.FlatDarkLaf;
import com.project.frontend.comprasSystem.models.Carrito;
import com.project.frontend.comprasSystem.models.ItemCarrito;
import com.project.frontend.comprasSystem.services.CarritoService;
import com.project.frontend.comprasSystem.services.CompraService;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class CarritoVista extends JFrame {
    private JTable tablaCarrito;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;
    private JButton btnVaciar;
    private JButton btnComprar;
    private JLabel lblTotal;
    private Carrito carrito;
    private ClienteVista clienteVista;
    private CompraService compraService;
    private final CarritoService carritoService;

    public CarritoVista(ClienteVista clienteVista, Carrito carrito) {
        this.clienteVista = clienteVista;
        this.carrito = carrito;
        this.compraService = new CompraService();
        this.carritoService = CarritoService.getInstance();
        initComponents();
        cargarItems();
    }

    private void initComponents() {
        setTitle("Carrito de Compras");
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(33, 33, 33));

        // Header
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(30, 30, 30));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));
        JLabel lblTitulo = new JLabel("Carrito de Compras");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        panelHeader.add(lblTitulo, BorderLayout.WEST);
        add(panelHeader, BorderLayout.NORTH);

        // Tabla del carrito
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Solo el botón de eliminar es editable
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Producto");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Precio Unit.");
        modeloTabla.addColumn("Subtotal");
        modeloTabla.addColumn(""); // Columna para el botón eliminar

        tablaCarrito = new JTable(modeloTabla) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)) {
                    c.setBackground(new Color(0, 200, 180));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(45, 45, 45) : new Color(40, 40, 40));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        };
        tablaCarrito.setRowHeight(40);
        tablaCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCarrito.setShowGrid(false);
        tablaCarrito.setIntercellSpacing(new Dimension(0, 0));
        tablaCarrito.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JTableHeader header = tablaCarrito.getTableHeader();
        header.setBackground(new Color(0, 200, 180));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(tablaCarrito);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        scrollPane.getViewport().setBackground(new Color(33, 33, 33));
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con total y botones
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(30, 30, 30));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Total
        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTotal.setForeground(Color.WHITE);
        panelInferior.add(lblTotal, BorderLayout.WEST);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(new Color(30, 30, 30));

        btnEliminar = new JButton("🗑️ Eliminar");
        btnVaciar = new JButton("🗑️ Vaciar Carrito");
        btnComprar = new JButton("💳 Comprar");

        // Estilo de botones
        for (JButton btn : new JButton[]{btnEliminar, btnVaciar, btnComprar}) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        btnEliminar.setBackground(new Color(244, 67, 54));
        btnVaciar.setBackground(new Color(244, 67, 54));
        btnComprar.setBackground(new Color(76, 175, 80));

        btnEliminar.setForeground(Color.WHITE);
        btnVaciar.setForeground(Color.WHITE);
        btnComprar.setForeground(Color.WHITE);

        panelBotones.add(btnEliminar);
        panelBotones.add(btnVaciar);
        panelBotones.add(btnComprar);
        panelInferior.add(panelBotones, BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);

        // Acciones de los botones
        btnEliminar.addActionListener(e -> eliminarItemSeleccionado());
        btnVaciar.addActionListener(e -> vaciarCarrito());
        btnComprar.addActionListener(e -> realizarCompra());

        // Render y editor para el botón "Eliminar"
        tablaCarrito.getColumnModel().getColumn(5).setCellRenderer((table, value, isSelected, hasFocus, row, col) -> {
            JButton btn = new JButton("🗑️");
            btn.setBackground(new Color(244, 67, 54));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return btn;
        });

        tablaCarrito.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            private JButton btn = new JButton("🗑️");
            private int row;
            {
                btn.setBackground(new Color(244, 67, 54));
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btn.addActionListener(e -> {
                    eliminarItem(row);
                    fireEditingStopped();
                });
            }
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                this.row = row;
                return btn;
            }
        });
    }

    private void cargarItems() {
        modeloTabla.setRowCount(0);
        double total = 0;
        for (ItemCarrito item : carrito.getItems()) {
            double subtotal = item.getCantidad() * item.getProducto().getPrecio();
            total += subtotal;
            modeloTabla.addRow(new Object[]{
                item.getProducto().getId(),
                item.getProducto().getNombre(),
                item.getCantidad(),
                String.format("$%.2f", item.getProducto().getPrecio()),
                String.format("$%.2f", subtotal),
                ""
            });
        }
        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    private void eliminarItemSeleccionado() {
        int row = tablaCarrito.getSelectedRow();
        if (row != -1) {
            eliminarItem(row);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un item para eliminar", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarItem(int row) {
        Long idProducto = (Long) modeloTabla.getValueAt(row, 0);
        carrito.removeItem(idProducto);
        carritoService.guardarCarrito();
        cargarItems();
    }

    private void vaciarCarrito() {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de que desea vaciar el carrito?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            carrito.vaciar();
            carritoService.guardarCarrito();
            cargarItems();
        }
    }

    private void realizarCompra() {
        if (carrito.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            compraService.realizarCompra(carrito);
            JOptionPane.showMessageDialog(this, "¡Compra realizada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            carrito.vaciar();
            carritoService.guardarCarrito();
            cargarItems();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al realizar la compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 
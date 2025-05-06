package com.project.frontend.comprasSystem.vistas;

import com.formdev.flatlaf.FlatDarkLaf;
import com.project.frontend.comprasSystem.models.Carrito;
import com.project.frontend.comprasSystem.models.ItemCarrito;
import com.project.frontend.comprasSystem.models.Producto;
import com.project.frontend.comprasSystem.services.CarritoService;
import com.project.frontend.comprasSystem.services.ProductoService;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class ClienteVista extends JFrame {
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JButton btnVerCarrito;
    private JButton btnAñadir;
    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltro;
    private JButton btnBuscar;
    private JButton btnMostrarTodos;
    private Carrito carrito;
    private ProductoService productoService;
    private List<Producto> productosOriginales;

    public ClienteVista() {
        try {
            this.carrito = CarritoService.cargarCarrito();
        } catch (Exception e) {
            this.carrito = new Carrito();
        }
        this.productoService = new ProductoService();
        initComponents();
        cargarProductos();
    }

    private void initComponents() {
        setTitle("Catálogo de Productos");
        setSize(1200, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(33, 33, 33));

        // Header
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(30, 30, 30));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));
        
        JLabel lblTitulo = new JLabel("Catálogo de Productos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        panelHeader.add(lblTitulo, BorderLayout.WEST);

        // Panel de búsqueda y filtros
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.X_AXIS));
        panelBusqueda.setBackground(new Color(30, 30, 30));

        txtBuscar = new JTextField(15);
        txtBuscar.setMaximumSize(new Dimension(160, 35));
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBackground(new Color(45, 45, 45));
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        cmbFiltro = new JComboBox<>(new String[]{"Todos", "Nombre", "Categoría", "Precio"});
        cmbFiltro.setMaximumSize(new Dimension(110, 35));
        cmbFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbFiltro.setBackground(new Color(45, 45, 45));
        cmbFiltro.setForeground(Color.WHITE);
        cmbFiltro.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));

        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBuscar.setBackground(new Color(0, 200, 180));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorder(BorderFactory.createEmptyBorder(7, 12, 7, 12));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnMostrarTodos = new JButton("🔄 Mostrar todos");
        btnMostrarTodos.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnMostrarTodos.setBackground(new Color(33, 150, 243)); // Azul llamativo
        btnMostrarTodos.setForeground(Color.WHITE);
        btnMostrarTodos.setFocusPainted(false);
        btnMostrarTodos.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
        btnMostrarTodos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMostrarTodos.setToolTipText("Haz clic para ver todos los productos");

        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(Box.createHorizontalStrut(8));
        panelBusqueda.add(cmbFiltro);
        panelBusqueda.add(Box.createHorizontalStrut(12));
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(Box.createHorizontalStrut(10));
        panelBusqueda.add(btnMostrarTodos);
        panelHeader.add(panelBusqueda, BorderLayout.CENTER);

        // Botón Ver Carrito
        btnVerCarrito = new JButton("🛒 Ver Carrito");
        btnVerCarrito.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVerCarrito.setBackground(new Color(0, 200, 180));
        btnVerCarrito.setForeground(Color.WHITE);
        btnVerCarrito.setFocusPainted(false);
        btnVerCarrito.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnVerCarrito.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelHeader.add(btnVerCarrito, BorderLayout.EAST);

        add(panelHeader, BorderLayout.NORTH);

        // Tabla de productos
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Solo el botón de añadir es editable
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Descripción");
        modeloTabla.addColumn("Categoría");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn(""); // Columna para el botón añadir

        tablaProductos = new JTable(modeloTabla) {
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
        tablaProductos.setRowHeight(40);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setShowGrid(false);
        tablaProductos.setIntercellSpacing(new Dimension(0, 0));
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JTableHeader header = tablaProductos.getTableHeader();
        header.setBackground(new Color(0, 200, 180));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        scrollPane.getViewport().setBackground(new Color(33, 33, 33));
        add(scrollPane, BorderLayout.CENTER);

        // Render y editor para el botón "Añadir"
        tablaProductos.getColumnModel().getColumn(5).setCellRenderer((table, value, isSelected, hasFocus, row, col) -> {
            JButton btn = new JButton("➕");
            btn.setBackground(new Color(76, 175, 80));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return btn;
        });

        tablaProductos.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            private JButton btn = new JButton("➕");
            private int row;
            {
                btn.setBackground(new Color(76, 175, 80));
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btn.addActionListener(e -> {
                    añadirAlCarrito(row);
                    fireEditingStopped();
                });
            }
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                this.row = row;
                return btn;
            }
        });

        // Acciones
        btnVerCarrito.addActionListener(e -> abrirCarrito());
        btnBuscar.addActionListener(e -> filtrarProductos());
        btnMostrarTodos.addActionListener(e -> cargarProductos());
        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrarProductos(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrarProductos(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrarProductos(); }
        });
    }

    private void cargarProductos() {
        try {
            List<Producto> productos = productoService.getAllProductos();
            productosOriginales = productos;
            modeloTabla.setRowCount(0);
            for (Producto producto : productos) {
                modeloTabla.addRow(new Object[]{
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getCategoria(),
                    String.format("$%.2f", producto.getPrecio()),
                    ""
                });
            }
            if (productos.isEmpty()) {
                mostrarMensajeSinResultados();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar productos: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarProductos() {
        String busqueda = txtBuscar.getText().toLowerCase();
        String filtro = (String) cmbFiltro.getSelectedItem();
        boolean hayResultados = false;
        modeloTabla.setRowCount(0);
        for (Producto producto : productosOriginales) {
            String valor = "";
            switch (filtro) {
                case "Nombre":
                    valor = producto.getNombre().toLowerCase();
                    break;
                case "Categoría":
                    valor = producto.getCategoria().toLowerCase();
                    break;
                case "Precio":
                    valor = String.format("$%.2f", producto.getPrecio()).toLowerCase();
                    break;
                default:
                    valor = producto.getNombre().toLowerCase() + " " + producto.getCategoria().toLowerCase();
            }
            if (valor.contains(busqueda)) {
                modeloTabla.addRow(new Object[]{
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getCategoria(),
                    String.format("$%.2f", producto.getPrecio()),
                    ""
                });
                hayResultados = true;
            }
        }
        if (!hayResultados) {
            mostrarMensajeSinResultados();
        }
    }

    private void mostrarMensajeSinResultados() {
        modeloTabla.setRowCount(0);
        modeloTabla.addRow(new Object[]{"", "No se encontraron productos", "", "", "", ""});
    }

    private void añadirAlCarrito(int row) {
        try {
            Long idProducto = (Long) modeloTabla.getValueAt(row, 0);
            String nombre = (String) modeloTabla.getValueAt(row, 1);
            double precio = Double.parseDouble(((String) modeloTabla.getValueAt(row, 4)).replace("$", "").replace(",", "."));

            Producto producto = new Producto();
            producto.setId(idProducto);
            producto.setNombre(nombre);
            producto.setPrecio(precio);

            ItemCarrito item = new ItemCarrito();
            item.setProducto(producto);
            item.setCantidad(1);

            carrito.addItem(item);
            CarritoService.guardarCarrito(carrito);

            JOptionPane.showMessageDialog(this,
                "Producto añadido al carrito",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al añadir al carrito: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCarrito() {
        CarritoVista carritoVista = new CarritoVista(this, carrito);
        carritoVista.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Error al cargar FlatLaf: " + ex.getMessage());
        }
        
        SwingUtilities.invokeLater(() -> {
            new ClienteVista().setVisible(true);
        });
    }
} 
package com.project.frontend.productSystem.vistas;

import com.formdev.flatlaf.FlatDarkLaf;
import com.project.frontend.productSystem.models.Producto;
import com.project.frontend.productSystem.services.IProductoService;
import com.project.frontend.productSystem.services.RetrofitClient;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import java.io.IOException;
import retrofit2.Response;

public class ProductoVista extends JFrame {
    private IProductoService productoService;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltro;
    private JButton btnNuevo, btnEditar, btnEliminar, btnBuscar;
    private JPanel panelSidebar, panelHeader, panelResumen, panelTabla, panelBotones, panelFiltros;
    private JLabel lblTotalProductos;

    public ProductoVista() {
        productoService = RetrofitClient.getClient().create(IProductoService.class);
        initComponents();
        cargarProductos();
    }

    private void initComponents() {
        setTitle("Dashboard - Gestión de Productos");
        setSize(1700, 800);
        setResizable(false);
        setMinimumSize(new Dimension(1300, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        panelSidebar = new JPanel();
        panelSidebar.setBackground(new Color(25, 25, 25));
        panelSidebar.setPreferredSize(new Dimension(220, 0));
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS));
        panelSidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(40, 40, 40)));

        JLabel lblLogo = new JLabel("\uD83D\uDCBB Inventronix");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogo.setForeground(new Color(0, 200, 180));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        panelSidebar.add(lblLogo);

        JButton btnProductos = crearBotonSidebar("Productos", "📦");
        btnProductos.setEnabled(false);
        panelSidebar.add(btnProductos);
        panelSidebar.add(Box.createVerticalGlue());

        JLabel lblFooter = new JLabel("v1.0 - Demo");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(120, 120, 120));
        lblFooter.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFooter.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelSidebar.add(lblFooter);

        add(panelSidebar, BorderLayout.WEST);

        // Header
        panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(30, 30, 30));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));
        JLabel lblTitulo = new JLabel("Gestión de Productos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        panelHeader.add(lblTitulo, BorderLayout.WEST);
        JLabel lblUser = new JLabel("👤 Admin");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblUser.setForeground(new Color(180, 180, 180));
        panelHeader.add(lblUser, BorderLayout.EAST);
        add(panelHeader, BorderLayout.NORTH);

        // Panel central (resumen + tabla)
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout(0, 20));
        panelCentral.setBackground(new Color(33, 33, 33));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tarjetas resumen
        panelResumen = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panelResumen.setOpaque(false);
        lblTotalProductos = crearTarjetaResumen("Total Productos", "0", new Color(0, 200, 180));
        panelResumen.add(lblTotalProductos);
        panelCentral.add(panelResumen, BorderLayout.NORTH);

        // Filtros y botones
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(false);
        panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltros.setOpaque(false);
        JLabel lblBuscar = new JLabel("Buscar por:");
        lblBuscar.setForeground(new Color(200, 200, 200));
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cmbFiltro = new JComboBox<>(new String[]{"Nombre", "Categoría", "Proveedor"});
        cmbFiltro.setBackground(new Color(40, 40, 40));
        cmbFiltro.setForeground(Color.WHITE);
        cmbFiltro.setPreferredSize(new Dimension(160, 36));
        cmbFiltro.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cmbFiltro.setFocusable(false);
        cmbFiltro.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
        cmbFiltro.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBackground(isSelected ? new Color(0, 200, 180) : new Color(40, 40, 40));
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        });
        cmbFiltro.setBorder(BorderFactory.createLineBorder(new Color(66, 66, 66), 2, true));
        txtBuscar = new JTextField(20);
        txtBuscar.setBackground(new Color(40, 40, 40));
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setCaretColor(Color.WHITE);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(66, 66, 66), 2, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        txtBuscar.setPreferredSize(new Dimension(250, 38));
        btnBuscar = crearBotonAccion("Buscar", "\uD83D\uDD0D", new Color(0, 200, 180));
        panelFiltros.add(lblBuscar);
        panelFiltros.add(cmbFiltro);
        panelFiltros.add(txtBuscar);
        panelFiltros.add(btnBuscar);
        panelTop.add(panelFiltros, BorderLayout.WEST);

        panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setOpaque(false);
        btnNuevo = crearBotonAccion("Nuevo", "\u2795", new Color(76, 175, 80));
        btnEditar = crearBotonAccion("Editar", "\u270E", new Color(0, 150, 136));
        btnEliminar = crearBotonAccion("Eliminar", "\uD83D\uDDD1", new Color(244, 67, 54));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelTop.add(panelBotones, BorderLayout.EAST);
        panelCentral.add(panelTop, BorderLayout.CENTER);

        // Tabla de productos
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Descripción");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Categoría");
        modeloTabla.addColumn("Stock");
        modeloTabla.addColumn("Proveedor");

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
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JTableHeader header = tablaProductos.getTableHeader();
        header.setBackground(new Color(0, 200, 180));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        scrollPane.getViewport().setBackground(new Color(33, 33, 33));
        panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        panelCentral.add(panelTabla, BorderLayout.SOUTH);

        add(panelCentral, BorderLayout.CENTER);

        configurarAcciones();
    }

    private JButton crearBotonSidebar(String texto, String icono) {
        JButton boton = new JButton(icono + "  " + texto);
        boton.setMaximumSize(new Dimension(200, 50));
        boton.setBackground(new Color(30, 30, 30));
        boton.setForeground(new Color(0, 200, 180));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JButton crearBotonAccion(String texto, String icono, Color color) {
        JButton boton = new JButton(icono + "  " + texto);
        boton.setPreferredSize(new Dimension(160, 40));
        boton.setBackground(color);
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setToolTipText(texto);
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });
        return boton;
    }

    private JLabel crearTarjetaResumen(String titulo, String valor, Color color) {
        JPanel tarjeta = new JPanel();
        tarjeta.setPreferredSize(new Dimension(220, 90));
        tarjeta.setBackground(new Color(40, 40, 40));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        tarjeta.setLayout(new BorderLayout());
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitulo.setForeground(color);
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValor.setForeground(Color.WHITE);
        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);
        panelResumen.add(tarjeta);
        return lblValor;
    }

    private void configurarAcciones() {
        btnNuevo.addActionListener(e -> {
            ProductoFormularioVista formulario = new ProductoFormularioVista(this, null);
            formulario.setVisible(true);
        });
        btnEditar.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada == -1) {
                mostrarError("Por favor, seleccione un producto para editar");
                return;
            }
            Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
            ProductoFormularioVista formulario = new ProductoFormularioVista(this, id);
            formulario.setVisible(true);
        });
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada == -1) {
                mostrarError("Por favor, seleccione un producto para eliminar");
                return;
            }
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
                    Response<Void> response = productoService.eliminarProducto(id).execute();
                    if (response.isSuccessful()) {
                        actualizarTabla();
                        mostrarExito("Producto eliminado exitosamente");
                    } else {
                        mostrarError("Error al eliminar el producto: " + response.errorBody().string());
                    }
                } catch (IOException ex) {
                    mostrarError("Error de conexión: " + ex.getMessage());
                } catch (Exception ex) {
                    mostrarError("Error al eliminar el producto: " + ex.getMessage());
                }
            }
        });
        btnBuscar.addActionListener(e -> {
            String filtro = txtBuscar.getText().trim();
            if (filtro.isEmpty()) {
                cargarProductos();
                return;
            }
            try {
                List<Producto> productos;
                String tipoFiltro = (String) cmbFiltro.getSelectedItem();
                Response<List<Producto>> response;
                switch (tipoFiltro) {
                    case "Nombre":
                        response = productoService.obtenerProductosPorNombre(filtro).execute();
                        break;
                    case "Categoría":
                        response = productoService.obtenerProductosPorCategoria(filtro).execute();
                        break;
                    case "Proveedor":
                        response = productoService.obtenerProductosPorProveedor(filtro).execute();
                        break;
                    default:
                        response = productoService.obtenerTodosLosProductos().execute();
                }
                if (response.isSuccessful()) {
                    actualizarTabla(response.body());
                } else {
                    mostrarError("Error al buscar productos: " + response.errorBody().string());
                }
            } catch (IOException ex) {
                mostrarError("Error de conexión: " + ex.getMessage());
            } catch (Exception ex) {
                mostrarError("Error al buscar productos: " + ex.getMessage());
            }
        });
    }

    public void actualizarTabla() {
        try {
            Response<List<Producto>> response = productoService.obtenerTodosLosProductos().execute();
            if (response.isSuccessful()) {
                actualizarTabla(response.body());
            } else {
                mostrarError("Error al cargar los productos: " + response.errorBody().string());
            }
        } catch (IOException ex) {
            mostrarError("Error de conexión: " + ex.getMessage());
        } catch (Exception e) {
            mostrarError("Error al cargar los productos: " + e.getMessage());
        }
    }

    private void actualizarTabla(List<Producto> productos) {
        modeloTabla.setRowCount(0);
        int total = 0;
        if (productos != null) {
            for (Producto producto : productos) {
                modeloTabla.addRow(new Object[]{
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getCategoria(),
                    producto.getStock(),
                    producto.getProveedor()
                });
                total++;
            }
        }
        lblTotalProductos.setText(String.valueOf(total));
    }

    private void cargarProductos() {
        actualizarTabla();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            ProductoVista vista = new ProductoVista();
            vista.setVisible(true);
        });
    }
}
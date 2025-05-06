package com.project.frontend.productSystem.vistas;

import com.project.frontend.productSystem.models.Producto;
import com.project.frontend.productSystem.services.IProductoService;
import com.project.frontend.productSystem.services.RetrofitClient;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import retrofit2.Response;

public class ProductoVista extends JFrame {
    private IProductoService productoService;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltro;
    private JButton btnNuevo, btnEditar, btnEliminar, btnBuscar;
    private JPanel panelFiltros, panelBotones, panelTabla;

    // Colores del tema oscuro
    private final Color COLOR_FONDO = new Color(33, 33, 33);
    private final Color COLOR_PRIMARIO = new Color(0, 150, 136);
    private final Color COLOR_SECUNDARIO = new Color(76, 175, 80);
    private final Color COLOR_PELIGRO = new Color(244, 67, 54);
    private final Color COLOR_TEXTO = new Color(255, 255, 255);
    private final Color COLOR_TEXTO_SECUNDARIO = new Color(189, 189, 189);
    private final Color COLOR_BORDE = new Color(66, 66, 66);

    public ProductoVista() {
        productoService = RetrofitClient.getClient().create(IProductoService.class);
        initComponents();
        cargarProductos();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión de Productos");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COLOR_FONDO);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de filtros
        panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltros.setBackground(COLOR_FONDO);
        
        JLabel lblBuscar = new JLabel("Buscar por:");
        lblBuscar.setForeground(COLOR_TEXTO);
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        cmbFiltro = new JComboBox<>(new String[]{"Nombre", "Categoría", "Proveedor"});
        cmbFiltro.setBackground(COLOR_FONDO);
        cmbFiltro.setForeground(COLOR_TEXTO);
        cmbFiltro.setPreferredSize(new Dimension(180, 35));
        cmbFiltro.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cmbFiltro.setFocusable(false);
        cmbFiltro.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
        cmbFiltro.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBackground(isSelected ? COLOR_PRIMARIO : COLOR_FONDO);
                label.setForeground(COLOR_TEXTO);
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        });
        cmbFiltro.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 2, true));
        
        txtBuscar = new JTextField(20);
        txtBuscar.setBackground(COLOR_FONDO);
        txtBuscar.setForeground(COLOR_TEXTO);
        txtBuscar.setCaretColor(COLOR_TEXTO);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 2, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        txtBuscar.setPreferredSize(new Dimension(250, 38));
        
        btnBuscar = crearBoton("Buscar", COLOR_PRIMARIO);
        btnBuscar.setPreferredSize(new Dimension(120, 35));
        
        panelFiltros.add(lblBuscar);
        panelFiltros.add(cmbFiltro);
        panelFiltros.add(txtBuscar);
        panelFiltros.add(btnBuscar);

        // Panel de botones
        panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBotones.setBackground(COLOR_FONDO);
        
        btnNuevo = crearBoton("Nuevo Producto", COLOR_SECUNDARIO);
        btnEditar = crearBoton("Editar Producto", COLOR_PRIMARIO);
        btnEliminar = crearBoton("Eliminar Producto", COLOR_PELIGRO);
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

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
                    c.setBackground(COLOR_PRIMARIO);
                    c.setForeground(COLOR_TEXTO);
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(45, 45, 45) : new Color(40, 40, 40));
                    c.setForeground(COLOR_TEXTO);
                }
                return c;
            }
        };
        
        tablaProductos.setRowHeight(40);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setShowGrid(false);
        tablaProductos.setIntercellSpacing(new Dimension(0, 0));
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Personalizar encabezados de la tabla
        JTableHeader header = tablaProductos.getTableHeader();
        header.setBackground(COLOR_PRIMARIO);
        header.setForeground(COLOR_TEXTO);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(new RoundBorder(15, COLOR_BORDE));
        scrollPane.getViewport().setBackground(COLOR_FONDO);

        // Panel para la tabla
        panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(COLOR_FONDO);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        // Agregar componentes al panel principal
        panelPrincipal.add(panelFiltros, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(panelTabla, BorderLayout.SOUTH);

        // Agregar panel principal al frame
        add(panelPrincipal);

        // Configurar acciones
        configurarAcciones();
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(150, 40));
        boton.setBackground(color);
        boton.setForeground(COLOR_TEXTO);
        boton.setBorder(new RoundBorder(15, color));
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
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
            }
        }
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

    // Clase para bordes redondeados
    private static class RoundBorder implements Border {
        private int radius;
        private Color color;

        RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            ProductoVista vista = new ProductoVista();
            vista.setVisible(true);
        });
    }
}
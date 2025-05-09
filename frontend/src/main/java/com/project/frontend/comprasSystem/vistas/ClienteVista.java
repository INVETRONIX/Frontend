package com.project.frontend.comprasSystem.vistas;

import com.formdev.flatlaf.FlatDarkLaf;
import com.project.frontend.comprasSystem.models.Carrito;
import com.project.frontend.comprasSystem.models.ItemCarrito;
import com.project.frontend.comprasSystem.models.Producto;
import com.project.frontend.comprasSystem.services.CarritoService;
import com.project.frontend.comprasSystem.services.ProductoService;
import com.project.frontend.comprasSystem.services.ImageService;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import java.net.URL;
import java.awt.image.BufferedImage;

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
    private final CarritoService carritoService;
    private ProductoService productoService;
    private List<Producto> productosOriginales;
    private JPanel panelAlbum;
    private JScrollPane scrollPaneAlbum;
    private ImageService imageService;

    public ClienteVista() {
        carritoService = CarritoService.getInstance();
        carrito = carritoService.getCarrito();
        this.productoService = new ProductoService();
        imageService = new ImageService();
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

        // Botón Historial de Compras
        JButton btnHistorial = new JButton("Historial de Compras");
        btnHistorial.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnHistorial.setBackground(new Color(0, 200, 180));
        btnHistorial.setForeground(Color.WHITE);
        btnHistorial.setFocusPainted(false);
        btnHistorial.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnHistorial.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHistorial.addActionListener(e -> new HistorialComprasVista(false).setVisible(true));

        // Panel para ambos botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnVerCarrito);
        panelBotones.add(btnHistorial);
        panelHeader.add(panelBotones, BorderLayout.EAST);

        add(panelHeader, BorderLayout.NORTH);

        // Panel tipo álbum
        panelAlbum = new JPanel();
        panelAlbum.setLayout(new WrapLayout(FlowLayout.LEFT, 20, 20));
        panelAlbum.setBackground(new Color(33, 33, 33));
        scrollPaneAlbum = new JScrollPane(panelAlbum);
        scrollPaneAlbum.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        scrollPaneAlbum.getViewport().setBackground(new Color(33, 33, 33));
        add(scrollPaneAlbum, BorderLayout.CENTER);

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
            mostrarProductosEnAlbum(productos);
            if (productos.isEmpty()) {
                mostrarMensajeSinResultadosAlbum();
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
        List<Producto> filtrados = new java.util.ArrayList<>();
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
                filtrados.add(producto);
                hayResultados = true;
            }
        }
        mostrarProductosEnAlbum(filtrados);
        if (!hayResultados) {
            mostrarMensajeSinResultadosAlbum();
        }
    }

    private void mostrarProductosEnAlbum(List<Producto> productos) {
        panelAlbum.removeAll();
        for (Producto producto : productos) {
            JPanel card = crearCardProducto(producto);
            panelAlbum.add(card);
        }
        panelAlbum.revalidate();
        panelAlbum.repaint();
    }

    private JPanel crearCardProducto(Producto producto) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(220, 320));
        card.setBackground(new Color(44, 44, 44));
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 200, 180), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Imagen
        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        lblImagen.setPreferredSize(new Dimension(200, 160));
        String urlImg = imageService.getImageUrl(producto.getNombre());
        try {
            if (urlImg != null) {
                java.awt.Image img = ImageIO.read(new URL(urlImg));
                lblImagen.setIcon(new ImageIcon(img.getScaledInstance(200, 160, java.awt.Image.SCALE_SMOOTH)));
            } else {
                lblImagen.setIcon(new ImageIcon(new BufferedImage(200, 160, BufferedImage.TYPE_INT_RGB)));
            }
        } catch (Exception e) {
            lblImagen.setIcon(new ImageIcon(new BufferedImage(200, 160, BufferedImage.TYPE_INT_RGB)));
        }
        card.add(lblImagen, BorderLayout.NORTH);

        // Info producto
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(new Color(44, 44, 44));
        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setForeground(Color.WHITE);
        JLabel lblPrecio = new JLabel(String.format("$%.2f", producto.getPrecio()));
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPrecio.setForeground(new Color(0, 200, 180));
        panelInfo.add(lblNombre);
        panelInfo.add(Box.createVerticalStrut(8));
        panelInfo.add(lblPrecio);
        card.add(panelInfo, BorderLayout.CENTER);

        // Botón añadir
        JButton btnAdd = new JButton("➕ Añadir al carrito");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.setBackground(new Color(76, 175, 80));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> {
            ItemCarrito item = new ItemCarrito();
            item.setProducto(producto);
            item.setCantidad(1);
            carrito.addItem(item);
            carritoService.guardarCarrito();
            JOptionPane.showMessageDialog(this,
                "Producto añadido al carrito",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        card.add(btnAdd, BorderLayout.SOUTH);
        return card;
    }

    private void mostrarMensajeSinResultadosAlbum() {
        panelAlbum.removeAll();
        JLabel lbl = new JLabel("No se encontraron productos", JLabel.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lbl.setForeground(Color.WHITE);
        panelAlbum.add(lbl);
        panelAlbum.revalidate();
        panelAlbum.repaint();
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
package com.project.frontend.SYSTEMcompras.interfaz;

import com.project.frontend.SYSTEMcompras.controller.ControllerCompra;
import com.project.frontend.SYSTEMcompras.model.Compra;
import com.project.frontend.SYSTEMlogin.data.TokenManager;
import com.project.frontend.SYSTEMlogin.interfaz.Login;
import com.project.frontend.SYSTEMproductos.controller.ControllerProducto;
import com.project.frontend.SYSTEMproductos.model.Producto;
import com.project.frontend.core.BackendException;
import com.project.frontend.SYSTEMimages.service.ImageService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class VentanaPrincipalCliente extends JFrame {
    private ControllerProducto controllerProducto;
    private ControllerCompra controllerCompra;
    private ImageService imageService;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel panelProductos;
    private JScrollPane scrollPane;
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JButton btnBuscar;
    private JButton btnHistorial;
    private JButton btnCerrarSesion;

    public VentanaPrincipalCliente() {
        this.controllerProducto = new ControllerProducto();
        this.controllerCompra = new ControllerCompra();
        this.imageService = new ImageService();
        
        initComponents();
        this.setLocationRelativeTo(null);
        
        try {
            cargarProductos();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Invetronix - Cliente");
        setSize(1200, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Panel izquierdo (logo y navegación)
        leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 122, 255));
        leftPanel.setPreferredSize(new Dimension(320, 800));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        JLabel lblLogo = new JLabel("Invetronix");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(lblLogo);
        leftPanel.add(Box.createVerticalStrut(40));

        btnHistorial = new JButton("Ver Historial");
        btnHistorial.setMaximumSize(new Dimension(260, 45));
        btnHistorial.setBackground(new Color(41, 128, 185));
        btnHistorial.setForeground(Color.WHITE);
        btnHistorial.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnHistorial.setFocusPainted(false);
        btnHistorial.setBorderPainted(false);
        btnHistorial.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHistorial.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHistorial.addActionListener(e -> btnHistorialActionPerformed(e));
        leftPanel.add(btnHistorial);
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
        btnCerrarSesion.addActionListener(e -> btnCerrarSesionActionPerformed(e));
        leftPanel.add(btnCerrarSesion);
        leftPanel.add(Box.createVerticalGlue());

        // Panel derecho (productos y búsqueda)
        rightPanel = new JPanel();
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Título
        JLabel lblTitulo = new JLabel("Catálogo de Productos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(33, 33, 33));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(lblTitulo);
        rightPanel.add(Box.createVerticalStrut(20));

        // Panel de búsqueda
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 4, 15, 15));
        searchPanel.setBackground(new Color(245, 245, 245));
        searchPanel.setMaximumSize(new Dimension(800, 120));
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campos de búsqueda
        txtId = createSearchField("ID");
        txtName = createSearchField("Nombre");
        txtPrecio = createSearchField("Precio");
        txtStock = createSearchField("Stock");

        searchPanel.add(txtId);
        searchPanel.add(txtName);
        searchPanel.add(txtPrecio);
        searchPanel.add(txtStock);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(150, 40));
        btnBuscar.setBackground(new Color(41, 128, 185));
        btnBuscar.setForeground(Color.BLUE);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> btnBuscarActionPerformed(e));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(btnBuscar);
        searchPanel.add(buttonPanel);

        rightPanel.add(searchPanel);
        rightPanel.add(Box.createVerticalStrut(20));

        // Panel de productos
        panelProductos = new JPanel(new GridLayout(0, 3, 20, 20));
        panelProductos.setBackground(new Color(245, 245, 245));
        
        scrollPane = new JScrollPane(panelProductos);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        scrollPane.setMaximumSize(new Dimension(800, 500));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        rightPanel.add(scrollPane);
        rightPanel.add(Box.createVerticalGlue());

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTextField createSearchField(String placeholder) {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(placeholder),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return field;
    }

    private void cargarProductos() throws IOException {
        try {
            List<Producto> productos = controllerProducto.getAllProductos();
            if (productos == null || productos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos disponibles", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            panelProductos.removeAll();
            for (Producto producto : productos) {
                JPanel cardPanel = crearCardProducto(producto);
                panelProductos.add(cardPanel);
            }

            panelProductos.revalidate();
            panelProductos.repaint();
        } catch (BackendException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel crearCardProducto(Producto producto) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setPreferredSize(new Dimension(250, 400));

        // Panel para la imagen
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(200, 200));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(imagePanel);

        // Obtener imagen del producto
        try {
            String imageUrl = imageService.getImageUrl(producto.getNombre());
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    ImageIcon icon = new ImageIcon(new java.net.URL(imageUrl));
                    Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(img);
                    JLabel imageLabel = new JLabel(scaledIcon);
                    imageLabel.setPreferredSize(new Dimension(200, 200));
                    imagePanel.add(imageLabel);
                } catch (Exception e) {
                    System.err.println("Error al cargar la imagen: " + e.getMessage());
                    JLabel errorLabel = new JLabel("Error al cargar imagen");
                    errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    errorLabel.setForeground(new Color(150, 150, 150));
                    imagePanel.add(errorLabel);
                }
            } else {
                JLabel placeholder = new JLabel("Imagen no disponible");
                placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                placeholder.setForeground(new Color(150, 150, 150));
                imagePanel.add(placeholder);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener la URL de la imagen: " + e.getMessage());
            JLabel errorLabel = new JLabel("Error al cargar imagen");
            errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            errorLabel.setForeground(new Color(150, 150, 150));
            imagePanel.add(errorLabel);
        }

        card.add(Box.createVerticalStrut(15));

        // Información del producto
        JLabel nombreLabel = new JLabel(producto.getNombre());
        nombreLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nombreLabel.setForeground(new Color(33, 33, 33));
        nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nombreLabel);

        card.add(Box.createVerticalStrut(10));

        // Agregar ID del producto
        JLabel idLabel = new JLabel("ID: " + producto.getId());
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idLabel.setForeground(new Color(100, 100, 100));
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(idLabel);

        card.add(Box.createVerticalStrut(10));

        JLabel precioLabel = new JLabel(String.format("$%,.2f", producto.getPrecio()));
        precioLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        precioLabel.setForeground(new Color(41, 128, 185));
        precioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(precioLabel);

        card.add(Box.createVerticalStrut(10));

        JLabel stockLabel = new JLabel("Stock: " + producto.getStock());
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stockLabel.setForeground(new Color(100, 100, 100));
        stockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(stockLabel);

        card.add(Box.createVerticalStrut(15));

        // Botón de compra
        JButton btnComprar = new JButton("Comprar");
        btnComprar.setPreferredSize(new Dimension(150, 40));
        btnComprar.setBackground(new Color(41, 128, 185));
        btnComprar.setForeground(Color.RED);
        btnComprar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnComprar.setFocusPainted(false);
        btnComprar.setBorderPainted(false);
        btnComprar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnComprar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnComprar.addActionListener(e -> realizarCompra(producto));
        card.add(btnComprar);

        return card;
    }

    private void realizarCompra(Producto producto) {
        try {
            // Verificar stock
            if (producto.getStock() <= 0) {
                JOptionPane.showMessageDialog(this, "Lo sentimos, este producto está agotado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Obtener el usuario actual del TokenManager
            Long userId = TokenManager.getInstance().getUserId();
            if (userId == null) {
                // Mostrar mensaje más detallado para diagnóstico
                String token = TokenManager.getInstance().getToken();
                JOptionPane.showMessageDialog(
                    this, 
                    "Error: No se pudo identificar al usuario. El ID de usuario es nulo.\n" +
                    "Token actual: " + (token != null ? "Presente" : "Null") + "\n" +
                    "Por favor, inicie sesión nuevamente.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                
                // Redirigir al login
                Login login = new Login();
                login.setVisible(true);
                this.dispose();
                return;
            }
    
            // Crear la compra
            Compra compra = new Compra();
            compra.setProducto(producto);
            
            // Crear usuario temporal para la compra
            com.project.frontend.SYSTEMregistro.model.Usuario usuario = new com.project.frontend.SYSTEMregistro.model.Usuario();
            usuario.setId(userId);
            compra.setUsuario(usuario);
    
            System.out.println("Realizando compra con usuario ID: " + userId);
            
            // Registrar la compra
            Compra compraCreada = controllerCompra.createCompra(compra);
            if (compraCreada != null) {
                JOptionPane.showMessageDialog(this, "¡Compra realizada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Actualizar la vista
                cargarProductos();
            }
        } catch (BackendException e) {
            JOptionPane.showMessageDialog(this, "Error al realizar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al realizar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        String idText = txtId.getText().trim();
        String nombre = txtName.getText().trim();
        String precioText = txtPrecio.getText().trim();
        String stockText = txtStock.getText().trim();

        try {
            List<Producto> productos = null;

            if (!idText.isEmpty()) {
                try {
                    Long id = Long.parseLong(idText);
                    Optional<Producto> producto = controllerProducto.getProductoById(id);
                    if (producto.isPresent()) {
                        productos = List.of(producto.get());
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
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

                productos = controllerProducto.findByFilters(nombre.isEmpty() ? null : nombre, precio, stock);
            }

            if (productos != null && !productos.isEmpty()) {
                panelProductos.removeAll();
                for (Producto producto : productos) {
                    JPanel cardPanel = crearCardProducto(producto);
                    panelProductos.add(cardPanel);
                }
                panelProductos.revalidate();
                panelProductos.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron productos con los criterios especificados", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {
        HistorialComprasCliente historial = new HistorialComprasCliente();
        historial.setVisible(true);
        this.dispose();
    }
 
    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {
        Login login = new Login();
        login.setVisible(true);
        TokenManager.getInstance().clearToken();
        this.dispose();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipalCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new VentanaPrincipalCliente().setVisible(true);
        });
    }
} 
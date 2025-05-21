package com.project.frontend.SYSTEMcompras.interfaz;

import com.project.frontend.SYSTEMcompras.controller.ControllerCompra;
import com.project.frontend.SYSTEMcompras.model.Compra;
import com.project.frontend.SYSTEMimages.controller.ControllerImages;
import com.project.frontend.SYSTEMlogin.data.TokenManager;
import com.project.frontend.SYSTEMlogin.interfaz.Login;
import com.project.frontend.SYSTEMproductos.controller.ControllerProducto;
import com.project.frontend.SYSTEMproductos.model.Producto;
import com.project.frontend.core.BackendException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class VentanaPrincipalCliente extends JFrame {
    private ControllerProducto controllerProducto;
    private ControllerCompra controllerCompra;
    private ControllerImages controllerImages;
    private JPanel panelProductos;
    private JScrollPane scrollPane;
    private JPanel panelBusqueda;
    private JPanel panelNavegacion;
    private JPanel panelContenedor;
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JButton btnBuscar;
    private JButton btnHistorial;
    private JButton btnCerrarSesion;

    public VentanaPrincipalCliente() {
        // Inicializamos los controladores
        this.controllerProducto = new ControllerProducto();
        this.controllerCompra = new ControllerCompra();
        this.controllerImages = new ControllerImages();

        // Configuramos la ventana
        this.setTitle("Invetronix - Cliente");
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Inicializamos los componentes
        inicializarComponentes();
        
        try {
            cargarProductos();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarComponentes() {
        // Panel principal
        panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(new Color(153, 153, 153));
        
        // Panel de búsqueda
        panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(new Color(153, 153, 153));
        
        txtId = new JTextField(5);
        txtName = new JTextField(10);
        txtPrecio = new JTextField(5);
        txtStock = new JTextField(5);
        btnBuscar = new JButton("Buscar");
        
        panelBusqueda.add(new JLabel("ID:"));
        panelBusqueda.add(txtId);
        panelBusqueda.add(new JLabel("Nombre:"));
        panelBusqueda.add(txtName);
        panelBusqueda.add(new JLabel("Precio:"));
        panelBusqueda.add(txtPrecio);
        panelBusqueda.add(new JLabel("Stock:"));
        panelBusqueda.add(txtStock);
        panelBusqueda.add(btnBuscar);
        
        // Panel de productos
        panelProductos = new JPanel(new GridLayout(0, 3, 10, 10));
        scrollPane = new JScrollPane(panelProductos);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        
        // Panel de navegación
        panelNavegacion = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelNavegacion.setBackground(new Color(153, 153, 153));
        
        btnHistorial = new JButton("Ver Historial");
        btnCerrarSesion = new JButton("Cerrar Sesión");
        
        panelNavegacion.add(btnHistorial);
        panelNavegacion.add(btnCerrarSesion);
        
        // Agregamos los paneles al contenedor principal
        panelContenedor.add(panelBusqueda, BorderLayout.NORTH);
        panelContenedor.add(scrollPane, BorderLayout.CENTER);
        panelContenedor.add(panelNavegacion, BorderLayout.SOUTH);
        
        // Agregamos el panel contenedor a la ventana
        this.add(panelContenedor);
        
        // Configuramos los listeners
        btnBuscar.addActionListener(e -> btnBuscarActionPerformed(e));
        btnHistorial.addActionListener(e -> btnHistorialActionPerformed(e));
        btnCerrarSesion.addActionListener(e -> btnCerrarSesionActionPerformed(e));
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
            e.printStackTrace(); // Para depuración
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para depuración
        }
    }

    private JPanel crearCardProducto(Producto producto) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setPreferredSize(new Dimension(250, 350));

        // Obtener imagen del producto
        try {
            String imageUrl = controllerImages.getImage(producto.getNombre());
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(new java.net.URL(imageUrl));
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(img));
                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                card.add(imageLabel);
            }
        } catch (Exception e) {
            // Si hay error al cargar la imagen, mostrar un placeholder
            JLabel placeholder = new JLabel("Imagen no disponible");
            placeholder.setPreferredSize(new Dimension(200, 200));
            placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(placeholder);
        }

        // Información del producto
        JLabel nombreLabel = new JLabel(producto.getNombre());
        nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nombreLabel);

        JLabel precioLabel = new JLabel(String.format("$%,.2f", producto.getPrecio()));
        precioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(precioLabel);

        JLabel stockLabel = new JLabel("Stock: " + producto.getStock());
        stockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(stockLabel);

        // Botón de compra
        JButton btnComprar = new JButton("Comprar");
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
        this.dispose();
        TokenManager.getInstance().saveToken(null);
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
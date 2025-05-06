package com.project.frontend.productSystem.vistas;

import com.project.frontend.productSystem.models.Producto;
import com.project.frontend.productSystem.services.IProductoService;
import com.project.frontend.productSystem.services.RetrofitClient;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.RoundRectangle2D;

public class ProductoFormularioVista extends JDialog {
    private final IProductoService productoService;
    private final ProductoVista parent;
    private final Long idProducto;
    
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtCategoria;
    private JTextField txtStock;
    private JTextField txtProveedor;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public ProductoFormularioVista(ProductoVista parent, Long id) {
        super(parent, true);
        this.parent = parent;
        this.productoService = RetrofitClient.getClient().create(IProductoService.class);
        this.idProducto = id;
        
        initComponents();
        if (id != null) {
            cargarProducto(id);
        }
    }

    private void initComponents() {
        setTitle(idProducto == null ? "Nuevo Producto" : "Editar Producto");
        setSize(500, 500);
        setLocationRelativeTo(parent);
        setResizable(false);

        // Panel principal con fondo degradado
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                Color color1 = new Color(52, 152, 219);
                Color color2 = new Color(41, 128, 185);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Estilo para las etiquetas
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Color labelColor = Color.WHITE;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(labelFont);
        lblNombre.setForeground(labelColor);
        panelFormulario.add(lblNombre, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(20);
        txtNombre.setBorder(new RoundBorder(10));
        panelFormulario.add(txtNombre, gbc);

        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(labelFont);
        lblDescripcion.setForeground(labelColor);
        panelFormulario.add(lblDescripcion, gbc);
        gbc.gridx = 1;
        txtDescripcion = new JTextArea(5, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtDescripcion.setBackground(new Color(40, 40, 40));
        txtDescripcion.setForeground(Color.WHITE);
        txtDescripcion.setCaretColor(Color.WHITE);
        txtDescripcion.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setBorder(new RoundBorder(10, new Color(66, 66, 66)));
        scrollDescripcion.getViewport().setBackground(new Color(40, 40, 40));
        scrollDescripcion.setPreferredSize(new Dimension(0, 90));
        panelFormulario.add(scrollDescripcion, gbc);

        // Precio
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(labelFont);
        lblPrecio.setForeground(labelColor);
        panelFormulario.add(lblPrecio, gbc);
        gbc.gridx = 1;
        txtPrecio = new JTextField(20);
        txtPrecio.setBorder(new RoundBorder(10));
        panelFormulario.add(txtPrecio, gbc);

        // Categoría
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(labelFont);
        lblCategoria.setForeground(labelColor);
        panelFormulario.add(lblCategoria, gbc);
        gbc.gridx = 1;
        txtCategoria = new JTextField(20);
        txtCategoria.setBorder(new RoundBorder(10));
        panelFormulario.add(txtCategoria, gbc);

        // Stock
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblStock = new JLabel("Stock:");
        lblStock.setFont(labelFont);
        lblStock.setForeground(labelColor);
        panelFormulario.add(lblStock, gbc);
        gbc.gridx = 1;
        txtStock = new JTextField(20);
        txtStock.setBorder(new RoundBorder(10));
        panelFormulario.add(txtStock, gbc);

        // Proveedor
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblProveedor = new JLabel("Proveedor:");
        lblProveedor.setFont(labelFont);
        lblProveedor.setForeground(labelColor);
        panelFormulario.add(lblProveedor, gbc);
        gbc.gridx = 1;
        txtProveedor = new JTextField(20);
        txtProveedor.setBorder(new RoundBorder(10));
        panelFormulario.add(txtProveedor, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);
        btnGuardar = crearBoton("Guardar", new Color(46, 204, 113));
        btnCancelar = crearBoton("Cancelar", new Color(231, 76, 60));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        // Agregar componentes al panel principal
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Agregar panel principal al diálogo
        add(panelPrincipal);

        // Configurar acciones de los botones
        configurarAcciones();
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(100, 35));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setBorder(new RoundBorder(10));
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        return boton;
    }

    private void configurarAcciones() {
        btnGuardar.addActionListener(e -> guardarProducto());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarProducto(Long id) {
        try {
            Producto producto = productoService.obtenerProductoPorId(id).execute().body();
            if (producto != null) {
                txtNombre.setText(producto.getNombre());
                txtDescripcion.setText(producto.getDescripcion());
                txtPrecio.setText(producto.getPrecio().toString());
                txtCategoria.setText(producto.getCategoria());
                txtStock.setText(producto.getStock().toString());
                txtProveedor.setText(producto.getProveedor());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el producto: " + e.getMessage());
            dispose();
        }
    }

    private void guardarProducto() {
        try {
            Producto producto = new Producto();
            if (idProducto != null) {
                producto.setId(idProducto);
            }
            producto.setNombre(txtNombre.getText());
            producto.setDescripcion(txtDescripcion.getText());
            producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            producto.setCategoria(txtCategoria.getText());
            producto.setStock(Integer.parseInt(txtStock.getText()));
            producto.setProveedor(txtProveedor.getText());

            if (idProducto == null) {
                productoService.crearProducto(producto).execute();
                JOptionPane.showMessageDialog(this, "Producto creado exitosamente");
            } else {
                productoService.actualizarProducto(idProducto, producto).execute();
                JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente");
            }

            parent.actualizarTabla();
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: El precio y el stock deben ser números válidos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el producto: " + e.getMessage());
        }
    }

    // Clase para bordes redondeados
    private static class RoundBorder implements Border {
        private int radius;
        private Color color;

        RoundBorder(int radius) {
            this.radius = radius;
        }

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
}
package com.project.frontend.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.project.frontend.productsSystem.controllers.ControllerProduct;
import com.project.frontend.productsSystem.models.Product;

/**
 *
 * @author juan
 */
public class ClientPrincipal extends javax.swing.JFrame {
    private ControllerProduct controllerProduct;

    private JScrollPane jScrollPane;

    /**
     * Creates new form ClientPrincipal
     */
    public ClientPrincipal() {
        initComponents();
        configurarPanelScroll();
        this.controllerProduct= new ControllerProduct();
        try {
            generarPanelProductos();
        }  catch (IOException ex) {
              JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } // Generamos 10 paneles de productos como ejemplo
        
        jButton1.addActionListener(e -> {
            System.out.println("Botón Historial clickeado");
            ClientHistorial cambio = new ClientHistorial();
            cambio.setVisible(true);
        });
        
        jButton2.addActionListener(e -> {
            System.out.println("Botón carrito clickeado");
            ClientCarrito cambio = new ClientCarrito();
            cambio.setVisible(true);
        });
        
        jButton3.addActionListener(e -> {
            System.out.println("Botón carrito clickeado");
            ClientDevoluciones cambio = new ClientDevoluciones();
            cambio.setVisible(true);
        });
        
        
        
        
        jButton1.setBackground(new Color(59, 89, 182));
        jButton1.setForeground(Color.WHITE);
        jButton1.setFont(new Font("Arial", Font.BOLD, 14));
        jButton1.setFocusPainted(false); // Opcional: quitar el rectángulo de foco al hacer clic

        jButton2.setBackground(new Color(59, 89, 182));
        jButton2.setForeground(Color.WHITE);
        jButton2.setFont(new Font("Arial", Font.BOLD, 14));
        jButton2.setFocusPainted(false);

        jButton3.setBackground(new Color(59, 89, 182));
        jButton3.setForeground(Color.WHITE);
        jButton3.setFont(new Font("Arial", Font.BOLD, 14));
        jButton3.setFocusPainted(false);

        jLabel1.setFont(new Font("Verdana", Font.ITALIC, 18));
        jLabel1.setForeground(new Color(150, 0, 0));
    }
    
    /**
     * Configura el panel scroll para hacerlo scrolleable
     */
    private void configurarPanelScroll() {
        // Establecemos un layout de tipo BoxLayout para organizar los paneles verticalmente
        panelScroll.setLayout(new BoxLayout(panelScroll, BoxLayout.Y_AXIS));
        panelScroll.setBackground(Color.WHITE);
        
        // Creamos un JScrollPane y añadimos el panel a él
        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(panelScroll);
        jScrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        
        // Actualizamos las restricciones del GroupLayout para usar jScrollPane en lugar de panelScroll
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) getContentPane().getLayout();
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(49, 49, 49)
                .addComponent(jLabel2)
                .addGap(274, 274, 274)
                .addComponent(jLabel3)
                .addGap(76, 76, 76))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
    }
    
    /**
     * Crea un panel para representar un producto con imagen, información y botones de acción
     * @param id ID del producto
     * @return JPanel configurado con la información del producto
     */
    private JPanel crearPanelProducto(int id, Product product) {
        // Panel principal con borde
        JPanel panelProducto = new JPanel();
        panelProducto.setLayout(new BorderLayout(10, 0));
        panelProducto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true)));
        panelProducto.setBackground(Color.WHITE);
        panelProducto.setMaximumSize(new Dimension(890, 300)); // Ancho máximo y altura fija
        panelProducto.setPreferredSize(new Dimension(890, 300));
        
        // Panel para la imagen (lado izquierdo)
        JPanel panelImagen = new JPanel();
        panelImagen.setBackground(new Color(240, 240, 240));
        panelImagen.setPreferredSize(new Dimension(300, 130));
        
        // Simulamos una imagen con un JLabel
        JLabel labelImagen = new JLabel("Imagen");
        labelImagen.setHorizontalAlignment(JLabel.CENTER);
        panelImagen.add(labelImagen);
        
        // Panel para la información del producto (centro)
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridBagLayout());
        panelInfo.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título del producto
        JLabel labelTitulo = new JLabel(product.getName());
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelInfo.add(labelTitulo, gbc);
        
        // Descripción del producto
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea descripcion = new JTextArea("Descripcion: " + product.getDescription());
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setEditable(false);
        descripcion.setBackground(Color.WHITE);
        descripcion.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInfo.add(descripcion, gbc);

        // stock del producto
        gbc.gridy = 4;
        gbc.weightx = 2.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea stock = new JTextArea("Cantidad de stock: " + String.valueOf(product.getStockQuantity()));
        stock.setLineWrap(true);
        stock.setWrapStyleWord(true);
        stock.setEditable(false);
        stock.setBackground(Color.WHITE);
        stock.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInfo.add(stock, gbc);
        
        // Precio
        gbc.gridy = 8;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel labelPrecio = new JLabel("Precio :" + String.valueOf(product.getPrice()));
        labelPrecio.setFont(new Font("Arial", Font.BOLD, 14));
        labelPrecio.setForeground(new Color(0, 100, 0));
        panelInfo.add(labelPrecio, gbc);

        // categoria del producto
        gbc.gridy = 6;
        gbc.weightx = 2.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea category = new JTextArea("Categoria: " + product.getCategory());
        category.setLineWrap(true);
        category.setWrapStyleWord(true);
        category.setEditable(false);
        category.setBackground(Color.WHITE);
        category.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInfo.add(category, gbc);

        // proveedor del producto
        gbc.gridy = 7;
        gbc.weightx = 2.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea proveedor = new JTextArea("Proveedor: " + product.getStockQuantity());
        proveedor.setLineWrap(true);
        proveedor.setWrapStyleWord(true);
        proveedor.setEditable(false);
        proveedor.setBackground(Color.WHITE);
        proveedor.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInfo.add(proveedor, gbc);


        
        // Panel para los botones (lado derecho)
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setPreferredSize(new Dimension(120, 130));
        
        
        // Botón Eliminar
        JButton btnComprar = new JButton("Comprar");
        btnComprar.setPreferredSize(new Dimension(100, 30));
        btnComprar.addActionListener(e -> {
            System.out.println("Eliminar producto " + id);
        });
        
        panelBotones.add(btnComprar);
        
        // Añadir todos los paneles al panel principal
        panelProducto.add(panelImagen, BorderLayout.WEST);
        panelProducto.add(panelInfo, BorderLayout.CENTER);
        panelProducto.add(panelBotones, BorderLayout.EAST);
        
        return panelProducto;
    }
    
    /**
     * Genera una cantidad específica de paneles de productos y los añade al panel de scroll
     * @param cantidad Número de productos a generar
     * @throws IOException 
     */
    private void generarPanelProductos() throws IOException {

        List<Product> productos = (List<Product>) controllerProduct.operation("GET_ALL", null);


        for (int i = 0; i < productos.size(); i++) {
            JPanel panelProducto = crearPanelProducto(i, productos.get(i));
            
            // Añadimos un separador entre los paneles (espacio vertical)
            panelScroll.add(Box.createRigidArea(new Dimension(0, 10)));
            
            // Añadimos el panel del producto
            panelScroll.add(panelProducto);
        }
        
        // Actualizamos el panel para mostrar los cambios
        panelScroll.revalidate();
        panelScroll.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelScroll = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Historial");

        jButton2.setText("Carrito");

        jButton3.setText("Devoluciones");

        jLabel1.setText("Cliente");

        jLabel2.setText("Monto");

        jLabel3.setText("logout");

        panelScroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelScrollLayout = new javax.swing.GroupLayout(panelScroll);
        panelScroll.setLayout(panelScrollLayout);
        panelScrollLayout.setHorizontalGroup(
            panelScrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 896, Short.MAX_VALUE)
        );
        panelScrollLayout.setVerticalGroup(
            panelScrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(panelScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(49, 49, 49)
                .addComponent(jLabel2)
                .addGap(274, 274, 274)
                .addComponent(jLabel3)
                .addGap(76, 76, 76))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

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
            java.util.logging.Logger.getLogger(ClientPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientPrincipal().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panelScroll;
    // End of variables declaration                   
}
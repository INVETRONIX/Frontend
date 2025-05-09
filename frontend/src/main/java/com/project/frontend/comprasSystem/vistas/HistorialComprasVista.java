package com.project.frontend.comprasSystem.vistas;

import com.project.frontend.comprasSystem.models.Compra;
import com.project.frontend.comprasSystem.services.CompraService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class HistorialComprasVista extends JFrame {
    private CompraService compraService;
    private JTable tablaCompras;
    private DefaultTableModel modeloTabla;
    private boolean esAdmin;
    private JTextField txtBuscar, txtUsuarioId, txtFechaInicio, txtFechaFin, txtTotalMin, txtTotalMax;
    private JButton btnEliminar, btnModificar, btnActualizar, btnBuscar, btnDetalles, btnLimpiarFiltros;
    private JButton btnBusquedaAvanzada;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    public HistorialComprasVista(boolean esAdmin) {
        this.esAdmin = esAdmin;
        compraService = new CompraService();
        initComponents();
        cargarCompras();
    }

    private void initComponents() {
        setTitle("Historial de Compras");
        setSize(1400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Usuario", "Fecha", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaCompras = new JTable(modeloTabla);
        tablaCompras.setRowHeight(32);
        tablaCompras.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablaCompras.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tablaCompras.getTableHeader().setBackground(new Color(0, 200, 180));
        tablaCompras.getTableHeader().setForeground(Color.BLACK);
        tablaCompras.setSelectionBackground(new Color(0, 200, 180));
        tablaCompras.setSelectionForeground(Color.BLACK);
        tablaCompras.setShowHorizontalLines(false);
        tablaCompras.setShowVerticalLines(false);
        tablaCompras.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaCompras.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaCompras.getColumnModel().getColumn(2).setPreferredWidth(180);
        tablaCompras.getColumnModel().getColumn(3).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(tablaCompras);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setBackground(new Color(33, 33, 33));

        btnDetalles = crearBoton("Detalles", "🔍", new Color(0, 200, 180), "Ver detalles de la compra seleccionada");
        btnActualizar = crearBoton("Actualizar", "🔄", new Color(0, 200, 180), "Actualizar la tabla de compras");
        btnActualizar.addActionListener(e -> cargarCompras());
        btnDetalles.addActionListener(e -> mostrarDetallesCompra());

        txtBuscar = new JTextField(8);
        txtBuscar.setToolTipText("Buscar por ID de compra");
        btnBuscar = crearBoton("Buscar por ID", "#", new Color(0, 200, 180), "Buscar compra por ID");
        btnBuscar.addActionListener(e -> buscarCompraPorId());
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) buscarCompraPorId();
            }
        });

        // Filtros avanzados
        if (esAdmin) {
            txtUsuarioId = new JTextField(5);
            txtUsuarioId.setToolTipText("Filtrar por ID de usuario");
            panelBotones.add(new JLabel("Usuario ID:"));
            panelBotones.add(txtUsuarioId);
        }
        txtFechaInicio = new JTextField(8);
        txtFechaInicio.setToolTipText("Fecha de inicio (yyyy-MM-dd)");
        txtFechaFin = new JTextField(8);
        txtFechaFin.setToolTipText("Fecha de fin (yyyy-MM-dd)");
        txtTotalMin = new JTextField(6);
        txtTotalMin.setToolTipText("Total mínimo de la compra");
        txtTotalMax = new JTextField(6);
        txtTotalMax.setToolTipText("Total máximo de la compra");
        panelBotones.add(new JLabel("Fecha Inicio (yyyy-MM-dd):"));
        panelBotones.add(txtFechaInicio);
        panelBotones.add(new JLabel("Fecha Fin (yyyy-MM-dd):"));
        panelBotones.add(txtFechaFin);
        panelBotones.add(new JLabel("Total Min:"));
        panelBotones.add(txtTotalMin);
        panelBotones.add(new JLabel("Total Max:"));
        panelBotones.add(txtTotalMax);
        btnBusquedaAvanzada = crearBoton("Búsqueda Avanzada", "🔎", new Color(0, 200, 180), "Buscar compras por filtros avanzados");
        btnBusquedaAvanzada.addActionListener(e -> buscarPorFiltros());
        panelBotones.add(btnBusquedaAvanzada);

        btnLimpiarFiltros = crearBoton("Limpiar Filtros", "🧹", new Color(33, 150, 243), "Limpiar todos los filtros y mostrar todas las compras");
        btnLimpiarFiltros.addActionListener(e -> limpiarFiltros());
        panelBotones.add(btnLimpiarFiltros);

        panelBotones.add(btnDetalles);
        panelBotones.add(btnActualizar);
        panelBotones.add(txtBuscar);
        panelBotones.add(btnBuscar);

        if (esAdmin) {
            btnEliminar = crearBoton("Eliminar", "🗑️", new Color(200, 50, 50), "Eliminar la compra seleccionada");
            btnEliminar.addActionListener(e -> eliminarCompraSeleccionada());
            btnModificar = crearBoton("Modificar", "✏️", new Color(33, 150, 243), "Modificar la compra seleccionada");
            btnModificar.addActionListener(e -> modificarCompraSeleccionada());
            panelBotones.add(btnModificar);
            panelBotones.add(btnEliminar);
        }

        add(panelBotones, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, String icono, Color color, String tooltip) {
        JButton btn = new JButton(icono + " " + texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btn.setToolTipText(tooltip);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        return btn;
    }

    private void cargarCompras() {
        modeloTabla.setRowCount(0);
        List<Compra> compras;
        if (esAdmin) {
            compras = compraService.obtenerTodasLasCompras();
        } else {
            Long usuarioId = 1L; // Cambia esto por el ID real cuando tengas login
            compras = compraService.obtenerComprasPorUsuario(usuarioId);
        }
        for (Compra compra : compras) {
            modeloTabla.addRow(new Object[]{
                compra.getId(),
                compra.getUsuario() != null ? compra.getUsuario().getNombre() : "N/A",
                compra.getFechaCompra() != null ? compra.getFechaCompra().toString() : "",
                currencyFormat.format(compra.getTotal())
            });
        }
        tablaCompras.clearSelection();
        actualizarEstadoBotones();
    }

    private void limpiarFiltros() {
        if (esAdmin && txtUsuarioId != null) txtUsuarioId.setText("");
        txtFechaInicio.setText("");
        txtFechaFin.setText("");
        txtTotalMin.setText("");
        txtTotalMax.setText("");
        txtBuscar.setText("");
        cargarCompras();
    }

    private void actualizarEstadoBotones() {
        boolean haySeleccion = tablaCompras.getSelectedRow() != -1;
        if (btnEliminar != null) btnEliminar.setEnabled(haySeleccion);
        if (btnModificar != null) btnModificar.setEnabled(haySeleccion);
        btnDetalles.setEnabled(haySeleccion);
    }

    private void eliminarCompraSeleccionada() {
        int fila = tablaCompras.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una compra para eliminar.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar la compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        Long id = (Long) modeloTabla.getValueAt(fila, 0);
        compraService.eliminarCompra(id);
        modeloTabla.removeRow(fila);
        JOptionPane.showMessageDialog(this, "Compra eliminada.");
        actualizarEstadoBotones();
    }

    private void buscarCompraPorId() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            cargarCompras();
            return;
        }
        try {
            Long id = Long.parseLong(texto);
            modeloTabla.setRowCount(0);
            List<Compra> compras;
            if (esAdmin) {
                compras = compraService.obtenerTodasLasCompras();
            } else {
                Long usuarioId = 1L;
                compras = compraService.obtenerComprasPorUsuario(usuarioId);
            }
            for (Compra compra : compras) {
                if (compra.getId().equals(id)) {
                    modeloTabla.addRow(new Object[]{
                        compra.getId(),
                        compra.getUsuario() != null ? compra.getUsuario().getNombre() : "N/A",
                        compra.getFechaCompra() != null ? compra.getFechaCompra().toString() : "",
                        currencyFormat.format(compra.getTotal())
                    });
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
        tablaCompras.clearSelection();
        actualizarEstadoBotones();
    }

    private void modificarCompraSeleccionada() {
        int fila = tablaCompras.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una compra para modificar.");
            return;
        }
        Long id = (Long) modeloTabla.getValueAt(fila, 0);
        Compra compra = obtenerCompraPorId(id);
        if (compra != null) {
            EditarCompraVista editar = new EditarCompraVista(this, compra);
            editar.setVisible(true);
            cargarCompras();
        }
    }

    private Compra obtenerCompraPorId(Long id) {
        List<Compra> compras = esAdmin ? compraService.obtenerTodasLasCompras() : compraService.obtenerComprasPorUsuario(1L);
        for (Compra c : compras) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }

    private void mostrarDetallesCompra() {
        int fila = tablaCompras.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una compra para ver detalles.");
            return;
        }
        Long id = (Long) modeloTabla.getValueAt(fila, 0);
        Compra compra = obtenerCompraPorId(id);
        if (compra != null) {
            JOptionPane.showMessageDialog(this,
                "ID: " + compra.getId() +
                "\nUsuario: " + (compra.getUsuario() != null ? compra.getUsuario().getNombre() : "N/A") +
                "\nFecha: " + (compra.getFechaCompra() != null ? compra.getFechaCompra().toString() : "") +
                "\nTotal: " + currencyFormat.format(compra.getTotal()),
                "Detalles de Compra",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void buscarPorFiltros() {
        Long usuarioId = null;
        if (esAdmin && txtUsuarioId != null && txtUsuarioId.getText() != null && !txtUsuarioId.getText().isEmpty()) {
            try { usuarioId = Long.parseLong(txtUsuarioId.getText().trim()); } catch (Exception ignored) {}
        } else if (!esAdmin) {
            usuarioId = 1L; // o el id del usuario logueado
        }
        String fechaInicio = txtFechaInicio.getText().trim();
        String fechaFin = txtFechaFin.getText().trim();
        Double totalMin = null, totalMax = null;
        try { if (!txtTotalMin.getText().trim().isEmpty()) totalMin = Double.parseDouble(txtTotalMin.getText().trim()); } catch (Exception ignored) {}
        try { if (!txtTotalMax.getText().trim().isEmpty()) totalMax = Double.parseDouble(txtTotalMax.getText().trim()); } catch (Exception ignored) {}

        List<Compra> compras = compraService.buscarComprasPorFiltros(usuarioId, fechaInicio, fechaFin, totalMin, totalMax);
        modeloTabla.setRowCount(0);
        for (Compra compra : compras) {
            modeloTabla.addRow(new Object[]{
                compra.getId(),
                compra.getUsuario() != null ? compra.getUsuario().getNombre() : "N/A",
                compra.getFechaCompra() != null ? compra.getFechaCompra().toString() : "",
                currencyFormat.format(compra.getTotal())
            });
        }
        tablaCompras.clearSelection();
        actualizarEstadoBotones();
    }
} 
package com.project.frontend.comprasSystem.vistas;

import com.project.frontend.comprasSystem.models.Compra;
import com.project.frontend.comprasSystem.services.CompraService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistorialComprasVista extends JFrame {
    private CompraService compraService;
    private JTable tablaCompras;
    private DefaultTableModel modeloTabla;
    private boolean esAdmin;
    private JTextField txtBuscar, txtUsuarioId, txtFechaInicio, txtFechaFin, txtTotalMin, txtTotalMax;
    private JButton btnEliminar, btnModificar, btnActualizar, btnBuscar, btnDetalles;

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

        JScrollPane scrollPane = new JScrollPane(tablaCompras);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setBackground(new Color(33, 33, 33));

        btnDetalles = new JButton("Detalles");
        btnDetalles.setBackground(new Color(0, 200, 180));
        btnDetalles.setForeground(Color.WHITE);
        btnDetalles.addActionListener(e -> mostrarDetallesCompra());

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(0, 200, 180));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.addActionListener(e -> cargarCompras());

        txtBuscar = new JTextField(8);
        btnBuscar = new JButton("Buscar por ID");
        btnBuscar.setBackground(new Color(0, 200, 180));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarCompraPorId());

        // Filtros avanzados
        if (esAdmin) {
            txtUsuarioId = new JTextField(5);
            panelBotones.add(new JLabel("Usuario ID:"));
            panelBotones.add(txtUsuarioId);
        }
        txtFechaInicio = new JTextField(8);
        txtFechaFin = new JTextField(8);
        txtTotalMin = new JTextField(6);
        txtTotalMax = new JTextField(6);
        panelBotones.add(new JLabel("Fecha Inicio (yyyy-MM-dd):"));
        panelBotones.add(txtFechaInicio);
        panelBotones.add(new JLabel("Fecha Fin (yyyy-MM-dd):"));
        panelBotones.add(txtFechaFin);
        panelBotones.add(new JLabel("Total Min:"));
        panelBotones.add(txtTotalMin);
        panelBotones.add(new JLabel("Total Max:"));
        panelBotones.add(txtTotalMax);
        JButton btnBusquedaAvanzada = new JButton("Búsqueda Avanzada");
        btnBusquedaAvanzada.setBackground(new Color(0, 200, 180));
        btnBusquedaAvanzada.setForeground(Color.WHITE);
        btnBusquedaAvanzada.addActionListener(e -> buscarPorFiltros());
        panelBotones.add(btnBusquedaAvanzada);

        panelBotones.add(btnDetalles);
        panelBotones.add(btnActualizar);
        panelBotones.add(txtBuscar);
        panelBotones.add(btnBuscar);

        if (esAdmin) {
            btnEliminar = new JButton("Eliminar");
            btnEliminar.setBackground(new Color(200, 50, 50));
            btnEliminar.setForeground(Color.WHITE);
            btnEliminar.addActionListener(e -> eliminarCompraSeleccionada());

            btnModificar = new JButton("Modificar");
            btnModificar.setBackground(new Color(33, 150, 243));
            btnModificar.setForeground(Color.WHITE);
            btnModificar.addActionListener(e -> modificarCompraSeleccionada());

            panelBotones.add(btnModificar);
            panelBotones.add(btnEliminar);
        }

        add(panelBotones, BorderLayout.SOUTH);
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
                compra.getFechaCompra(),
                compra.getTotal()
            });
        }
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
                        compra.getFechaCompra(),
                        compra.getTotal()
                    });
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
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
                "\nFecha: " + compra.getFechaCompra() +
                "\nTotal: " + compra.getTotal(),
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
                compra.getFechaCompra(),
                compra.getTotal()
            });
        }
    }
} 
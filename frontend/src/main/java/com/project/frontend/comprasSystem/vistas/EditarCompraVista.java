package com.project.frontend.comprasSystem.vistas;

import com.project.frontend.comprasSystem.models.Compra;
import com.project.frontend.comprasSystem.services.CompraService;
import javax.swing.*;
import java.awt.*;

public class EditarCompraVista extends JDialog {
    private final CompraService compraService;
    private final Compra compra;
    private JTextField txtTotal;
    private JButton btnGuardar, btnCancelar;

    public EditarCompraVista(JFrame parent, Compra compra) {
        super(parent, "Editar Compra", true);
        this.compraService = new CompraService();
        this.compra = compra;
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setSize(400, 250);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("ID:"));
        panel.add(new JLabel(String.valueOf(compra.getId())));

        panel.add(new JLabel("Total:"));
        txtTotal = new JTextField(String.valueOf(compra.getTotal()));
        panel.add(txtTotal);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(0, 200, 180));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> guardarCambios());

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(200, 50, 50));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void guardarCambios() {
        try {
            double nuevoTotal = Double.parseDouble(txtTotal.getText());
            compra.setTotal(nuevoTotal);
            compraService.actualizarCompra(compra.getId(), compra);
            JOptionPane.showMessageDialog(this, "Compra actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 
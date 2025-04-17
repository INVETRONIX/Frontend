package com.project.frontend.shared.handlers;

import javax.swing.JOptionPane;


public class HandlerMessage {

    public static void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, tipo);
    }
}

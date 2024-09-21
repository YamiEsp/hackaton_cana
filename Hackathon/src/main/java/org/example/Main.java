package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        // Crear el frame
        JFrame frame = new JFrame("Coordenadas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes

        // Crear el label
        JLabel label = new JLabel("Introduce tus coordenadas:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.add(label, gbc);

        // Label para X
        JLabel labelX = new JLabel("X:");
        gbc.gridx = 0; // Primera columna (label a la izquierda)
        gbc.gridy = 1; // Primera fila
        gbc.anchor = GridBagConstraints.LINE_END; // Alinea a la derecha de la celda
        frame.add(labelX, gbc);

        // Campo de texto para X
        JTextField inputFieldX = new JTextField(15);
        gbc.gridx = 1; // Segunda columna (textfield a la derecha del label)
        gbc.gridy = 1; // Primera fila
        gbc.anchor = GridBagConstraints.LINE_START; // Alinea a la izquierda de la celda
        frame.add(inputFieldX, gbc);

        // Label para Y
        JLabel labelY = new JLabel("Y:");
        gbc.gridx = 0; // Primera columna (label a la izquierda)
        gbc.gridy = 2; // Segunda fila
        gbc.anchor = GridBagConstraints.LINE_END; // Alinea a la derecha de la celda
        frame.add(labelY, gbc);

        // Campo de texto para Y
        JTextField inputFieldY = new JTextField(15);
        gbc.gridx = 1; // Segunda columna (textfield a la derecha del label)
        gbc.gridy = 2; // Segunda fila
        gbc.anchor = GridBagConstraints.LINE_START; // Alinea a la izquierda de la celda
        frame.add(inputFieldY, gbc);

        // Botón
        JButton nextButton = new JButton("Siguiente");
        gbc.gridx = 1; // Segunda columna
        gbc.gridy = 2; // Tercera fila
        gbc.anchor = GridBagConstraints.LINE_START; // Alinea el botón a la izquierda
        frame.add(nextButton, gbc);

        // Mostrar la ventana
        frame.setVisible(true);
    }
}

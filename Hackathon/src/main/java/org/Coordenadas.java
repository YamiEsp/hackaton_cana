package org;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Coordenadas {

    public static void main(String[] args) {
        // Crear el frame
        JFrame frame = new JFrame("Coordenadas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes

        // Crear el label
        JLabel label = new JLabel("Introduce tus coordenadas:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(label, gbc);

        // Label para X
        JLabel labelX = new JLabel("X:");
        gbc.gridx = 0; // Primera columna (label a la izquierda)
        gbc.gridy = 1; // Primera fila
        gbc.anchor = GridBagConstraints.LINE_END; // Alinea a la derecha de la celda
        frame.add(labelX, gbc);

        // ComboBox para X (se llenará con datos de la base de datos)
        JComboBox<String> comboBoxX = new JComboBox<>();
        gbc.gridx = 1; // Segunda columna (combobox a la derecha del label)
        gbc.gridy = 1; // Primera fila
        gbc.anchor = GridBagConstraints.LINE_START; // Alinea a la izquierda de la celda
        frame.add(comboBoxX, gbc);

        // Label para Y
        JLabel labelY = new JLabel("Y:");
        gbc.gridx = 0; // Primera columna (label a la izquierda)
        gbc.gridy = 2; // Segunda fila
        gbc.anchor = GridBagConstraints.LINE_END; // Alinea a la derecha de la celda
        frame.add(labelY, gbc);

        // ComboBox para Y (se llenará con datos de la base de datos)
        JComboBox<String> comboBoxY = new JComboBox<>();
        gbc.gridx = 1; // Segunda columna (combobox a la derecha del label)
        gbc.gridy = 2; // Segunda fila
        gbc.anchor = GridBagConstraints.LINE_START; // Alinea a la izquierda de la celda
        frame.add(comboBoxY, gbc);

        // Botón
        JButton nextButton = new JButton("Siguiente");
        gbc.gridx = 1; // Segunda columna
        gbc.gridy = 3; // Tercera fila
        gbc.anchor = GridBagConstraints.LINE_START; // Alinea el botón a la izquierda
        frame.add(nextButton, gbc);

        // Llamar al método que llena los ComboBox con datos de la base de datos
        llenarComboBoxDesdeBaseDatos(comboBoxX, comboBoxY);

        // Mostrar la ventana
        frame.setVisible(true);
    }

    // Método para obtener datos de la base de datos y llenar los ComboBox
    public static void llenarComboBoxDesdeBaseDatos(JComboBox<String> comboBoxX, JComboBox<String> comboBoxY) {
        String url = "jdbc:sqlite:Hackathon/src/main/databases/Database.accdb"; // Aquí cambia la ruta según tu archivo de base de datos
        String query = "SELECT coordX, coordY FROM Coordenadas"; // Aquí cambia 'Coordenadas' por tu tabla y los campos que necesitas

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Limpiar los ComboBox antes de llenarlos
            comboBoxX.removeAllItems();
            comboBoxY.removeAllItems();

            // Llenar los ComboBox con los datos de la base de datos
            while (rs.next()) {
                String coordX = rs.getString("coordX");
                String coordY = rs.getString("coordY");
                comboBoxX.addItem(coordX);
                comboBoxY.addItem(coordY);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

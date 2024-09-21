package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;

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
        gbc.gridy = 3; // Tercera fila
        gbc.anchor = GridBagConstraints.LINE_START; // Alinea el botón a la izquierda
        frame.add(nextButton, gbc);

        // Acción del botón
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String coordenadaX = inputFieldX.getText();
                String coordenadaY = inputFieldY.getText();

                // Aquí defines la ruta a tu base de datos
                String dbPath = "Hackathon\\src\\main\\databases\\Cultivos.accdb"; // Cambia esto por la ruta real a tu archivo .accdb

                // Mostrar los valores en la consola para depuración
                System.out.println("Valor X ingresado: " + coordenadaX);
                System.out.println("Valor Y ingresado: " + coordenadaY);
                System.out.println("Ruta de la base de datos: " + dbPath);

                // Llamar a realizarConsulta pasándole las coordenadas y la ruta
                realizarConsulta(coordenadaX, coordenadaY, dbPath);
            }
        });

        // Mostrar la ventana
        frame.setVisible(true);
    }

    // Método para realizar la consulta a la base de datos
    private static void realizarConsulta(String x, String y, String dbPath) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            // Conectar a la base de datos usando el path que se pasa como argumento
            System.out.println("Intentando conectar a la base de datos en: " + dbPath);
            conn = Bd.Conexion(dbPath);
    
            // Verificar si la conexión es nula
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Conexión a la base de datos fallida. Verifique la ruta de la base de datos.");
                return;
            }
    
            // Crear la consulta SQL usando los valores de X e Y
            String sql = "SELECT * FROM coordenadas WHERE x = ? AND y = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, x);
            stmt.setString(2, y);
    
            // Ejecutar la consulta
            rs = stmt.executeQuery();
    
            // Procesar los resultados
            if (rs.next()) {
                String resultado = "Resultado encontrado: X = " + rs.getString("x") + ", Y = " + rs.getString("y");
                JOptionPane.showMessageDialog(null, resultado);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron resultados para las coordenadas ingresadas.");
            }
    
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

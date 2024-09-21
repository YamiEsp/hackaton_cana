package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        // Crear el frame
        JFrame frame = new JFrame("TerraDrone");
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

        JLabel labelEdo = new JLabel("Estado:");
        gbc.gridx = 0; // Primera columna (label a la izquierda)
        gbc.gridy = 1; // Primera fila
        gbc.anchor = GridBagConstraints.LINE_END; // Alinea a la derecha de la celda
        frame.add(labelEdo, gbc);

        // ComboBox para el estado
        JComboBox<String> comboBoxEdo = new JComboBox<>();
        gbc.gridx = 1; // Segunda columna (combobox a la derecha del label)
        gbc.gridy = 1; // Primera fila
        gbc.anchor = GridBagConstraints.LINE_START; // Alinea a la izquierda de la celda
        frame.add(comboBoxEdo, gbc);

        JLabel labelCader = new JLabel("Cader:");
        gbc.gridx = 0; // Primera columna (label a la izquierda)
        gbc.gridy = 2; // Segunda fila
        gbc.anchor = GridBagConstraints.LINE_END; // Alinea a la derecha de la celda
        frame.add(labelCader, gbc);

        // ComboBox para Cader
        JComboBox<String> comboBoxCader = new JComboBox<>();
        gbc.gridx = 1; // Segunda columna (combobox a la derecha del label)
        gbc.gridy = 2; // Segunda fila
        gbc.anchor = GridBagConstraints.LINE_START; // Alinea a la izquierda de la celda
        frame.add(comboBoxCader, gbc);

        JLabel labelMunic = new JLabel("Municipio:");
        gbc.gridx = 0; // Primera columna (label a la izquierda)
        gbc.gridy = 3; // Tercera fila
        gbc.anchor = GridBagConstraints.LINE_END; // Alinea a la derecha de la celda
        frame.add(labelMunic, gbc);

        // ComboBox para Municipio
        JComboBox<String> comboBoxMunic = new JComboBox<>();
        gbc.gridx = 1; // Segunda columna (combobox a la derecha del label)
        gbc.gridy = 3; // Tercera fila
        gbc.anchor = GridBagConstraints.LINE_START; // Alinea a la izquierda de la celda
        frame.add(comboBoxMunic, gbc);

        // Botón
        JButton nextButton = new JButton("Siguiente");
        gbc.gridx = 1; // Segunda columna
        gbc.gridy = 4; // Cuarta fila
        gbc.anchor = GridBagConstraints.LINE_START; // Alinea el botón a la izquierda
        frame.add(nextButton, gbc);

        JLabel labelPlantas = new JLabel("plantas");
        gbc.gridx = 0; // Primera columna (label a la izquierda)
        gbc.gridy = 5; // Tercera fila
        gbc.anchor = GridBagConstraints.LINE_END; // Alinea a la derecha de la celda
        frame.add(labelPlantas, gbc);

        // Llamar al método que llena el ComboBox de estados con datos de la base de
        // datos
        llenarComboBoxEstados(comboBoxEdo);

        // Acción al seleccionar un estado
        comboBoxEdo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el estado seleccionado
                String estadoSeleccionado = (String) comboBoxEdo.getSelectedItem();
                // Llenar el ComboBox de municipios basado en el estado seleccionado
                llenarComboBoxCader(comboBoxCader, estadoSeleccionado);
            }
        });

        comboBoxCader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el estado seleccionado
                String municipioSeleccionado = (String) comboBoxCader.getSelectedItem();
                // Llenar el ComboBox de municipios basado en el estado seleccionado
                llenarComboBoxMunicipios(comboBoxMunic, municipioSeleccionado);
            }
        });

        // Acción del botón
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener las selecciones de los ComboBox
                String caderSeleccionado = (String) comboBoxCader.getSelectedItem();
                String estadoSeleccionado = (String) comboBoxEdo.getSelectedItem();
                String municipioSeleccionado = (String) comboBoxMunic.getSelectedItem(); // O cualquier otra columna
        
                // Llamar al método que realiza la consulta en base a los ComboBox
                realizarConsultaFiltrada(caderSeleccionado, estadoSeleccionado, municipioSeleccionado, labelPlantas);
            }
        });

        // Mostrar la ventana
        frame.setVisible(true);
    }

    // Método para llenar el ComboBox de estados
    private static void llenarComboBoxEstados(JComboBox<String> comboBoxEdo) {
        String url = "jdbc:ucanaccess://Hackathon/src/main/databases/Cultivos.accdb"; // Cambia la ruta según tu archivo

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM NORM_TT_Estado")) {

            // Limpiar el ComboBox antes de llenarlo
            comboBoxEdo.removeAllItems();

            // Llenar el JComboBox con los datos de la base de datos
            while (rs.next()) {
                String estado = rs.getString("Nomestado"); // Cambia "Nomestado" por el nombre correcto de la columna
                comboBoxEdo.addItem(estado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para llenar el ComboBox de municipios basado en el estado seleccionado
    private static void llenarComboBoxCader(JComboBox<String> comboBoxCader, String estado) {
        String url = "jdbc:ucanaccess://Hackathon/src/main/databases/Cultivos.accdb"; // Cambia la ruta según tu archivo

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT DISTINCT Nomcader FROM Cierre_agr_mun_2023 WHERE Nomestado = '"
                                + estado.substring(0, 1).toUpperCase() + estado.substring(1).toLowerCase() + "'")) {

            System.out.println(estado.substring(0, 1).toUpperCase() + estado.substring(1).toLowerCase());
            // stmt.setString(1, estado); // Establece el parámetro del estado

            try (ResultSet rs = stmt.executeQuery()) {
                // Limpiar el ComboBox antes de llenarlo
                comboBoxCader.removeAllItems();

                // Llenar el JComboBox con los datos de la base de datos
                while (rs.next()) {
                    String cader = rs.getString("Nomcader"); // Cambia "Nommunicipio" por el nombre correcto de la
                                                             // columna
                    comboBoxCader.addItem(cader);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void llenarComboBoxMunicipios(JComboBox<String> comboBoxMunic, String cader) {
        String url = "jdbc:ucanaccess://Hackathon/src/main/databases/Cultivos.accdb"; // Cambia la ruta según tu archivo

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT DISTINCT Nommunicipio FROM Cierre_agr_mun_2023 WHERE Nomcader = '"
                                + cader.substring(0, 1).toUpperCase() + cader.substring(1).toLowerCase() + "'")) {

            System.out.println(cader.substring(0, 1).toUpperCase() + cader.substring(1).toLowerCase());
            // stmt.setString(1, estado); // Establece el parámetro del estado

            try (ResultSet rs = stmt.executeQuery()) {
                // Limpiar el ComboBox antes de llenarlo
                comboBoxMunic.removeAllItems();

                // Llenar el JComboBox con los datos de la base de datos
                while (rs.next()) {
                    String munic = rs.getString("Nommunicipio"); // Cambia "Nommunicipio" por el nombre correcto de la
                                                                 // columna
                    comboBoxMunic.addItem(munic);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void realizarConsultaFiltrada(String cader, String estado, String municipio, JLabel labelPlantas) {
        String url = "jdbc:ucanaccess://Hackathon/src/main/databases/Cultivos.accdb"; // Cambia la ruta según tu archivo
    
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Cierre_agr_mun_2023 WHERE Nomcader = ? AND Nomestado = ? AND Nommunicipio = ?")) {
    
            // Formatear estado
            String estadoFormateado = estado.substring(0, 1).toUpperCase() + estado.substring(1).toLowerCase();
    
            // Establecer los parámetros
            stmt.setString(1, cader);
            stmt.setString(2, estadoFormateado);
            stmt.setString(3, municipio);
    
            try (ResultSet rs = stmt.executeQuery()) {
                // Limpiar el JLabel antes de mostrar nuevos resultados
                labelPlantas.setText("");
    
                // Procesar los resultados
                if (rs.next()) {
                    String resultado = "Resultado encontrado: " + rs.getString("Nommunicipio"); // Cambia "Nommunicipio" por el campo deseado
                    labelPlantas.setText(resultado);
                } else {
                    labelPlantas.setText("No se encontraron resultados para la búsqueda.");
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            labelPlantas.setText("Error al conectarse a la base de datos.");
        }
    }
    
}

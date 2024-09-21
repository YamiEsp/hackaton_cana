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

        // Llamar al método que llena el ComboBox de estados con datos de la base de datos
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
                // Obtener la selección del ComboBox de municipios
                String municipioSeleccionado = (String) comboBoxMunic.getSelectedItem();

                // Aquí defines la ruta a tu base de datos
                String dbPath = "Hackathon\\src\\main\\databases\\Cultivos.accdb"; // Cambia esto por la ruta real

                // Mostrar los valores en la consola para depuración
                System.out.println("Municipio seleccionado: " + municipioSeleccionado);
                System.out.println("Ruta de la base de datos: " + dbPath);

                // Realizar consulta usando el municipio seleccionado
                realizarConsulta(municipioSeleccionado, dbPath);
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
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Cierre_agr_mun_2023 WHERE Nomestado = '"+estado.substring(0, 1).toUpperCase() + estado.substring(1).toLowerCase()+"'")) {

            System.out.println(estado.substring(0, 1).toUpperCase() + estado.substring(1).toLowerCase());
            //stmt.setString(1, estado); // Establece el parámetro del estado

            try (ResultSet rs = stmt.executeQuery()) {
                // Limpiar el ComboBox antes de llenarlo
                comboBoxCader.removeAllItems();

                // Llenar el JComboBox con los datos de la base de datos
                while (rs.next()) {
                    String cader = rs.getString("Nomcader"); // Cambia "Nommunicipio" por el nombre correcto de la columna
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
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Cierre_agr_mun_2023 WHERE Nomcader = '"+ cader.substring(0, 1).toUpperCase() + cader.substring(1).toLowerCase()+"'")) {

            System.out.println(cader.substring(0, 1).toUpperCase() + cader.substring(1).toLowerCase());
            //stmt.setString(1, estado); // Establece el parámetro del estado

            try (ResultSet rs = stmt.executeQuery()) {
                // Limpiar el ComboBox antes de llenarlo
                comboBoxMunic.removeAllItems();

                // Llenar el JComboBox con los datos de la base de datos
                while (rs.next()) {
                    String munic = rs.getString("Nommunicipio"); // Cambia "Nommunicipio" por el nombre correcto de la columna
                    comboBoxMunic.addItem(munic);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para realizar la consulta a la base de datos
    private static void realizarConsulta(String municipio, String dbPath) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Conectar a la base de datos usando el path que se pasa como argumento
            System.out.println("Intentando conectar a la base de datos en: " + dbPath);
            conn = Bd.Conexion(dbPath);

            // Verificar si la conexión es nula
            if (conn == null) {
                JOptionPane.showMessageDialog(null,
                        "Conexión a la base de datos fallida. Verifique la ruta de la base de datos.");
                return;
            }

            // Crear la consulta SQL usando el municipio seleccionado
            String sql = "SELECT * FROM Cierre_agr_mun_2023 WHERE Nommunicipio = ?"; // Cambia "TuTabla" por el nombre correcto de la tabla
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, municipio);

            // Ejecutar la consulta
            rs = stmt.executeQuery();

            // Procesar los resultados
            if (rs.next()) {
                String resultado = "Resultado encontrado para el municipio: " + rs.getString("Nommunicipio"); // Cambia "Nommunicipio" por el campo deseado
                JOptionPane.showMessageDialog(null, resultado);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron resultados para el municipio seleccionado.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

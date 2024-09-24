package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main extends JFrame {
    private static JTextArea textAreaCultivos; // JTextArea para mostrar cultivos
    private static JTextArea textAreaPlagas; // JTextArea para mostrar plagas

    public static void main(String[] args) {
        // Crear el frame
        JFrame frame = new JFrame("TerraDrone");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
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

        // JTextArea para mostrar cultivos
        textAreaCultivos = new JTextArea(10, 30); // Ajusta el tamaño según sea necesario
        textAreaCultivos.setEditable(false); // Para que no se pueda editar
        textAreaCultivos.setVisible(false); // Inicialmente oculto
        gbc.gridx = 0; // Ajusta según donde quieras que aparezca
        gbc.gridy = 5; // Ajusta según la fila
        gbc.gridwidth = 2; // Ocupa dos columnas
        frame.add(new JScrollPane(textAreaCultivos), gbc); // Usa JScrollPane para que sea desplazable

        // JTextArea para mostrar plagas
        textAreaPlagas = new JTextArea(10, 30); // Ajusta el tamaño según sea necesario
        textAreaPlagas.setEditable(false); // Para que no se pueda editar
        textAreaPlagas.setVisible(false); // Inicialmente oculto
        gbc.gridx = 0; // Ajusta según donde quieras que aparezca
        gbc.gridy = 6; // Ajusta según la fila
        gbc.gridwidth = 2; // Ocupa dos columnas
        frame.add(new JScrollPane(textAreaPlagas), gbc); // Usa JScrollPane para que sea desplazable

        // Llamar al método que llena el ComboBox de estados con datos de la base de datos
        llenarComboBoxEstados(comboBoxEdo);

        // Acción al seleccionar un estado
        comboBoxEdo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el estado seleccionado
                String estadoSeleccionado = (String) comboBoxEdo.getSelectedItem();
                // Llenar el ComboBox de cader basado en el estado seleccionado
                llenarComboBoxCader(comboBoxCader, estadoSeleccionado);
            }
        });

        // Acción al seleccionar cader
        comboBoxCader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el cader seleccionado
                String caderSeleccionado = (String) comboBoxCader.getSelectedItem();
                // Llenar el ComboBox de municipios basado en el cader seleccionado
                llenarComboBoxMunic(comboBoxMunic, caderSeleccionado);
            }
        });

        // Acción del botón
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la selección del ComboBox de municipios
                String municipioSeleccionado = (String) comboBoxMunic.getSelectedItem();
                String caderSeleccionado = (String) comboBoxCader.getSelectedItem();
                String estadoSeleccionado = (String) comboBoxEdo.getSelectedItem();

                // Aquí defines la ruta a tu base de datos
                String dbPath = "Hackathon\\src\\main\\databases\\Cultivos.accdb"; // Cambia esto por la ruta real

                // Mostrar los valores en la consola para depuración
                System.out.println("Municipio seleccionado: " + municipioSeleccionado);
                System.out.println("Cader seleccionado: " + caderSeleccionado);
                System.out.println("Estado seleccionado: " + estadoSeleccionado);
                System.out.println("Ruta de la base de datos: " + dbPath);

                // Realizar consulta usando el municipio seleccionado
                String cultivo = realizarConsultaCultivo(municipioSeleccionado, caderSeleccionado, estadoSeleccionado);

                // Si se encontró un cultivo, hacer visible el JTextArea de cultivos
                if (!cultivo.isEmpty()) {
                    textAreaCultivos.setVisible(true);
                }

                // Buscar plagas para el cultivo obtenido
                buscarPlagasPorCultivo(cultivo, textAreaPlagas);
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

    // Método para llenar el ComboBox de cader basado en el estado seleccionado
    private static void llenarComboBoxCader(JComboBox<String> comboBoxCader, String estado) {
        String url = "jdbc:ucanaccess://Hackathon/src/main/databases/Cultivos.accdb"; // Cambia la ruta según tu archivo

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT DISTINCT Nomcader FROM Cierre_agr_mun_2023 WHERE Nomestado = ?")) {

            stmt.setString(1, estado.substring(0, 1).toUpperCase() + estado.substring(1).toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                // Limpiar el ComboBox antes de llenarlo
                comboBoxCader.removeAllItems();

                // Llenar el JComboBox con los datos de la base de datos
                while (rs.next()) {
                    String cader = rs.getString("Nomcader"); // Cambia "Nomcader" por el nombre correcto de la columna
                    comboBoxCader.addItem(cader);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para llenar el ComboBox de municipios basado en el cader seleccionado
    private static void llenarComboBoxMunic(JComboBox<String> comboBoxMunic, String cader) {
        String url = "jdbc:ucanaccess://Hackathon/src/main/databases/Cultivos.accdb"; // Cambia la ruta según tu archivo

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT DISTINCT Nommunicipio FROM Cierre_agr_mun_2023 WHERE Nomcader = ?")) {

            stmt.setString(1, cader.substring(0, 1).toUpperCase() + cader.substring(1).toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                // Limpiar el ComboBox antes de llenarlo
                comboBoxMunic.removeAllItems();

                // Llenar el JComboBox con los datos de la base de datos
                while (rs.next()) {
                    String municipio = rs.getString("Nommunicipio"); // Cambia "Nommunicipio" por el nombre correcto de la columna
                    comboBoxMunic.addItem(municipio);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para realizar la consulta de cultivos
    private static String realizarConsultaCultivo(String municipio, String cader, String estado) {
        String url = "jdbc:ucanaccess://Hackathon/src/main/databases/Cultivos.accdb"; // Cambia la ruta según tu archivo
        StringBuilder cultivos = new StringBuilder(); // Usar StringBuilder para concatenar cultivos
        String cultivoFinal = "";

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT Nomcultivo FROM Cierre_agr_mun_2023 WHERE Nommunicipio = ? AND Nomcader = ? AND Nomestado = ?")) {

            stmt.setString(1, municipio);
            stmt.setString(2, cader);
            stmt.setString(3, estado);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean hayResultados = false;

                while (rs.next()) {
                    hayResultados = true;
                    String cultivo = rs.getString("Nomcultivo"); // Cambia "Nomcultivo" por el campo correcto de la columna
                    cultivos.append(cultivo).append("\n"); // Agregar el cultivo al StringBuilder con salto de línea
                }

                if (hayResultados) {
                    // Mostrar todos los cultivos en el JTextArea
                    textAreaCultivos.setText(cultivos.toString());
                    cultivoFinal = cultivos.toString(); // Asignar a cultivoFinal para usarlo más tarde
                } else {
                    textAreaCultivos.setText("No se encontraron cultivos para la búsqueda.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return cultivoFinal; // Retornar el último cultivo encontrado
    }

    // Método para buscar plagas por cultivo
    private static void buscarPlagasPorCultivo(String cultivo, JTextArea textAreaPlagas) {
        String url = "jdbc:ucanaccess://Hackathon/src/main/databases/Plagas.accdb"; // Cambia la ruta según tu archivo

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT 'Plaga 1' FROM Hoja1 WHERE Cultivo = ?")) {

            stmt.setString(1, cultivo.trim()); // Asegurarse de que no haya espacios en blanco

            try (ResultSet rs = stmt.executeQuery()) {
                StringBuilder plagas = new StringBuilder("Plagas encontradas:\n");

                boolean hayPlagas = false;

                while (rs.next()) {
                    hayPlagas = true;
                    String plaga = rs.getString("Plaga 1");
                    plagas.append(plaga).append("\n"); // Agregar plaga con salto de línea
                }

                if (hayPlagas) {
                    textAreaPlagas.setText(plagas.toString()); // Mostrar plagas en el JTextArea
                    textAreaPlagas.setVisible(true); // Hacer visible el JTextArea de plagas
                } else {
                    textAreaPlagas.setText("No se encontraron plagas para el cultivo seleccionado.");
                    textAreaPlagas.setVisible(true); // Hacer visible el JTextArea de plagas
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

package org.example;

import java.io.File;
import java.sql.*;

public class Bd {

    // Método para establecer la conexión
    public static Connection Conexion(String dbpath) throws ClassNotFoundException {
        Connection connection = null;
        try {
            // Cargar el driver UCanAccess
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            System.out.println("Driver cargado correctamente.");

            // Usar un path relativo para acceder a la base de datos
            File dbFile = new File(dbpath);

            // Verificar si el archivo de la base de datos existe
            if (!dbFile.exists()) {
                System.err.println("Error: La base de datos no existe en la ruta: " + dbFile.getAbsolutePath());
                return null;
            }

            // Construir la URL JDBC con la ruta absoluta
            String url = "jdbc:ucanaccess://" + dbFile.getAbsolutePath();
            System.out.println("Conectando a la base de datos en: " + url);

            // Establecer la conexión a la base de datos
            connection = DriverManager.getConnection(url);
            System.out.println("Conexión a la base de datos " + dbFile.getName() + " establecida.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver UCanAccess no encontrado.");
            throw e;
        } catch (SQLException e) {
            System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
        }
        return connection;
    }
}

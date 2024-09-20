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

            // Usar un path relativo para acceder a la base de datos
            File dbFile = new File(dbpath);

            // Construir la URL JDBC con la ruta relativa
            String url = "jdbc:ucanaccess://" + dbFile.getAbsolutePath();

            // Establecer la conexión a la base de datos
            connection = DriverManager.getConnection(url);

            System.out.println("Conexión a la base de datos " + dbFile.getName() + " establecida");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    
}

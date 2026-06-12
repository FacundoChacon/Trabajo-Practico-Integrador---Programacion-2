package com.foodstore.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró la clase del Driver de MySQL en el Classpath", e);
        }

        String url = "jdbc:mysql://localhost:3306/food_store_db";
        String user = "root";
        String pass = "";

        return DriverManager.getConnection(url, user, pass);
    }
}
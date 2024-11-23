package com.empresa.projetoapi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/empresa_java";
    private static final String USER = "docker";
    private static final String PASSWORD = "docker";

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão bem-sucedida!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            throw e;
        }
    }
}


package com.empresa.projetoapi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;

public class Database {
    private static final Dotenv dotenv = Dotenv.configure().directory("./").load();

    private static final String URL = dotenv.get("DATABASE_URL") + "?ssl=true";
    private static final String USER = dotenv.get("DATABASE_USER");
    private static final String PASSWORD = dotenv.get("DATABASE_PASSWORD");

    public static Connection getConnection() throws SQLException {
        try {
            System.out.println("Tentando conectar ao banco...");
            System.out.println("URL: " + URL);
            System.out.println("Usuário: " + USER);

            Properties props = new Properties();
            props.setProperty("user", USER);
            props.setProperty("password", PASSWORD);
            props.setProperty("ssl", "true");

            Connection connection = DriverManager.getConnection(URL, props);
            System.out.println("Conexão bem-sucedida!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            throw e;
        }
    }
}


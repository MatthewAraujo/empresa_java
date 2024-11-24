package com.empresa.projetoapi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class Database {
    private static final Dotenv dotenv = Dotenv.configure().directory("./").load();

    private static final String URL = dotenv.get("DATABASE_URL") ;
    private static final String USER = dotenv.get("DATABASE_USER");
    private static final String PASSWORD = dotenv.get("DATABASE_PASSWORD");



    public static Connection getConnection() throws SQLException {
        System.out.println("vamo começar?");
        System.out.println("URL: "+URL);
        System.out.println("USER: "+ USER);
        System.out.println("vamo começar?");
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

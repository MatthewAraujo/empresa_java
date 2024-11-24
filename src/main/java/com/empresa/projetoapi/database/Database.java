package com.empresa.projetoapi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class Database {
    private static final Dotenv dotenv = Dotenv.configure().directory("./").load();

    private static final String URL = "jdbc:postgresql://junction.proxy.rlwy.net:46588/railway";
    private static final String USER = "postgress"; 
    private static final String PASSWORD = "WcqUfNlqKdgPgIjzkmNjVMcAHiuYRnaY";



    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão bem-sucedida!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

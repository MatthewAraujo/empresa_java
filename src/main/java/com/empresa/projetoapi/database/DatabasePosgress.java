package com.empresa.projetoapi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabasePosgress {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://junction.proxy.rlwy.net:46588/railway"; 
        String user = "postgres";
        String password = "WcqUfNlqKdgPgIjzkmNjVMcAHiuYRnaY";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão bem-sucedida!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            throw e;
        }
    }
}




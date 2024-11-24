package com.empresa.projetoapi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.empresa.projetoapi.database.DatabasePosgress;
import com.empresa.projetoapi.model.Funcionario;

@Repository
public class FuncionarioRepository {

    public List<Funcionario> findAll() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String query = "SELECT * FROM funcionarios";

        try (Connection connection = DatabasePosgress.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("sobrenome"),
                    resultSet.getInt("idade"),
                    resultSet.getString("email"),
                    resultSet.getString("cargo"),
                    resultSet.getInt("empresa_id")
                );
                funcionarios.add(funcionario);
            }
        }
        return funcionarios;
    }

    public Optional<Funcionario> findById(int id) throws SQLException {
        String query = "SELECT * FROM funcionarios WHERE id = ?";
        try (Connection connection = DatabasePosgress.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Funcionario funcionario = new Funcionario(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("sobrenome"),
                        resultSet.getInt("idade"),
                        resultSet.getString("email"),
                        resultSet.getString("cargo"),
                        resultSet.getInt("empresa_id")
                    );
                    return Optional.of(funcionario);
                }
            }
        }
        return Optional.empty();
    }

    public void save(Funcionario funcionario) throws SQLException {
        String query = "INSERT INTO funcionarios (nome, sobrenome, idade, email, cargo, empresa_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabasePosgress.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, funcionario.getNome());
            statement.setString(2, funcionario.getSobrenome());
            statement.setInt(3, funcionario.getIdade());
            statement.setString(4, funcionario.getEmail());
            statement.setString(5, funcionario.getCargo());
            statement.setInt(6, funcionario.getEmpresaId());
            statement.executeUpdate();
        }
    }

    public void update(Funcionario funcionario) throws SQLException {
        String query = "UPDATE funcionarios SET nome = ?, sobrenome = ?, idade = ?, email = ?, cargo = ?, empresa_id = ? WHERE id = ?";
        try (Connection connection = DatabasePosgress.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, funcionario.getNome());
            statement.setString(2, funcionario.getSobrenome());
            statement.setInt(3, funcionario.getIdade());
            statement.setString(4, funcionario.getEmail());
            statement.setString(5, funcionario.getCargo());
            statement.setInt(6, funcionario.getEmpresaId());
            statement.setInt(7, funcionario.getId());
            statement.executeUpdate();
        }
    }

    public void deleteById(int id) throws SQLException {
        String query = "DELETE FROM funcionarios WHERE id = ?";
        try (Connection connection = DatabasePosgress.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}


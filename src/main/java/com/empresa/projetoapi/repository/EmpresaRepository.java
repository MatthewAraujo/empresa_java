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

import com.empresa.projetoapi.database.Database;
import com.empresa.projetoapi.model.Empresa;
import com.empresa.projetoapi.model.Funcionario;

@Repository
public class EmpresaRepository {

    public List<Empresa> findAll() throws SQLException {
        System.out.println("PAssei aqui tambem");

        List<Empresa> empresas = new ArrayList<>();
        String query = "SELECT * FROM empresas";
        
        try (Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int empresaId = resultSet.getInt("id");
                Empresa empresa = new Empresa(
                    empresaId,
                    resultSet.getString("nome"),
                    resultSet.getString("telefone"),
                    resultSet.getString("endereco"),
                    new ArrayList<>()
                );

                // Carregar funcionários associados a esta empresa
                List<Funcionario> funcionarios = findFuncionariosByEmpresaId(empresaId);
                empresa.setFuncionarios(funcionarios);

                empresas.add(empresa);
            }
        }
        return empresas;
    }

    private List<Funcionario> findFuncionariosByEmpresaId(int empresaId) throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String query = "SELECT * FROM funcionarios WHERE empresa_id = ?";
        
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, empresaId);
            try (ResultSet resultSet = statement.executeQuery()) {
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
        }
        return funcionarios;
    }

    public Optional<Empresa> findById(int id) throws SQLException {
        String query = "SELECT * FROM empresas WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Empresa empresa = new Empresa(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("telefone"),
                        resultSet.getString("endereco"),
                        new ArrayList<>()
                    );

                    // Carregar funcionários associados a esta empresa
                    List<Funcionario> funcionarios = findFuncionariosByEmpresaId(id);
                    empresa.setFuncionarios(funcionarios);

                    return Optional.of(empresa);
                }
            }
        }
        return Optional.empty();
    }

    public void save(Empresa empresa) throws SQLException {
        String query = "INSERT INTO empresas (nome, telefone, endereco) VALUES (?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, empresa.getNome());
            statement.setString(2, empresa.getTelefone());
            statement.setString(3, empresa.getEndereco());
            statement.executeUpdate();
        }
    }

    public void update(Empresa empresa) throws SQLException {
        String query = "UPDATE empresas SET nome = ?, telefone = ?, endereco = ? WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, empresa.getNome());
            statement.setString(2, empresa.getTelefone());
            statement.setString(3, empresa.getEndereco());
            statement.setInt(4, empresa.getId());
            statement.executeUpdate();
        }
    }

    public void deleteById(int id) throws SQLException {
        String query = "DELETE FROM empresas WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}


package com.empresa.projetoapi.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.empresa.projetoapi.model.Empresa;
import com.empresa.projetoapi.model.Funcionario;
import com.empresa.projetoapi.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final EmpresaService empresaService;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, EmpresaService empresaService) {
        this.funcionarioRepository = funcionarioRepository;
        this.empresaService = empresaService;
    }

    public Optional<Funcionario> getFuncionario(Integer id) {
        try {
            return funcionarioRepository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário com ID " + id + ": " + e.getMessage(), e);
        }
    }

    public List<Funcionario> getFuncionarios() {
        try {
            return funcionarioRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os funcionários: " + e.getMessage(), e);
        }
    }

    public Funcionario createFuncionario(Funcionario funcionario) {
        try {
            if (empresaService.getEmpresaById(funcionario.getEmpresaId()).isEmpty()) {
                throw new IllegalArgumentException("Empresa com ID " + funcionario.getEmpresaId() + " não existe.");
            }

            funcionarioRepository.save(funcionario);

            Empresa empresa = empresaService.getEmpresaById(funcionario.getEmpresaId())
                    .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada para adicionar o funcionário."));
            empresa.addFuncionario(funcionario);

            return funcionario;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar funcionário: " + e.getMessage(), e);
        }
    }

    public Optional<Funcionario> getFuncionarioById(Integer id) {
        return getFuncionario(id);
    }

    public Optional<Funcionario> updateFuncionarioById(Funcionario funcionario) {
        try {
            if (empresaService.getEmpresaById(funcionario.getEmpresaId()).isEmpty()) {
                throw new IllegalArgumentException("Empresa com ID " + funcionario.getEmpresaId() + " não encontrada");
            }

            funcionarioRepository.update(funcionario);
            return Optional.of(funcionario);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário com ID " + funcionario.getId() + ": " + e.getMessage(), e);
        }
    }

    public Optional<String> deleteFuncionarioById(Integer id) {
        try {
            funcionarioRepository.deleteById(id);
            return Optional.of("Funcionario com ID " + id + " deletado com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar funcionário com ID " + id + ": " + e.getMessage(), e);
        }
    }



    
}


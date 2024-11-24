package com.empresa.projetoapi.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.projetoapi.model.Empresa;
import com.empresa.projetoapi.repository.EmpresaRepository;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> getEmpresas() {
        try {
            return empresaRepository.findAll();
        } catch (SQLException e) {
            // Captura SQLException e imprime o stack trace completo
            System.err.println("Erro inesperado ao buscar empresas: " + e.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.err.println("Stack trace completo: " + sw.toString());
            throw new RuntimeException("Erro ao buscar empresas", e); // Lança uma exceção de runtime com mais contexto
        } catch (Exception e) {
            // Captura qualquer outra exceção
            System.err.println("Erro inesperado: " + e.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.err.println("Stack trace completo: " + sw.toString());
            throw new RuntimeException("Erro desconhecido", e);
        }
    }



    public Optional<Empresa> getEmpresaById(Integer id) throws SQLException {
        return empresaRepository.findById(id);
    }

    public void createEmpresa(Empresa empresa) throws SQLException {
        empresaRepository.save(empresa);
    }

    public Optional<Empresa> updateEmpresaById(Empresa empresa) throws SQLException {
        Optional<Empresa> existing = empresaRepository.findById(empresa.getId());
        if (existing.isPresent()) {
            empresaRepository.update(empresa);
        }
        return existing;
    }

    public Optional<String> deleteEmpresaById(Integer id) throws SQLException {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (empresa.isPresent()) {
            empresaRepository.deleteById(id);
            return Optional.of("Deleted");
        }
        return Optional.empty();
    }
}

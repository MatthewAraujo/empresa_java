package com.empresa.projetoapi.service;

import com.empresa.projetoapi.repository.EmpresaRepository;
import com.empresa.projetoapi.model.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> getEmpresas() throws SQLException {
        return empresaRepository.findAll();
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


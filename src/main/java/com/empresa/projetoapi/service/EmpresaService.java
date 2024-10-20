package com.empresa.projetoapi.service;

import com.empresa.projetoapi.model.Empresa;
import com.empresa.projetoapi.model.Funcionario; 
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final List<Empresa> empresaList;

    public EmpresaService() {
        empresaList = new ArrayList<>();

        Empresa empresa1 = new Empresa(1, "Empresa A", "123456789", "Rua 1, Bairro 1", new ArrayList<>());
        Empresa empresa2 = new Empresa(2, "Empresa B", "987654321", "Rua 2, Bairro 2", new ArrayList<>());
        Empresa empresa3 = new Empresa(3, "Empresa C", "555555555", "Rua 3, Bairro 3", new ArrayList<>());
        Empresa empresa4 = new Empresa(4, "Empresa D", "444444444", "Rua 4, Bairro 4", new ArrayList<>());
        Empresa empresa5 = new Empresa(5, "Empresa E", "333333333", "Rua 5, Bairro 5", new ArrayList<>());

        empresaList.addAll(List.of(empresa1, empresa2, empresa3, empresa4, empresa5));
    }

    public Optional<Empresa> getEmpresa(Integer id) {
        return empresaList.stream()
                .filter(empresa -> empresa.getId() == id)
                .findFirst();
    }

    public List<Empresa> getEmpresas() {
        return empresaList;
    }

    public Empresa createEmpresa(Empresa empresa) {
        if (empresaList.stream().anyMatch(existingEmpresa -> existingEmpresa.getId() == empresa.getId())) {
            throw new IllegalArgumentException("Empresa com ID " + empresa.getId() + " já existe.");
        }
        empresaList.add(empresa);
        return empresa;
    }

    public Optional<Empresa> getEmpresaById(Integer id) {
        return getEmpresa(id);
    }

    public Optional<Empresa> updateEmpresaById(Empresa empresa) {
        return empresaList.stream()
                .filter(existingEmpresa -> existingEmpresa.getId() == empresa.getId())
                .findFirst()
                .map(existingEmpresa -> {
                    int index = empresaList.indexOf(existingEmpresa);
                    empresaList.set(index, empresa);
                    return empresa;
                });
    }

    public Optional<String> deleteEmpresaById(Integer id) {
        return empresaList.stream()
                .filter(empresa -> empresa.getId() == id)
                .findFirst()
                .map(empresa -> {
                    empresaList.remove(empresa);
                    return "Empresa deletada: " + empresa.getNome();
                });
    }

    public Optional<Funcionario> addFuncionarioToEmpresa(Integer empresaId, Funcionario funcionario) {
        return getEmpresaById(empresaId).map(empresa -> {
            empresa.getFuncionarios().add(funcionario);
            return funcionario;
        });
    }

    public Optional<String> removeFuncionarioFromEmpresa(Integer empresaId, Integer funcionarioId) {
        Optional<Empresa> empresa = getEmpresaById(empresaId);
        if (empresa.isPresent()) {
            List<Funcionario> funcionarios = empresa.get().getFuncionarios();
            Optional<Funcionario> funcionarioToRemove = funcionarios.stream()
                    .filter(funcionario -> funcionario.getId() == funcionarioId)
                    .findFirst();

            if (funcionarioToRemove.isPresent()) {
                funcionarios.remove(funcionarioToRemove.get());
                return Optional.of("Funcionário removido: " + funcionarioToRemove.get().getNome());
            } else {
                return Optional.empty(); 
            }
        }
        return Optional.empty(); 
    }
}


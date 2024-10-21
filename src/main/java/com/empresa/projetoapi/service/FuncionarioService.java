package com.empresa.projetoapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.empresa.projetoapi.model.Empresa;
import com.empresa.projetoapi.model.Funcionario; 

@Service
public class FuncionarioService {

    private final List<Funcionario> funcionarioList;
    private final EmpresaService empresaService; 

    public FuncionarioService(EmpresaService empresaService) {
    this.empresaService = empresaService;
    funcionarioList = new ArrayList<>();

    // Criando funcionários e associando às empresas
    Funcionario funcionario1 = new Funcionario(1, "Carlos", "Silva", 35, "carlos@gmail.com", "TI", 1);
    Funcionario funcionario2 = new Funcionario(2, "Ana", "Souza", 29, "ana@gmail.com", "RH", 2);
    Funcionario funcionario3 = new Funcionario(3, "Pedro", "Lima", 40, "pedro@gmail.com", "Financeiro", 3);
    Funcionario funcionario4 = new Funcionario(4, "Clara", "Costa", 31, "clara@gmail.com", "Marketing", 1);
    Funcionario funcionario5 = new Funcionario(5, "João", "Pereira", 25, "joao@gmail.com", "Desenvolvimento", 2);

    funcionarioList.addAll(List.of(funcionario1, funcionario2, funcionario3, funcionario4, funcionario5));

    // Associando funcionários às suas empresas
    empresaService.getEmpresa(1).ifPresent(empresa -> empresa.addFuncionario(funcionario1));
    empresaService.getEmpresa(1).ifPresent(empresa -> empresa.addFuncionario(funcionario4));
    empresaService.getEmpresa(2).ifPresent(empresa -> empresa.addFuncionario(funcionario2));
    empresaService.getEmpresa(2).ifPresent(empresa -> empresa.addFuncionario(funcionario5));
    empresaService.getEmpresa(3).ifPresent(empresa -> empresa.addFuncionario(funcionario3));
}


    public Optional<Funcionario> getFuncionario(Integer id) {
        return funcionarioList.stream()
                .filter(funcionario -> funcionario.getId() == id)
                .findFirst();
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarioList;
    }

    public Funcionario createFuncionario(Funcionario funcionario) {
        if (empresaService.getEmpresa(funcionario.getEmpresaId()).isEmpty()) {
            throw new IllegalArgumentException("Empresa com ID " + funcionario.getEmpresaId() + " não existe.");
        }

        funcionarioList.add(funcionario);
        
        Empresa empresa = empresaService.getEmpresa(funcionario.getEmpresaId()).orElseThrow(() -> 
            new IllegalArgumentException("Empresa não encontrada para adicionar o funcionário."));
        empresa.addFuncionario(funcionario);

        return funcionario;
    }


    public Optional<Funcionario> getFuncionarioById(Integer id) {
        return getFuncionario(id);
    }

    public Optional<Funcionario> updateFuncionarioById(Funcionario funcionario) {
        Optional<Empresa> empresa = empresaService.getEmpresa(funcionario.getEmpresaId());
        if (empresa.isEmpty()) {
            throw new IllegalArgumentException("Empresa com ID " + funcionario.getEmpresaId() + " não encontrada");
        }

        return funcionarioList.stream()
                .filter(existingFuncionario -> existingFuncionario.getId() == funcionario.getId())
                .findFirst()
                .map(existingFuncionario -> {
                    int index = funcionarioList.indexOf(existingFuncionario);
                    funcionarioList.set(index, funcionario);
                    return funcionario;
                });
    }

    public Optional<String> deleteFuncionarioById(Integer id) {
        return funcionarioList.stream()
                .filter(funcionario -> funcionario.getId() == id)
                .findFirst()
                .map(funcionario -> {
                    // Remover o funcionário da lista de funcionários de sua empresa associada
                    empresaService.getEmpresa(funcionario.getEmpresaId()).ifPresent(empresa -> {
                        empresa.getFuncionarios().removeIf(f -> f.getId() == id);
                    });

                    // Remover o funcionário da lista geral de funcionários
                    funcionarioList.remove(funcionario);

                    return "Funcionário deletado e removido da empresa: " + funcionario.getNome();
                });
    }



    public void removerFuncionariosPorEmpresa(Integer empresaId) {
        funcionarioList.removeIf(funcionario -> funcionario.getEmpresaId() == empresaId);
    }
}


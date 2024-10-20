package com.funcionario.projetoapi.service;

import com.funcionario.projetoapi.model.Funcionario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final List<Funcionario> funcionarioList;

    public FuncionarioService() {
        funcionarioList = new ArrayList<>();

        Funcionario funcionario1 = new Funcionario(1, "Carlos", 35, "carlos@gmail.com", "TI");
        Funcionario funcionario2 = new Funcionario(2, "Ana", 29, "ana@gmail.com", "RH");
        Funcionario funcionario3 = new Funcionario(3, "Pedro", 40, "pedro@gmail.com", "Financeiro");
        Funcionario funcionario4 = new Funcionario(4, "Clara", 31, "clara@gmail.com", "Marketing");
        Funcionario funcionario5 = new Funcionario(5, "João", 25, "joao@gmail.com", "Desenvolvimento");

        funcionarioList.addAll(Arrays.asList(funcionario1, funcionario2, funcionario3, funcionario4, funcionario5));
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
        if (funcionarioList.stream().anyMatch(existingFuncionario -> existingFuncionario.getId() == funcionario.getId())) {
            throw new IllegalArgumentException("Funcionario with ID " + funcionario.getId() + " already exists");
        }
        funcionarioList.add(funcionario);
        return funcionario;
    }

    public Optional<Funcionario> getFuncionarioById(Integer id) {
        return getFuncionario(id);
    }

    public Optional<Funcionario> updateFuncionarioById(Funcionario funcionario) {
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
                    funcionarioList.remove(funcionario);
                    return "Funcionario deleted: " + funcionario.getName();
                });
    }
}


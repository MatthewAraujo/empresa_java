package com.empresa.projetoapi.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.projetoapi.model.Funcionario;
import com.empresa.projetoapi.service.FuncionarioService;

@RestController
@RequestMapping("/api")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<Funcionario>> getFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.getFuncionarios();
        return ResponseEntity.status(HttpStatus.OK).body(funcionarios);
    }

    @PostMapping("/funcionario")
    public ResponseEntity<String> createFuncionario(@RequestBody Funcionario funcionario) {
        try {
            funcionarioService.createFuncionario(funcionario); // Não precisa passar empresaId aqui, pois já está no funcionario
            return ResponseEntity.status(HttpStatus.CREATED).body("Funcionario created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Funcionario: " + e.getMessage());
        }
    }

    @GetMapping("/funcionario/{id}")
    public ResponseEntity<?> getFuncionarioById(@PathVariable("id") Integer id) {
        Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(id);
        if (funcionario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }

    @PutMapping("/funcionario/{id}")
    public ResponseEntity<?> updateFuncionarioById(@PathVariable("id") Integer id, @RequestBody Funcionario funcionario) {
        funcionario.setId(id);
        try {
            Optional<Funcionario> updatedFuncionario = funcionarioService.updateFuncionarioById(funcionario); // Não precisa passar empresaId aqui, pois já está no funcionario
            if (updatedFuncionario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario not found for update");
            }
            return ResponseEntity.status(HttpStatus.OK).body(updatedFuncionario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Funcionario: " + e.getMessage());
        }
    }

    @DeleteMapping("/funcionario/{id}")
    public ResponseEntity<?> deleteFuncionarioById(@PathVariable("id") Integer id) {
        Optional<String> deletedFuncionario = funcionarioService.deleteFuncionarioById(id);
        return deletedFuncionario.map(s -> ResponseEntity.status(HttpStatus.OK).body("Funcionario deleted: " + s))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario not found for deletion"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
}


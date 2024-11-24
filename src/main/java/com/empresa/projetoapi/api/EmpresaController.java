package com.empresa.projetoapi.api;

import java.sql.SQLException;
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

import com.empresa.projetoapi.model.Empresa;
import com.empresa.projetoapi.service.EmpresaService;
import com.empresa.projetoapi.service.FuncionarioService;

@RestController
@RequestMapping("/api")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService, FuncionarioService funcionarioService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/empresas")
public ResponseEntity<List<Empresa>> getEmpresas() {
    try {
        System.out.println("Passei aqui");
        List<Empresa> empresas = empresaService.getEmpresas();
        return ResponseEntity.status(HttpStatus.OK).body(empresas);
    } catch (NullPointerException e) {
        // Captura especificamente NullPointerException
        System.err.println("Erro de Null Pointer: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (Exception e) {
        // Captura qualquer outra exceção
        System.err.println("Erro inesperado: " + e.getMessage());
        e.printStackTrace(); // Imprime todo o stack trace no console
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}


    @GetMapping("/empresa/{id}")
    public ResponseEntity<?> getEmpresaById(@PathVariable("id") Integer id) {
        try {
            Optional<Empresa> empresa = empresaService.getEmpresaById(id);
            if (empresa.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa not found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(empresa);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching Empresa");
        }
    }

    @PostMapping("/empresa")
    public ResponseEntity<String> createEmpresa(@RequestBody Empresa empresa) {
        try {
            empresaService.createEmpresa(empresa);
            return ResponseEntity.status(HttpStatus.CREATED).body("Empresa created successfully");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Empresa");
        }
    }

    @PutMapping("/empresa/{id}")
    public ResponseEntity<?> updateEmpresaById(@PathVariable("id") Integer id, @RequestBody Empresa empresa) {
        try {
            empresa.setId(id); // Garantir que o ID no corpo e na URL sejam sincronizados
            Optional<Empresa> updatedEmpresa = empresaService.updateEmpresaById(empresa);
            if (updatedEmpresa.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa not found for update");
            }
            return ResponseEntity.status(HttpStatus.OK).body(updatedEmpresa);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Empresa");
        }
    }

    @DeleteMapping("/empresa/{id}")
    public ResponseEntity<String> deleteEmpresaById(@PathVariable("id") Integer id) {
        try {
            Optional<String> result = empresaService.deleteEmpresaById(id);
            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa not found for deletion");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Empresa deleted successfully");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Empresa");
        }
    }

    // Tratamento global para exceções genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
    }
}


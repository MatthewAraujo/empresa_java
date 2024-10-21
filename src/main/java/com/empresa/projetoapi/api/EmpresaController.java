package com.empresa.projetoapi.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empresa.projetoapi.model.Empresa;
import com.empresa.projetoapi.service.EmpresaService;
import com.empresa.projetoapi.service.FuncionarioService;

@RestController
@RequestMapping("/api")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final FuncionarioService funcionarioService;

    @Autowired
    public EmpresaController(EmpresaService empresaService, FuncionarioService funcionarioService) {
        this.empresaService = empresaService;
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/empresas")
    public ResponseEntity<List<Empresa>> getEmpresas() {
        List<Empresa> empresas = empresaService.getEmpresas();
        return ResponseEntity.status(HttpStatus.OK).body(empresas);
    }

    @PostMapping("/empresa")
    public ResponseEntity<String> createEmpresa(@RequestBody Empresa empresa) {
        try {
            empresaService.createEmpresa(empresa);
            return ResponseEntity.status(HttpStatus.CREATED).body("Empresa created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Empresa");
        }
    }

    @GetMapping("/empresa/{id}")
    public ResponseEntity<?> getEmpresaById(@PathVariable("id") Integer id) {
        Optional<Empresa> empresa = empresaService.getEmpresaById(id);
        if (empresa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(empresa);
    }

    @PutMapping("/empresa/{id}")
    public ResponseEntity<?> updateEmpresaById(@PathVariable("id") Integer id, @RequestBody Empresa empresa) {
        empresa.setId(id);
        Optional<Empresa> updatedEmpresa = empresaService.updateEmpresaById(empresa);
        if (updatedEmpresa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa not found for update");
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmpresa);
    }

    @DeleteMapping("/empresa/{id}")
    public ResponseEntity<?> deleteEmpresaById(@PathVariable("id") Integer id) {
        Optional<String> deletedEmpresa = empresaService.deleteEmpresaById(id, funcionarioService);
        return deletedEmpresa.map(s -> ResponseEntity.status(HttpStatus.OK).body("Empresa deleted: " + s)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa not found for deletion"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }

}


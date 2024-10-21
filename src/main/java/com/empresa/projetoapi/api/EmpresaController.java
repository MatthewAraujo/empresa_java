package com.empresa.projetoapi.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
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


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }

}


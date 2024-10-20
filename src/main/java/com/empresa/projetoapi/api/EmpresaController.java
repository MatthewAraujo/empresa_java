package com.empresa.projetoapi.api;

import com.empresa.projetoapi.model.Empresa;
import com.empresa.projetoapi.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/empresa")
    public ResponseEntity<?> getEmpresa(@RequestParam Integer id) {
        Optional<Empresa> empresa = empresaService.getEmpresa(id);
        if (empresa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(empresa);
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

    @PostMapping("/empresa/{id}/funcionario")
    public ResponseEntity<String> addFuncionarioToEmpresa(@PathVariable("id") Integer id, @RequestBody Funcionario funcionario) {
        Optional<Empresa> empresa = empresaService.getEmpresaById(id);
        if (empresa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa not found");
        }

        try {
            empresa.get().getFuncionarios().add(funcionario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Funcionário adicionado com sucesso à empresa " + empresa.get().getNome());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding Funcionario to Empresa");
        }
    }
    
    @DeleteMapping("/empresa/{empresaId}/funcionario/{funcionarioId}")
    public ResponseEntity<String> deleteFuncionarioFromEmpresa(@PathVariable("empresaId") Integer empresaId, @PathVariable("funcionarioId") Integer funcionarioId) {
        Optional<Empresa> empresa = empresaService.getEmpresaById(empresaId);
        if (empresa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa not found");
        }

        List<Funcionario> funcionarios = empresa.get().getFuncionarios();
        Optional<Funcionario> funcionarioToRemove = funcionarios.stream()
                .filter(funcionario -> funcionario.getId() == funcionarioId)
                .findFirst();

        if (funcionarioToRemove.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário not found in the company");
        }

        funcionarios.remove(funcionarioToRemove.get());
        return ResponseEntity.status(HttpStatus.OK).body("Funcionário deleted successfully from empresa " + empresa.get().getNome());
    }

    
    @DeleteMapping("/empresa/{id}")
    public ResponseEntity<?> deleteEmpresaById(@PathVariable("id") Integer id) {
        Optional<String> deletedEmpresa = empresaService.deleteEmpresaById(id);
        return deletedEmpresa.map(s -> ResponseEntity.status(HttpStatus.OK).body("Empresa deleted: " + s))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa not found for deletion"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }

}


package com.vanrides.vanrides.controller;

import com.vanrides.vanrides.model.Motorista;
import com.vanrides.vanrides.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @PostMapping
    public ResponseEntity<?> criarMotorista(@RequestBody Motorista motorista) {
        // Verifica se a senha foi enviada
        if (motorista.getSenha() == null || motorista.getSenha().isBlank()) {
            return ResponseEntity.badRequest().body("Senha é obrigatória.");
        }

        // Criptografa a senha diretamente na service
        Motorista salvo = motoristaService.salvarMotorista(motorista);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Motorista>> listarMotoristas() {
        List<Motorista> motoristas = motoristaService.listarTodosMotoristas();
        return ResponseEntity.ok(motoristas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorista> buscarPorId(@PathVariable Long id) {
        Optional<Motorista> motorista = motoristaService.buscarMotoristaPorId(id);
        return motorista.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        Optional<Motorista> motorista = motoristaService.buscarMotoristaPorId(id);

        if (motorista.isPresent()) {
            motoristaService.deletarMotorista(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.vanrides.vanrides.controller;

import com.vanrides.vanrides.model.Passageiro;
import com.vanrides.vanrides.service.PassageiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passageiros")
public class PassageiroController {

    @Autowired
    private PassageiroService passageiroService;

    @PostMapping
    public ResponseEntity<?> criarPassageiro(@RequestBody Passageiro passageiro) {
        // Verifica se a senha foi enviada
        if (passageiro.getSenha() == null || passageiro.getSenha().isBlank()) {
            return ResponseEntity.badRequest().body("Senha é obrigatória.");
        }

        // Criptografa a senha diretamente na service
        Passageiro salvo = passageiroService.salvarPassageiro(passageiro);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Passageiro>> listarPassageiros() {
        List<Passageiro> passageiros = passageiroService.listarTodosPassageiros();
        return ResponseEntity.ok(passageiros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passageiro> buscarPorId(@PathVariable Long id) {
        Optional<Passageiro> passageiro = passageiroService.buscarPassageiroPorId(id);
        return passageiro.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        Optional<Passageiro> passageiro = passageiroService.buscarPassageiroPorId(id);

        if (passageiro.isPresent()) {
            passageiroService.deletarPassageiro(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

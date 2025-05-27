package com.vanrides.vanrides.controller;

import com.vanrides.vanrides.model.Passageiro;
import com.vanrides.vanrides.service.PassageiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passageiros")
public class PassageiroController {

    @Autowired
    private PassageiroService passageiroService;

    // ✅ Cadastro de passageiro
    @PostMapping
    public ResponseEntity<?> criarPassageiro(@RequestBody Passageiro passageiro) {
        // Verifica campos obrigatórios
        if (passageiro.getSenha() == null || passageiro.getSenha().isBlank()) {
            return ResponseEntity.badRequest().body("Senha é obrigatória.");
        }

        if (passageiro.getEndereco() == null || passageiro.getEndereco().isBlank()
            || passageiro.getBairro() == null || passageiro.getBairro().isBlank()
            || passageiro.getCidade() == null || passageiro.getCidade().isBlank()
            || passageiro.getEstado() == null || passageiro.getEstado().isBlank()
            || passageiro.getCep() == null || passageiro.getCep().isBlank()) {
            return ResponseEntity.badRequest().body("Endereço completo com bairro é obrigatório.");
        }

        // Salva e retorna
        Passageiro salvo = passageiroService.salvarPassageiro(passageiro);
        return ResponseEntity.ok(salvo);
    }

    // ✅ Listagem de passageiros
    @GetMapping
    public ResponseEntity<List<Passageiro>> listarPassageiros() {
        List<Passageiro> passageiros = passageiroService.listarTodosPassageiros();
        return ResponseEntity.ok(passageiros);
    }

    // ✅ Buscar passageiro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Passageiro> buscarPorId(@PathVariable Long id) {
        Optional<Passageiro> passageiro = passageiroService.buscarPassageiroPorId(id);
        return passageiro.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Atualizar passageiro
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPassageiro(@PathVariable Long id, @RequestBody Passageiro passageiroAtualizado) {
        Optional<Passageiro> existente = passageiroService.buscarPassageiroPorId(id);

        if (existente.isPresent()) {
            Passageiro passageiro = existente.get();

            passageiro.setNome(passageiroAtualizado.getNome());
            passageiro.setEmail(passageiroAtualizado.getEmail());
            passageiro.setTelefone(passageiroAtualizado.getTelefone());
            passageiro.setEndereco(passageiroAtualizado.getEndereco());
            passageiro.setBairro(passageiroAtualizado.getBairro()); // ✅ Bairro incluído
            passageiro.setCidade(passageiroAtualizado.getCidade());
            passageiro.setEstado(passageiroAtualizado.getEstado());
            passageiro.setCep(passageiroAtualizado.getCep());

            // Se quiser atualizar latitude e longitude manualmente
            passageiro.setLatitude(passageiroAtualizado.getLatitude());
            passageiro.setLongitude(passageiroAtualizado.getLongitude());

            // Se o campo senha for enviado, atualiza
            if (passageiroAtualizado.getSenha() != null && !passageiroAtualizado.getSenha().isBlank()) {
                passageiro.setSenha(passageiroAtualizado.getSenha());
            }

            Passageiro salvo = passageiroService.salvarPassageiro(passageiro);
            return ResponseEntity.ok(salvo);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Deletar passageiro
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

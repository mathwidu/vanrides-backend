package com.vanrides.vanrides.controller;

import com.vanrides.vanrides.dto.LoginRequest;
import com.vanrides.vanrides.dto.LoginResponse;
import com.vanrides.vanrides.model.Motorista;
import com.vanrides.vanrides.model.Passageiro;
import com.vanrides.vanrides.service.MotoristaService;
import com.vanrides.vanrides.service.PassageiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private PassageiroService passageiroService;

    @Autowired
    private MotoristaService motoristaService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String senha = loginRequest.getSenha();

        System.out.println("Tentando login com:");
        System.out.println("Email recebido: " + email);
        System.out.println("Senha recebida: " + senha);

        // 1. Verificar passageiro
        Passageiro passageiro = passageiroService.buscarPorEmail(email);
        if (passageiro != null) {
            System.out.println("Passageiro encontrado: " + passageiro.getEmail());
            boolean senhaConfere = BCrypt.verifyer().verify(senha.toCharArray(), passageiro.getSenha().toCharArray()).verified;
            System.out.println("Senha válida para passageiro? " + senhaConfere);

            if (senhaConfere) {
                LoginResponse response = new LoginResponse(
                    "Passageiro",
                    "Bem-vindo, Passageiro " + passageiro.getNome() + "!",
                    passageiro.getId()
                );
                return ResponseEntity.ok(response);
            }
        }

        // 2. Verificar motorista
        Motorista motorista = motoristaService.buscarPorEmail(email);
        if (motorista != null) {
            System.out.println("Motorista encontrado: " + motorista.getEmail());
            boolean senhaConfere = BCrypt.verifyer().verify(senha.toCharArray(), motorista.getSenha().toCharArray()).verified;
            System.out.println("Senha válida para motorista? " + senhaConfere);

            if (senhaConfere) {
                LoginResponse response = new LoginResponse(
                    "Motorista",
                    "Bem-vindo, Motorista " + motorista.getNome() + "!",
                    motorista.getId()
                );
                return ResponseEntity.ok(response);
            }
        }

        System.out.println("Credenciais inválidas para: " + email);
        return ResponseEntity.status(401).body("Credenciais inválidas.");
    }
}

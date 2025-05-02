package com.vanrides.vanrides.service;

import com.vanrides.vanrides.model.Passageiro;
import com.vanrides.vanrides.repository.PassageiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

@Service
public class PassageiroService {

    @Autowired
    private PassageiroRepository passageiroRepository;

    // Salva um novo passageiro com a senha criptografada
    public Passageiro salvarPassageiro(Passageiro passageiro) {
        String senhaCriptografada = BCrypt.withDefaults().hashToString(12, passageiro.getSenha().toCharArray());
        passageiro.setSenha(senhaCriptografada);
        return passageiroRepository.save(passageiro);
    }

    // Retorna todos os passageiros
    public List<Passageiro> listarTodosPassageiros() {
        return passageiroRepository.findAll();
    }

    // Busca um passageiro por ID
    public Optional<Passageiro> buscarPassageiroPorId(Long id) {
        return passageiroRepository.findById(id);
    }

    // Deleta um passageiro pelo ID
    public void deletarPassageiro(Long id) {
        passageiroRepository.deleteById(id);
    }

    // Busca um passageiro pelo e-mail
    public Passageiro buscarPorEmail(String email) {
        return passageiroRepository.findByEmail(email);
    }
}

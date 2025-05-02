package com.vanrides.vanrides.service;

import com.vanrides.vanrides.model.Motorista;
import com.vanrides.vanrides.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    // Salva um novo motorista com senha criptografada
    public Motorista salvarMotorista(Motorista motorista) {
        String senhaCriptografada = BCrypt.withDefaults().hashToString(12, motorista.getSenha().toCharArray());
        motorista.setSenha(senhaCriptografada);
        return motoristaRepository.save(motorista);
    }

    // Lista todos os motoristas
    public List<Motorista> listarTodosMotoristas() {
        return motoristaRepository.findAll();
    }

    // Busca um motorista por ID
    public Optional<Motorista> buscarMotoristaPorId(Long id) {
        return motoristaRepository.findById(id);
    }

    // Deleta um motorista pelo ID
    public void deletarMotorista(Long id) {
        motoristaRepository.deleteById(id);
    }

    // Busca um motorista pelo e-mail
    public Motorista buscarPorEmail(String email) {
        return motoristaRepository.findByEmail(email);
    }
}

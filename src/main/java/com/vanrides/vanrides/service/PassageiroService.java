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

    @Autowired
    private GeocodingService geocodingService;

    // ✅ Salvar novo passageiro (com senha criptografada e geolocalização)
    public Passageiro salvarPassageiro(Passageiro passageiro) {
        // 🔒 Criptografa a senha apenas se ela não estiver criptografada
        if (!passageiro.getSenha().startsWith("$2a$")) {
            String senhaCriptografada = BCrypt.withDefaults().hashToString(12, passageiro.getSenha().toCharArray());
            passageiro.setSenha(senhaCriptografada);
        }

        // 🌎 Monta o endereço completo, agora incluindo o bairro
        String enderecoCompleto = passageiro.getEndereco() + ", "
                                + passageiro.getBairro() + ", "
                                + passageiro.getCidade() + ", "
                                + passageiro.getEstado() + ", "
                                + passageiro.getCep();

        try {
            // 🚀 Faz a busca das coordenadas
            GeocodingService.Coordenadas coordenadas = geocodingService.buscarCoordenadas(enderecoCompleto);

            if (coordenadas != null) {
                passageiro.setLatitude(coordenadas.getLatitude());
                passageiro.setLongitude(coordenadas.getLongitude());
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar coordenadas: " + e.getMessage());
            // ⚠️ Aqui você pode decidir se salva sem coordenadas ou lança um erro
        }

        return passageiroRepository.save(passageiro);
    }

    // ✅ Listar todos os passageiros
    public List<Passageiro> listarTodosPassageiros() {
        return passageiroRepository.findAll();
    }

    // ✅ Buscar passageiro por ID
    public Optional<Passageiro> buscarPassageiroPorId(Long id) {
        return passageiroRepository.findById(id);
    }

    // ✅ Deletar passageiro
    public void deletarPassageiro(Long id) {
        passageiroRepository.deleteById(id);
    }

    // ✅ Buscar por e-mail
    public Passageiro buscarPorEmail(String email) {
        return passageiroRepository.findByEmail(email);
    }
}

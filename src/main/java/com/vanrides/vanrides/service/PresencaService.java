package com.vanrides.vanrides.service;

import com.vanrides.vanrides.model.Presenca;
import com.vanrides.vanrides.repository.PresencaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PresencaService {

    @Autowired
    private PresencaRepository presencaRepository;

    public Presenca salvarPresenca(Presenca presenca) {
        return presencaRepository.save(presenca);
    }

    public List<Presenca> listarTodasPresencas() {
        return presencaRepository.findAll();
    }

    public Optional<Presenca> buscarPresencaPorId(Long id) {
        return presencaRepository.findById(id);
    }

    public void deletarPresenca(Long id) {
        presencaRepository.deleteById(id);
    }

    public List<Presenca> listarPresencasPorPassageiro(Long passageiroId) {
        return presencaRepository.findByPassageiroId(passageiroId);
    }

    public List<Presenca> listarPresencasPorData(LocalDate dataPresenca) {
        return presencaRepository.findByDataPresenca(dataPresenca);
    }

    // ✅ NOVO: verifica se já existe presença para o passageiro no dia
    public boolean jaExistePresenca(Long passageiroId, LocalDate data) {
        return !presencaRepository.findByPassageiroIdAndDataPresenca(passageiroId, data).isEmpty();
    }
}

package com.vanrides.vanrides.service;

import com.vanrides.vanrides.model.LocalizacaoMotorista;
import com.vanrides.vanrides.repository.LocalizacaoMotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalizacaoMotoristaService {

    @Autowired
    private LocalizacaoMotoristaRepository repository;

    public LocalizacaoMotorista salvar(LocalizacaoMotorista localizacao) {
        return repository.save(localizacao);
    }

    public List<LocalizacaoMotorista> buscarHistoricoPorMotorista(Long motoristaId) {
        return repository.findByMotoristaIdOrderByDataHoraDesc(motoristaId);
    }

    public LocalizacaoMotorista buscarUltimaPorMotorista(Long motoristaId) {
        List<LocalizacaoMotorista> lista = repository.findByMotoristaIdOrderByDataHoraDesc(motoristaId);
        return lista.isEmpty() ? null : lista.get(0);
    }
}

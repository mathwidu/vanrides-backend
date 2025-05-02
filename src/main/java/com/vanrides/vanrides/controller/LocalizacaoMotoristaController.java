package com.vanrides.vanrides.controller;

import com.vanrides.vanrides.model.LocalizacaoMotorista;
import com.vanrides.vanrides.model.Motorista;
import com.vanrides.vanrides.service.LocalizacaoMotoristaService;
import com.vanrides.vanrides.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/localizacoes")
public class LocalizacaoMotoristaController {

    @Autowired
    private LocalizacaoMotoristaService localizacaoService;

    @Autowired
    private MotoristaService motoristaService;

    @PostMapping("/{idMotorista}")
    public ResponseEntity<?> registrarLocalizacao(
            @PathVariable Long idMotorista,
            @RequestBody LocalizacaoMotorista localizacao) {

        Motorista motorista = motoristaService.buscarMotoristaPorId(idMotorista).orElse(null);
        if (motorista == null) {
            return ResponseEntity.badRequest().body("Motorista não encontrado.");
        }

        localizacao.setMotorista(motorista);
        localizacao.setDataHora(LocalDateTime.now());

        LocalizacaoMotorista salva = localizacaoService.salvar(localizacao);
        return ResponseEntity.ok(salva);
    }

    @GetMapping("/{idMotorista}")
    public ResponseEntity<?> ultimaLocalizacao(@PathVariable Long idMotorista) {
        LocalizacaoMotorista ultima = localizacaoService.buscarUltimaPorMotorista(idMotorista);
        if (ultima == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ultima);
    }

    @GetMapping("/{idMotorista}/historico")
    public ResponseEntity<List<LocalizacaoMotorista>> historico(@PathVariable Long idMotorista) {
        return ResponseEntity.ok(localizacaoService.buscarHistoricoPorMotorista(idMotorista));
    }
}

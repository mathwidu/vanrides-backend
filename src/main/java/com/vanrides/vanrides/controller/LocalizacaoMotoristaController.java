package com.vanrides.vanrides.controller;

import com.vanrides.vanrides.model.LocalizacaoMotorista;
import com.vanrides.vanrides.model.Motorista;
import com.vanrides.vanrides.service.LocalizacaoMotoristaService;
import com.vanrides.vanrides.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/localizacao") // 🔥 Corrigido para bater com o Flutter
public class LocalizacaoMotoristaController {

    @Autowired
    private LocalizacaoMotoristaService localizacaoService;

    @Autowired
    private MotoristaService motoristaService;

    // 🔥 Registrar uma nova localização via REST (opcional, o WebSocket já faz isso)
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
        System.out.println("[DEBUG BACKEND] 📥 Localização salva via REST para motorista ID: " + idMotorista);
        return ResponseEntity.ok(salva);
    }

    // ✅ Endpoint que o PASSAGEIRO usa para obter a última localização
    @GetMapping("/{idMotorista}")
    public ResponseEntity<?> ultimaLocalizacao(@PathVariable Long idMotorista) {
        LocalizacaoMotorista ultima = localizacaoService.buscarUltimaPorMotorista(idMotorista);
        if (ultima == null) {
            System.out.println("[DEBUG BACKEND] ⚠️ Nenhuma localização encontrada para motorista ID: " + idMotorista);
            return ResponseEntity.noContent().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("latitude", ultima.getLatitude());
        response.put("longitude", ultima.getLongitude());

        System.out.println("[DEBUG BACKEND] ✅ Última localização retornada para motorista ID: " + idMotorista);
        return ResponseEntity.ok(response);
    }

    // 🔥 Endpoint opcional para consultar o histórico
    @GetMapping("/{idMotorista}/historico")
    public ResponseEntity<List<LocalizacaoMotorista>> historico(@PathVariable Long idMotorista) {
        System.out.println("[DEBUG BACKEND] 📜 Histórico de localizações solicitado para motorista ID: " + idMotorista);
        return ResponseEntity.ok(localizacaoService.buscarHistoricoPorMotorista(idMotorista));
    }
}

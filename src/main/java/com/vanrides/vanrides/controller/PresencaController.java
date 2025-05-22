package com.vanrides.vanrides.controller;

import com.vanrides.vanrides.model.Presenca;
import com.vanrides.vanrides.service.PresencaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/presencas")
public class PresencaController {

    @Autowired
    private PresencaService presencaService;

    @PostMapping
    public ResponseEntity<?> criarPresenca(@RequestBody Presenca presenca) {
        boolean existe = presencaService.jaExistePresenca(
                presenca.getPassageiro().getId(),
                presenca.getDataPresenca()
        );

        if (existe) {
            return ResponseEntity.status(409).body("Já existe presença registrada para este dia.");
        }

        Presenca salva = presencaService.salvarPresenca(presenca);
        return ResponseEntity.ok(salva);
    }

    @GetMapping
    public ResponseEntity<List<Presenca>> listarPresencas() {
        return ResponseEntity.ok(presencaService.listarTodasPresencas());
    }

    @GetMapping("/passageiro/{passageiroId}")
    public ResponseEntity<List<Presenca>> listarPorPassageiro(@PathVariable Long passageiroId) {
        return ResponseEntity.ok(presencaService.listarPresencasPorPassageiro(passageiroId));
    }

    @GetMapping("/data")
    public ResponseEntity<List<Presenca>> listarPorData(@RequestParam("data")
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPresenca) {
        return ResponseEntity.ok(presencaService.listarPresencasPorData(dataPresenca));
    }

    @GetMapping("/motorista/{motoristaId}")
    public ResponseEntity<List<Presenca>> listarPorMotoristaEData(
            @PathVariable Long motoristaId,
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(presencaService.listarPresencasPorMotoristaEData(motoristaId, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPresenca(@PathVariable Long id) {
        Optional<Presenca> presenca = presencaService.buscarPresencaPorId(id);

        if (presenca.isPresent()) {
            presencaService.deletarPresenca(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

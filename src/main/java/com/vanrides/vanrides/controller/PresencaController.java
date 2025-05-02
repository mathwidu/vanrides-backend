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

    // ✅ Atualizado para bloquear duplicidade
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
        List<Presenca> presencas = presencaService.listarTodasPresencas();
        return ResponseEntity.ok(presencas);
    }

    @GetMapping("/passageiro/{passageiroId}")
    public ResponseEntity<List<Presenca>> listarPresencasPorPassageiro(@PathVariable Long passageiroId) {
        List<Presenca> presencas = presencaService.listarPresencasPorPassageiro(passageiroId);
        return ResponseEntity.ok(presencas);
    }

    @GetMapping("/data")
    public ResponseEntity<List<Presenca>> listarPresencasPorData(@RequestParam("data")
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                 LocalDate dataPresenca) {
        List<Presenca> presencas = presencaService.listarPresencasPorData(dataPresenca);
        return ResponseEntity.ok(presencas);
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

package com.vanrides.vanrides.websocket;

import com.vanrides.vanrides.dto.LocalizacaoDTO;
import com.vanrides.vanrides.model.LocalizacaoMotorista;
import com.vanrides.vanrides.model.Motorista;
import com.vanrides.vanrides.repository.LocalizacaoMotoristaRepository;
import com.vanrides.vanrides.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class LocalizacaoSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private LocalizacaoMotoristaRepository localizacaoMotoristaRepository;

    @Autowired
    private MotoristaRepository motoristaRepository;

    @MessageMapping("/enviarLocalizacao")
    public void receberLocalizacao(LocalizacaoDTO localizacao) {
    System.out.println("[DEBUG] 📥 Recebendo localização via WebSocket:");
    System.out.println("[DEBUG] Latitude: " + localizacao.getLatitude());
    System.out.println("[DEBUG] Longitude: " + localizacao.getLongitude());
    System.out.println("[DEBUG] ID do Motorista recebido: " + localizacao.getIdMotorista());

    // Verificar se o motorista existe
    Motorista motorista = motoristaRepository.findById(localizacao.getIdMotorista())
            .orElseThrow(() -> {
                System.out.println("[DEBUG] ❌ Motorista NÃO encontrado com ID: " + localizacao.getIdMotorista());
                return new RuntimeException("Motorista não encontrado com ID: " + localizacao.getIdMotorista());
            });

    System.out.println("[DEBUG] ✅ Motorista encontrado: " + motorista.getNome());

    // Criar entidade de localização
    LocalizacaoMotorista entidade = new LocalizacaoMotorista();
    entidade.setLatitude(localizacao.getLatitude());
    entidade.setLongitude(localizacao.getLongitude());
    entidade.setDataHora(LocalDateTime.now());
    entidade.setMotorista(motorista);

    localizacaoMotoristaRepository.save(entidade);

    System.out.println("[DEBUG] 💾 Localização salva no banco para o motorista ID: " + motorista.getId());

    messagingTemplate.convertAndSend("/topic/localizacao", localizacao);

    System.out.println("[DEBUG] 📤 Localização enviada no tópico /topic/localizacao");
}


    
}

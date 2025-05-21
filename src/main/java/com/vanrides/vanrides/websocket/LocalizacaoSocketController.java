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
        System.out.println("Localização recebida via WebSocket: " +
                localizacao.getLatitude() + ", " +
                localizacao.getLongitude() + ", " +
                "Motorista ID: " + localizacao.getIdMotorista());

        // 🔥 Buscar o motorista no banco de dados
        Motorista motorista = motoristaRepository.findById(localizacao.getIdMotorista())
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado com ID: " + localizacao.getIdMotorista()));

        // 🔥 Criar entidade de localização
        LocalizacaoMotorista entidade = new LocalizacaoMotorista();
        entidade.setLatitude(localizacao.getLatitude());
        entidade.setLongitude(localizacao.getLongitude());
        entidade.setDataHora(LocalDateTime.now()); // Grava a data e hora atual
        entidade.setMotorista(motorista); // Associa o motorista corretamente

        // 🔥 Salvar no banco
        localizacaoMotoristaRepository.save(entidade);

        // 🔥 Enviar localização para todos os inscritos no tópico
        messagingTemplate.convertAndSend("/topic/localizacao", localizacao);
    }
}

package com.vanrides.vanrides.websocket;

import com.vanrides.vanrides.dto.LocalizacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LocalizacaoSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/enviarLocalizacao")
    public void receberLocalizacao(LocalizacaoDTO localizacao) {
        System.out.println("Localização recebida via WebSocket: " + localizacao.getLatitude() + ", " + localizacao.getLongitude());

        // Faz broadcast para todos os ouvintes no tópico
        messagingTemplate.convertAndSend("/topic/localizacao", localizacao);
    }
}

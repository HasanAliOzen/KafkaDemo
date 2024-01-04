package dev.travula.service;

import dev.travula.dto.KMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerService {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group}")
    public void listen(@Payload KMessage message) {
        log.info("Received Message : " + message);
        messagingTemplate.convertAndSend("/topic/kafka", message);
    }
}

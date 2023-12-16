package dev.travula.service;

import dev.travula.dto.KMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListenerService {

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group}")
    public void listen(@Payload KMessage message) {
        log.info("Received Message : " + message);
    }
}

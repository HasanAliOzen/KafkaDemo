package dev.travula.api;

import dev.travula.dto.KMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ResourceController {

    @Value("${kafka.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, KMessage> kafkaTemplete;

    @PostMapping("/message")
    public void sendMessage(@RequestBody KMessage message) {
        kafkaTemplete.send(kafkaTopic, UUID.randomUUID().toString(),message);

    }


}

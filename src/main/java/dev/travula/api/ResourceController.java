package dev.travula.api;

import dev.travula.dto.KMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class ResourceController {

    @Value("${kafka.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, KMessage> kafkaTemplate;

    @PostMapping("/message")
    public ResponseEntity<Void> sendMessage(@RequestBody KMessage message) {
        kafkaTemplate.send(kafkaTopic, UUID.randomUUID().toString(),message);
        return ResponseEntity.noContent().build();
    }

    @MessageMapping("/send")
    public KMessage receiveMessage(@Payload KMessage message){
        //Sending this message to all the subscribers
        kafkaTemplate.send(kafkaTopic, UUID.randomUUID().toString(),message);
        return message;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/kafka")
    public KMessage addUser(@Payload KMessage message, SimpMessageHeaderAccessor headerAccessor){
        // Add user in web socket session
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.sender());
        return message;
    }
}

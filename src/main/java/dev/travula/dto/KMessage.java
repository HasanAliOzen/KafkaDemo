package dev.travula.dto;

import java.sql.Timestamp;
import java.util.UUID;

public record KMessage(
        UUID id,
        String sender,
        String message,
        Timestamp timestamp
) {

    public KMessage {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (timestamp == null) {
            timestamp = new Timestamp(System.currentTimeMillis());
        }
    }
}

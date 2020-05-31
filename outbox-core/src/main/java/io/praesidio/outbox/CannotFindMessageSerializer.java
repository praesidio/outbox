package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

public class CannotFindMessageSerializer extends RuntimeException {
    public CannotFindMessageSerializer(MessageType type) {
        super("Cannot find message serializer for type " + type.getValue());
    }
}

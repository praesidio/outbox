package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

class CannotFindMessageSerializer extends RuntimeException {
    CannotFindMessageSerializer(MessageType type) {
        super("Cannot find message serializer for type " + type.getValue());
    }
}

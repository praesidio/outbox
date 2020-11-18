package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

class MessageSerializerNotFoundException extends RuntimeException {
    MessageSerializerNotFoundException(MessageType type) {
        super("Cannot find message serializer for type " + type.getValue());
    }
}

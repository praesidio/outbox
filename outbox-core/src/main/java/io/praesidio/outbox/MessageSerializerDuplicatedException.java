package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

class MessageSerializerDuplicatedException extends RuntimeException {
    MessageSerializerDuplicatedException(MessageType messageType) {
        super("Duplicate serializers for message type: " + messageType.getValue());
    }
}

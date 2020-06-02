package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

class SerializerDuplicationException extends RuntimeException {
    SerializerDuplicationException(MessageType messageType) {
    }
}

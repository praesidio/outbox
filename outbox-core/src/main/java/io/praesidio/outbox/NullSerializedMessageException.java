package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

class NullSerializedMessageException extends RuntimeException {
    NullSerializedMessageException(MessageType type) {
        super("Message returned by the serializer for type " + type + " was null. It must never be null.");
    }
}

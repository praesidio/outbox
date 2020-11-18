package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

class MessageRelayProviderNotFoundException extends RuntimeException {
    MessageRelayProviderNotFoundException(MessageType type) {
        super("Cannot find message relay provider for type " + type.getValue());
    }
}

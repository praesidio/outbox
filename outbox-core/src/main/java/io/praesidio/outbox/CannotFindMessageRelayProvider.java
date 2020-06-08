package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

class CannotFindMessageRelayProvider extends RuntimeException {
    CannotFindMessageRelayProvider(MessageType type) {
        super("Cannot find message relay provider for type " + type.getValue());
    }
}

package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

public class CannotFindMessageRelayProvider extends RuntimeException {
    public CannotFindMessageRelayProvider(MessageType type) {
        super("Cannot find message relay provider for type " + type.getValue());
    }
}

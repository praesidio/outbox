package io.praesidio.outbox.spi;

import io.praesidio.outbox.values.MessageType;

public interface MessageRelayProvider<T extends SendMessageCommand> {
    void relay(T command);
    MessageType getType();
}

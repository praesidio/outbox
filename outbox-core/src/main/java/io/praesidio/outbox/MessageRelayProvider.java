package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

public interface MessageRelayProvider {
    void relay(SendMessageCommand sendMessageCommand);
    MessageType getType();
}

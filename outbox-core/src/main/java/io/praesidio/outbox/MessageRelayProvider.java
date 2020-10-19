package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

public interface MessageRelayProvider {
    void relay(Message message);
    MessageType getType();
}

package io.praesidio.outbox.spi;

import io.praesidio.outbox.Message;
import io.praesidio.outbox.values.MessageType;

public interface MessageRelayProvider {
    void relay(Message message);
    MessageType getType();
}

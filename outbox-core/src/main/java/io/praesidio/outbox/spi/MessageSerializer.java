package io.praesidio.outbox.spi;

import io.praesidio.outbox.Message;
import io.praesidio.outbox.values.MessageType;

public interface MessageSerializer {
    Message serialize(SendMessageCommand sendMessageCommand);
    MessageType getType();
}

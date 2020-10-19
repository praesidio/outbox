package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

public interface MessageSerializer {
    Message serialize(SendMessageCommand sendMessageCommand);
    MessageType getType();
}

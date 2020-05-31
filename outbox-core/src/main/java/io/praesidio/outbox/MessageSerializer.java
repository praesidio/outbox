package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

public interface MessageSerializer {
    Message convert(SendMessageCommand sendMessageCommand);
    MessageType getType();
}

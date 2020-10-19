package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

public interface SendMessageCommand {
    MessageType getType();
}

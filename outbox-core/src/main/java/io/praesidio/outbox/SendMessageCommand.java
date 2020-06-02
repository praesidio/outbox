package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

@FunctionalInterface
public interface SendMessageCommand {
    MessageType getType();
}

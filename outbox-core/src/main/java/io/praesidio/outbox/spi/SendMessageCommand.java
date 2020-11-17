package io.praesidio.outbox.spi;

import io.praesidio.outbox.values.MessageType;

public interface SendMessageCommand {
    MessageType getType();
}

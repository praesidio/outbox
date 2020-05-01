package io.praesidio.outbox;

import io.praesidio.outbox.values.PraesidioMessageType;

public interface PraesidioSendMessageCommand {
    PraesidioMessageType getType();
}

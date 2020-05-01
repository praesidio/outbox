package io.praesidio.outbox;

import io.praesidio.outbox.values.PraesidioMessageType;

public interface PraesidioMessageSerializer {
    PraesidioMessage convert(PraesidioSendMessageCommand sendMessageCommand);
    PraesidioMessageType getType();
}

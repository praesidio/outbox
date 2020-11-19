package io.praesidio.outbox.spi;

import io.praesidio.outbox.Message;
import io.praesidio.outbox.values.MessageType;

public interface MessageSerializer<T extends SendMessageCommand> {
    Message serialize(T sendMessageCommand);
    T deserialize(Message message);
    MessageType getType();
}

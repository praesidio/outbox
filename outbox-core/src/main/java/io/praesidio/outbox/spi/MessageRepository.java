package io.praesidio.outbox.spi;

import io.praesidio.outbox.Message;
import io.praesidio.outbox.values.MessageId;

import java.util.List;

public interface MessageRepository {

    void save(Message message);

    List<Message> findMessagesToRelay();

    void markAsSent(MessageId messageId);
}

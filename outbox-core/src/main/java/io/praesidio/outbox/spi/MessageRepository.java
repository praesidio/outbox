package io.praesidio.outbox.spi;

import io.praesidio.outbox.Message;
import io.praesidio.outbox.values.MessageId;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    void save(Message message);

    List<Message> findMessagesToRelay();

    void markAsSent(MessageId messageId);

    Optional<Message> findById(MessageId messageId);
}

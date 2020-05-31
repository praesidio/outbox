package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageId;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    void save(Message message);

    Optional<Message> findMessage(MessageId id);

    List<Message> findMessagesToRelay();
}

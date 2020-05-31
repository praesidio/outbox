package io.praesidio.outbox.stubs;

import io.praesidio.outbox.Message;
import io.praesidio.outbox.values.MessageId;
import io.praesidio.outbox.MessageRepository;

import java.util.*;

public class StubMessageRepository implements MessageRepository {

    private final Map<MessageId, Message> messages = new HashMap<>();

    @Override
    public void save(Message message) {
        messages.put(message.getId(), message);
    }

    @Override
    public Optional<Message> findMessage(MessageId id) {
        return Optional.ofNullable(messages.get(id));
    }

    @Override
    public List<Message> findMessagesToRelay() {
        throw new RuntimeException("Not implemented!");
    }

    public Collection<Message> getAll() {
        return messages.values();
    }
}

package io.praesidio.outbox.stubs;

import io.praesidio.outbox.Message;
import io.praesidio.outbox.spi.MessageRepository;
import io.praesidio.outbox.values.MessageId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StubMessageRepository implements MessageRepository {

    private final Map<MessageId, Message> messages = new HashMap<>();
    @Getter
    private final Set<Message> sentMessages = new HashSet<>();

    @Override
    public void save(Message message) {
        messages.put(message.getId(), message);
    }

    @Override
    public List<Message> findMessagesToRelay() {
        return new ArrayList<>(messages.values());
    }

    @Override
    public void markAsSent(MessageId messageId) {
        Message message = messages
                .values()
                .stream()
                .filter(m -> messageId.equals(m.getId())).findFirst()
                .orElseThrow(() -> new MessageNotFoundException(messageId));
        sentMessages.add(message);
    }

    public Collection<Message> getAll() {
        return messages.values();
    }
}

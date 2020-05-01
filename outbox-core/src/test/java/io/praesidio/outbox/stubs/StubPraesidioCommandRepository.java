package io.praesidio.outbox.stubs;

import io.praesidio.outbox.PraesidioMessage;
import io.praesidio.outbox.PraesidioSendMessageCommand;
import io.praesidio.outbox.values.PraesidioMessageId;
import io.praesidio.outbox.PraesidioCommandRepository;

import java.util.*;

public class StubPraesidioCommandRepository implements PraesidioCommandRepository {

    private final Map<PraesidioMessageId, PraesidioMessage> messages = new HashMap<>();

    @Override
    public void save(PraesidioMessage message) {
        messages.put(message.getId(), message);
    }

    @Override
    public Optional<PraesidioMessage> findMessage(PraesidioMessageId id) {
        return Optional.ofNullable(messages.get(id));
    }

    @Override
    public List<PraesidioMessage> findMessagesToRelay() {
        throw new RuntimeException("Not implemented!");
    }

    public Collection<PraesidioMessage> getAll() {
        return messages.values();
    }
}

package io.praesidio.outbox.stubs;

import io.praesidio.outbox.TransactionalEventPublisher;
import io.praesidio.outbox.values.MessageId;

import java.util.ArrayList;
import java.util.List;

public class StubTransactionalEventPublisher implements TransactionalEventPublisher {

    private final List<MessageId> messageIds = new ArrayList<>();

    @Override
    public void processAfterCommit(MessageId messageId) {
        messageIds.add(messageId);
    }

    public List<MessageId> getMessageIds() {
        return messageIds;
    }
}

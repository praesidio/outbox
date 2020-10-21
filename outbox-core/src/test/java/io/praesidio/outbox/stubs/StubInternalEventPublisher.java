package io.praesidio.outbox.stubs;

import io.praesidio.outbox.InternalEventPublisher;
import io.praesidio.outbox.values.MessageId;

import java.util.ArrayList;
import java.util.List;

public class StubInternalEventPublisher implements InternalEventPublisher {

    private final List<MessageId> messageIds = new ArrayList<>();

    @Override
    public void messageReadyForProcessingAfterCommit(MessageId messageId) {
        messageIds.add(messageId);
    }

    public List<MessageId> getMessageIds() {
        return messageIds;
    }
}

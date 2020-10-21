package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageId;

public interface InternalEventPublisher {
    void messageReadyForProcessingAfterCommit(MessageId messageId);
}

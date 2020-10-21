package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageId;

public interface TransactionalEventPublisher {
    void processAfterCommit(MessageId messageId);
}

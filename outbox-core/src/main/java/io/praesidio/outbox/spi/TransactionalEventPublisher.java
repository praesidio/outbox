package io.praesidio.outbox.spi;

import io.praesidio.outbox.values.MessageId;

public interface TransactionalEventPublisher {
    void processAfterCommit(MessageId messageId);
}

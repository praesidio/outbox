package io.praesidio.outbox;

import io.praesidio.outbox.spi.TransactionalEventPublisher;
import io.praesidio.outbox.values.MessageId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SpringTransactionalEventPublisher implements TransactionalEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void processAfterCommit(MessageId messageId) {
        applicationEventPublisher.publishEvent(new SpringOutboxEvent(messageId));
    }

}

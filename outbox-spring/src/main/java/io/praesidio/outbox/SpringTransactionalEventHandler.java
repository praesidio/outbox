package io.praesidio.outbox;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
class SpringTransactionalEventHandler {

    private final MessageRelay messageRelay;

    @TransactionalEventListener
    public void handle(SpringOutboxEvent event) {
        messageRelay.relayMessage(event.getMessageId());
    }
}

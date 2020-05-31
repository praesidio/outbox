package io.praesidio.outbox;

import java.util.concurrent.Future;

public interface OutboxRelayProvider {
    Future<?> relay(SendMessageCommand sendMessageCommand);
}

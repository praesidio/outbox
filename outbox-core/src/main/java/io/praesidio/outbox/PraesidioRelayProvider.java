package io.praesidio.outbox;

import java.util.concurrent.Future;

public interface PraesidioRelayProvider {
    Future<?> relay(PraesidioSendMessageCommand praesidioSendMessageCommand);
}

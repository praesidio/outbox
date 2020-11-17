package io.praesidio.outbox;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
class SpringScheduler {

    private final MessageRelay messageRelay;

    @Scheduled
    public void execute() {
        messageRelay.relayMessages();
    }
}

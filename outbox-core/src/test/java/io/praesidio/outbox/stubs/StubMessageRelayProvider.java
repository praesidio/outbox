package io.praesidio.outbox.stubs;

import io.praesidio.outbox.Message;
import io.praesidio.outbox.spi.MessageRelayProvider;
import io.praesidio.outbox.values.MessageType;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

import static io.praesidio.outbox.stubs.StubImplementationConstants.MESSAGE_TYPE;

public class StubMessageRelayProvider implements MessageRelayProvider {

    @Getter
    private final Set<Message> messages = new HashSet<>();

    @Override
    public void relay(Message message) {
        messages.add(message);
    }

    @Override
    public MessageType getType() {
        return MESSAGE_TYPE;
    }
}

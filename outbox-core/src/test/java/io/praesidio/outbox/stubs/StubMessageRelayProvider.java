package io.praesidio.outbox.stubs;

import io.praesidio.outbox.spi.MessageRelayProvider;
import io.praesidio.outbox.values.MessageType;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

import static io.praesidio.outbox.stubs.StubImplementationConstants.MESSAGE_TYPE;

public class StubMessageRelayProvider implements MessageRelayProvider<StubSendMessageCommand> {

    @Getter
    private final Set<StubSendMessageCommand> commands = new HashSet<>();

    @Override
    public void relay(StubSendMessageCommand command) {
        commands.add(command);
    }

    @Override
    public MessageType getType() {
        return MESSAGE_TYPE;
    }
}

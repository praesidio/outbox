package io.praesidio.outbox.stubs;

import io.praesidio.outbox.MessageRelayProvider;
import io.praesidio.outbox.SendMessageCommand;
import io.praesidio.outbox.values.MessageType;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

import static io.praesidio.outbox.stubs.StubImplementationConstants.MESSAGE_TYPE;

public class StubMessageRelayProvider implements MessageRelayProvider {

    @Getter
    private final Set<SendMessageCommand> commands = new HashSet<>();

    @Override
    public void relay(SendMessageCommand sendMessageCommand) {
        commands.add(sendMessageCommand);
    }

    @Override
    public MessageType getType() {
        return MESSAGE_TYPE;
    }
}

package io.praesidio.outbox.stubs;

import io.praesidio.outbox.spi.SendMessageCommand;
import io.praesidio.outbox.values.MessageType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StubSendMessageCommand implements SendMessageCommand {
    MessageType type = MessageType.of("STUB");
    String content;
    String metadata;
}

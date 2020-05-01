package io.praesidio.outbox.stubs;

import io.praesidio.outbox.PraesidioSendMessageCommand;
import io.praesidio.outbox.values.PraesidioMessageType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StubPraesidioSendMessageCommand implements PraesidioSendMessageCommand {
    PraesidioMessageType type = PraesidioMessageType.of("STUB");
    String content;
    String metadata;
}

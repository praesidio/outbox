package io.praesidio.outbox;

import io.praesidio.outbox.values.PraesidioMessageContent;
import io.praesidio.outbox.values.PraesidioMessageId;
import io.praesidio.outbox.values.PraesidioMessageMetadata;
import io.praesidio.outbox.values.PraesidioMessageType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PraesidioMessage {
    PraesidioMessageId id;
    PraesidioMessageType type;
    PraesidioMessageMetadata metadata;
    PraesidioMessageContent content;
}

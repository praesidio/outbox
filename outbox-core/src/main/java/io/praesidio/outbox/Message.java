package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageContent;
import io.praesidio.outbox.values.MessageId;
import io.praesidio.outbox.values.MessageMetadata;
import io.praesidio.outbox.values.MessageType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Message {
    MessageId id;
    MessageType type;
    MessageMetadata metadata;
    MessageContent content;
}

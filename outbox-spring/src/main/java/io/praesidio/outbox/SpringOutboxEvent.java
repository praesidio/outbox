package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageId;
import lombok.Value;

@Value
class SpringOutboxEvent {
    MessageId messageId;
}

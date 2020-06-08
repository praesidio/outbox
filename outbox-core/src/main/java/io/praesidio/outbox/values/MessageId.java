package io.praesidio.outbox.values;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class MessageId {
    @NonNull
    UUID value;
}

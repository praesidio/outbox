package io.praesidio.outbox.values;

import lombok.Value;

@Value(staticConstructor = "of")
public class MessageContent {
    String value;
}

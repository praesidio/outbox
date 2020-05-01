package io.praesidio.outbox.values;

import lombok.Value;

@Value(staticConstructor = "of")
public class PraesidioMessageContent {
    String value;
}

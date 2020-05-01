package io.praesidio.outbox;

import io.praesidio.outbox.values.PraesidioMessageType;

public class CannotFindPraesidioMessageSerializer extends RuntimeException {
    public CannotFindPraesidioMessageSerializer(PraesidioMessageType type) {
        super("Cannot find message serializer for type " + type.getValue());
    }
}

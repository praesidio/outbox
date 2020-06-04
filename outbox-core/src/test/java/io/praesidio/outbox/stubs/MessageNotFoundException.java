package io.praesidio.outbox.stubs;

import io.praesidio.outbox.values.MessageId;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(MessageId messageId) {
        super("Message not found: " + messageId);
    }
}

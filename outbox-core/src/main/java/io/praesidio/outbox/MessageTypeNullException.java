package io.praesidio.outbox;

import io.praesidio.outbox.spi.MessageSerializer;

class MessageTypeNullException extends RuntimeException {
    MessageTypeNullException(Class<? extends MessageSerializer> aClass) {
        super("Class does not provide a valid message type:" + aClass.getName());
    }
}

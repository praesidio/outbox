package io.praesidio.outbox;

class NullMessageTypeException extends RuntimeException {
    NullMessageTypeException(Class<? extends MessageSerializer> aClass) {
        super("Class does not provide a valid message type:" + aClass.getName());
    }
}

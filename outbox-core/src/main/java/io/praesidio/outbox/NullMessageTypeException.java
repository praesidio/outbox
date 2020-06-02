package io.praesidio.outbox;

class NullMessageTypeException extends RuntimeException {
    NullMessageTypeException(Class<? extends MessageSerializer> aClass) {
    }
}

package io.praesidio.outbox;

public class TransactionRequiredException extends RuntimeException {
    TransactionRequiredException() {
        super("Transaction is required.");
    }
}

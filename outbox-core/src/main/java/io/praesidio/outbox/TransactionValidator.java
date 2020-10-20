package io.praesidio.outbox;

public interface TransactionValidator {
    boolean isTransactionActive();
}

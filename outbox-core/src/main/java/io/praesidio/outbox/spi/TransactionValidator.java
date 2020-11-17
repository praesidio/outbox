package io.praesidio.outbox.spi;

public interface TransactionValidator {
    boolean isTransactionActive();
}

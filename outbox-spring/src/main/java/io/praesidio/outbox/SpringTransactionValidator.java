package io.praesidio.outbox;

import io.praesidio.outbox.spi.TransactionValidator;
import org.springframework.transaction.support.TransactionSynchronizationManager;

class SpringTransactionValidator implements TransactionValidator {

    @Override
    public boolean isTransactionActive() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

}

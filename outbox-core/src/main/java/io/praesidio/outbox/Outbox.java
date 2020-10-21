package io.praesidio.outbox;

import io.praesidio.outbox.spi.*;
import lombok.NonNull;

import java.util.Collection;

public class Outbox {

    private final MessageFactory messageFactory;
    private final MessageRepository messageRepository;
    private final TransactionValidator transactionValidator;
    private final TransactionalEventPublisher transactionalEventPublisher;

    public Outbox(@NonNull MessageRepository messageRepository,
                  @NonNull Collection<MessageSerializer> serializers,
                  @NonNull TransactionValidator transactionValidator,
                  @NonNull TransactionalEventPublisher transactionalEventPublisher) {
        this.messageRepository = messageRepository;
        this.transactionValidator = transactionValidator;
        this.messageFactory = new MessageFactory(serializers);
        this.transactionalEventPublisher = transactionalEventPublisher;
    }

    public void send(SendMessageCommand command) {
        if (!transactionValidator.isTransactionActive()) {
            throw new TransactionRequiredException();
        }
        Message message = messageFactory.create(command);
        messageRepository.save(message);
        transactionalEventPublisher.processAfterCommit(message.getId());
    }
}

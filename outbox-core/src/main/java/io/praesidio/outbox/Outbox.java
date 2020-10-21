package io.praesidio.outbox;

import lombok.NonNull;

import java.util.Collection;

public class Outbox {

    private final MessageFactory messageFactory;
    private final MessageRepository messageRepository;
    private final TransactionValidator transactionValidator;
    private final InternalEventPublisher internalEventPublisher;

    public Outbox(@NonNull MessageRepository messageRepository,
                  @NonNull Collection<MessageSerializer> serializers,
                  @NonNull TransactionValidator transactionValidator,
                  @NonNull InternalEventPublisher internalEventPublisher) {
        this.messageRepository = messageRepository;
        this.transactionValidator = transactionValidator;
        this.messageFactory = new MessageFactory(serializers);
        this.internalEventPublisher = internalEventPublisher;
    }

    public void send(SendMessageCommand command) {
        if (!transactionValidator.isTransactionActive()) {
            throw new TransactionRequiredException();
        }
        Message message = messageFactory.create(command);
        messageRepository.save(message);
        internalEventPublisher.messageReadyForProcessingAfterCommit(message.getId());
    }
}

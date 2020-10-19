package io.praesidio.outbox;

import lombok.NonNull;

import java.util.Collection;

public class Outbox {

    private final MessageFactory messageFactory;
    private final MessageRepository messageRepository;
    private final TransactionValidator transactionValidator;

    public Outbox(@NonNull MessageRepository messageRepository,
                  @NonNull Collection<MessageSerializer> serializers,
                  @NonNull TransactionValidator transactionValidator) {
        this.messageRepository = messageRepository;
        this.transactionValidator = transactionValidator;
        this.messageFactory = new MessageFactory(serializers);
    }

    public void send(SendMessageCommand command) {
        if (!transactionValidator.isTransactionActive()) {
            throw new TransactionRequiredException();
        }
        Message message = messageFactory.create(command);
        messageRepository.save(message);
    }
}

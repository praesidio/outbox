package io.praesidio.outbox;

import lombok.NonNull;

import java.util.Collection;

public class Outbox {

    private final MessageFactory messageFactory;
    private final MessageRepository messageRepository;

    public Outbox(@NonNull MessageRepository messageRepository,
                  @NonNull Collection<MessageSerializer> serializers) {
        this.messageRepository = messageRepository;
        this.messageFactory = new MessageFactory(serializers);
    }

    public void send(SendMessageCommand command) {
        Message message = messageFactory.create(command);
        // FIXME #10 make sure this is always invoked inside a transaction
        messageRepository.save(message);
    }
}

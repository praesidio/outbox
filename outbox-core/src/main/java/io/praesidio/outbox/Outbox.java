package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;
import lombok.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Outbox {

    private final MessageRepository messageRepository;
    private final Map<MessageType, MessageSerializer> serializers;

    public Outbox(@NonNull MessageRepository messageRepository,
                  @NonNull Collection<MessageSerializer> serializers) {
        this.messageRepository = messageRepository;
        this.serializers = serializers.stream().collect(Collectors.toMap(MessageSerializer::getType, s -> s));
    }

    public void send(SendMessageCommand command) {
        MessageType type = command.getType();
        Message message = Optional.ofNullable(serializers.get(type))
                                  .map(s -> s.convert(command))
                                  .orElseThrow(() -> new CannotFindMessageSerializer(type));
        // FIXME #10 make sure this is always invoked inside a transaction
        messageRepository.save(message);
    }
}

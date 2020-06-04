package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageRelay {

    private final MessageRepository messageRepository;
    private final Map<MessageType, MessageRelayProvider> messageRelayProviders;
    private final Map<MessageType, MessageSerializer> messageSerializers;

    public MessageRelay(
            MessageRepository messageRepository,
            Set<MessageRelayProvider> messageRelayProviders,
            Set<MessageSerializer> messageSerializers
    ) {
        this.messageRepository = messageRepository;
        this.messageRelayProviders = messageRelayProviders.stream().collect(Collectors.toMap(MessageRelayProvider::getType, s -> s));
        this.messageSerializers = messageSerializers.stream().collect(Collectors.toMap(MessageSerializer::getType, s -> s));
    }

    public void relayMessages() {
        messageRepository.findMessagesToRelay().forEach(this::relay);
    }

    private void relay(Message message) {
        MessageRelayProvider messageRelayProvider =
                Optional.ofNullable(messageRelayProviders.get(message.getType()))
                        .orElseThrow(() -> new CannotFindMessageRelayProvider(message.getType()));
        MessageSerializer messageSerializer =
                Optional.ofNullable(messageSerializers.get(message.getType()))
                        .orElseThrow(() -> new CannotFindMessageSerializer(message.getType()));
        messageRelayProvider.relay(messageSerializer.convert(message));
        // FIXME I don't think this needs to be in a transaction?
        messageRepository.markAsSent(message.getId());
    }
}

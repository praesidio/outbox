package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MessageRelay {

    private final MessageRepository messageRepository;
    private final Map<MessageType, MessageRelayProvider> messageRelayProviders;
    private final Map<MessageType, MessageSerializer> messageSerializers;

    public MessageRelay(
            MessageRepository messageRepository,
            Collection<MessageRelayProvider> messageRelayProviders,
            Collection<MessageSerializer> messageSerializers
    ) {
        this.messageRepository = messageRepository;
        this.messageRelayProviders = messageRelayProviders.stream().collect(Collectors.toMap(MessageRelayProvider::getType, s -> s));
        this.messageSerializers = messageSerializers.stream().collect(Collectors.toMap(MessageSerializer::getType, s -> s));
    }

    public void relayMessages() {
        messageRepository.findMessagesToRelay().forEach(this::relay);
    }

    private void relay(Message message) {
        MessageRelayProvider messageRelayProvider = getMessageRelayProvider(message);
        messageRelayProvider.relay(message);
        // FIXME #10 should this happen in transaction?
        messageRepository.markAsSent(message.getId());
    }

    private MessageSerializer getMessageSerializer(Message message) {
        return Optional.ofNullable(messageSerializers.get(message.getType()))
                .orElseThrow(() -> new CannotFindMessageSerializer(message.getType()));
    }

    private MessageRelayProvider getMessageRelayProvider(Message message) {
        return Optional.ofNullable(messageRelayProviders.get(message.getType()))
                .orElseThrow(() -> new CannotFindMessageRelayProvider(message.getType()));
    }
}

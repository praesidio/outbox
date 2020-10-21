package io.praesidio.outbox;

import io.praesidio.outbox.values.MessageType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class MessageRelay {

    private final MessageRepository messageRepository;
    private final Map<MessageType, MessageRelayProvider> messageRelayProviders;
    private final Map<MessageType, MessageSerializer> messageSerializers;
    private final TransactionValidator transactionValidator;

    public MessageRelay(
            MessageRepository messageRepository,
            Collection<MessageRelayProvider> messageRelayProviders,
            Collection<MessageSerializer> messageSerializers,
            TransactionValidator transactionValidator
    ) {
        this.messageRepository = messageRepository;
        this.messageRelayProviders = messageRelayProviders.stream().collect(toMap(MessageRelayProvider::getType, identity()));
        this.messageSerializers = messageSerializers.stream().collect(toMap(MessageSerializer::getType, identity()));
        this.transactionValidator = transactionValidator;
    }

    public void relayMessages() {
        messageRepository.findMessagesToRelay().forEach(this::relay);
    }

    private void relay(Message message) {
        if (!transactionValidator.isTransactionActive()) {
            throw new TransactionRequiredException();
        }
        MessageRelayProvider messageRelayProvider = getMessageRelayProvider(message);
        messageRelayProvider.relay(message);
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

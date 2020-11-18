package io.praesidio.outbox;

import io.praesidio.outbox.spi.MessageRelayProvider;
import io.praesidio.outbox.spi.MessageRepository;
import io.praesidio.outbox.spi.MessageSerializer;
import io.praesidio.outbox.spi.TransactionValidator;
import io.praesidio.outbox.values.MessageType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

class MessageRelay {

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
        MessageSerializer messageSerializer = getMessageSerializer(message);
        messageRelayProvider.relay(messageSerializer.deserialize(message));
        messageRepository.markAsSent(message.getId());
    }

    private MessageRelayProvider getMessageRelayProvider(Message message) {
        return Optional.ofNullable(messageRelayProviders.get(message.getType()))
                .orElseThrow(() -> new MessageRelayProviderNotFoundException(message.getType()));
    }

    private MessageSerializer getMessageSerializer(Message message) {
        return Optional.ofNullable(messageSerializers.get(message.getType()))
                .orElseThrow(() -> new CannotFindMessageSerializer(message.getType()));
    }
}

package io.praesidio.outbox;

import io.praesidio.outbox.spi.MessageRelayProvider;
import io.praesidio.outbox.spi.MessageRepository;
import io.praesidio.outbox.spi.TransactionValidator;
import io.praesidio.outbox.values.MessageId;
import io.praesidio.outbox.values.MessageType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

class MessageRelay {

    private final MessageRepository messageRepository;
    private final Map<MessageType, MessageRelayProvider> messageRelayProviders;
    private final TransactionValidator transactionValidator;

    public MessageRelay(
            MessageRepository messageRepository,
            Collection<MessageRelayProvider> messageRelayProviders,
            TransactionValidator transactionValidator
    ) {
        this.messageRepository = messageRepository;
        this.messageRelayProviders = messageRelayProviders.stream().collect(toMap(MessageRelayProvider::getType, identity()));
        this.transactionValidator = transactionValidator;
    }

    public void relayMessage(MessageId messageId) {
        messageRepository.findById(messageId)
                         .ifPresent(this::relay);
    }

    public void relayMessages() {
        messageRepository.findMessagesToRelay().forEach(this::relay);
    }

    private void relay(Message message) {
        // FIXME possible double sending of a message - we should check if already sent
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
                .orElseThrow(() -> new MessageSerializerNotFoundException(message.getType()));
    }
}

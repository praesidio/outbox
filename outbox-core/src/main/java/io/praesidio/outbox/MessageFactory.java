package io.praesidio.outbox;

import io.praesidio.outbox.spi.MessageSerializer;
import io.praesidio.outbox.spi.SendMessageCommand;
import io.praesidio.outbox.values.MessageType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class MessageFactory {

    private final Map<MessageType, MessageSerializer> serializers;

    MessageFactory(Collection<MessageSerializer> serializers) {
        this.serializers = new HashMap<>(serializers.size());
        serializers.forEach(serializer -> {
            MessageType messageType = serializer.getType();
            if (messageType == null) {
                throw new NullMessageTypeException(serializer.getClass());
            }
            if (this.serializers.containsKey(messageType)) {
                throw new SerializerDuplicationException(messageType);
            }
            this.serializers.put(messageType, serializer);
        });
    }

    Message create(SendMessageCommand command) {
        MessageSerializer messageSerializer =
                Optional.ofNullable(serializers.get(command.getType()))
                        .orElseThrow(() -> new CannotFindMessageSerializer(command.getType()));
        return Optional.ofNullable(messageSerializer.serialize(command))
                .orElseThrow(() -> new NullSerializedMessageException(command.getType()));
    }
}

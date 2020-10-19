package io.praesidio.outbox;

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
        return Optional.ofNullable(serializers.get(command.getType()))
                       .map(serializer -> serializer.serialize(command))
                       .orElseThrow(() -> new CannotFindMessageSerializer(command.getType()));
    }

}

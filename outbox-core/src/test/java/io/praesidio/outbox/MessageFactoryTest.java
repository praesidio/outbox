package io.praesidio.outbox;

import io.praesidio.outbox.stubs.StubImplementationConstants;
import io.praesidio.outbox.stubs.StubMessageSerializer;
import io.praesidio.outbox.stubs.StubSendMessageCommand;
import io.praesidio.outbox.values.MessageContent;
import io.praesidio.outbox.values.MessageMetadata;
import io.praesidio.outbox.values.MessageType;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MessageFactoryTest {

    private static final String CONTENT = "{data: 'Power'}";
    private static final MessageContent MESSAGE_CONTENT = MessageContent.of(CONTENT);
    private static final String METADATA = "{headers: [{key: 'Name', value: 'Value'}]}";
    private static final MessageMetadata MESSAGE_METADATA = MessageMetadata.of(METADATA);

    private final Set<MessageSerializer> serializers = Set.of(new StubMessageSerializer());
    private final MessageFactory messageFactory = new MessageFactory(serializers);

    @Test
    void initialization_when() {
        // given
        Set<MessageSerializer> serializers = Set.of(new StubMessageSerializer(), new StubMessageSerializer());

        // expected
        assertThrows(SerializerDuplicationException.class, () -> new MessageFactory(serializers));
    }

    @Test
    void initialization_when3() {
        // given
        Set<MessageSerializer> serializers = Set.of(messageSerializerWithNullMessageType());

        // expected
        assertThrows(NullMessageTypeException.class, () -> new MessageFactory(serializers));
    }

    @Test
    void initialization_when2() {
        // given
        SendMessageCommand command = () -> MessageType.of("NOT_SUPPORTED");

        // expected
        assertThrows(CannotFindMessageSerializer.class, () -> messageFactory.create(command));
    }

    @Test
    void initialization_when1() {
        // given
        StubSendMessageCommand command = StubSendMessageCommand.builder()
                                                               .content(CONTENT)
                                                               .metadata(METADATA)
                                                               .build();

        // when
        Message message = messageFactory.create(command);

        // then
        assertNotNull(message.getId());
        assertEquals(MESSAGE_CONTENT, message.getContent());
        assertEquals(MESSAGE_METADATA, message.getMetadata());
        assertEquals(StubImplementationConstants.MESSAGE_TYPE, message.getType());
    }

    private MessageSerializer messageSerializerWithNullMessageType() {
        return new MessageSerializer() {

            @Override
            public Message serialize(SendMessageCommand sendMessageCommand) {
                return null;
            }

            @Override
            public MessageType getType() {
                return null;
            }
        };
    }
}
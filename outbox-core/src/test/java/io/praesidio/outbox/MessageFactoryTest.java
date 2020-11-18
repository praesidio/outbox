package io.praesidio.outbox;

import io.praesidio.outbox.spi.MessageSerializer;
import io.praesidio.outbox.spi.SendMessageCommand;
import io.praesidio.outbox.stubs.StubImplementationConstants;
import io.praesidio.outbox.stubs.StubMessageSerializer;
import io.praesidio.outbox.stubs.StubSendMessageCommand;
import io.praesidio.outbox.util.Sets;
import io.praesidio.outbox.values.MessageContent;
import io.praesidio.outbox.values.MessageMetadata;
import io.praesidio.outbox.values.MessageType;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MessageFactoryTest {

    private static final String CONTENT = "{data: 'Power'}";
    private static final MessageContent MESSAGE_CONTENT = MessageContent.of(CONTENT);
    private static final String METADATA = "{headers: [{key: 'Name', value: 'Value'}]}";
    private static final MessageMetadata MESSAGE_METADATA = MessageMetadata.of(METADATA);
    public static final String TEST_TYPE = "TEST_TYPE";

    private final Set<MessageSerializer> serializers = Sets.of(new StubMessageSerializer());
    private final MessageFactory messageFactory = new MessageFactory(serializers);

    @Test
    void whenDuplicatedSerializersAreRegisteredThenAnExceptionIsThrown() {
        // given
        Set<MessageSerializer> serializers = Sets.of(new StubMessageSerializer(), new StubMessageSerializer());

        // expected
        assertThrows(MessageSerializerDuplicatedException.class, () -> new MessageFactory(serializers));
    }

    @Test
    void whenRegisteredSerializerReturnsNullTypeThenAnExceptionIsThrown() {
        // given
        Set<MessageSerializer> serializers = Sets.of(messageSerializerWithNullMessageType());

        // expected
        assertThrows(MessageTypeNullException.class, () -> new MessageFactory(serializers));
    }

    @Test
    void whenCommandDeclaresAnUnknownTypeThenAnExceptionIsThrown() {
        // given
        SendMessageCommand command = () -> MessageType.of("NOT_SUPPORTED");

        // expected
        assertThrows(MessageSerializerNotFoundException.class, () -> messageFactory.create(command));
    }

    @Test
    void whenMessageSerializerReturnsNullThenAnExceptionIsThrown() {
        // given
        SendMessageCommand command = () -> MessageType.of(TEST_TYPE);

        // and
        MessageFactory messageFactory = new MessageFactory(Sets.of(messageSerializerWithNullSerializeMethod()));

        // expected
        assertThrows(SerializedMessageNullException.class, () -> messageFactory.create(command));
    }

    @Test
    void whenValidCommandIsPassedThenMessageIsCreated() {
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
            public SendMessageCommand deserialize(Message message) {
                return null;
            }

            @Override
            public MessageType getType() {
                return null;
            }
        };
    }

    private MessageSerializer messageSerializerWithNullSerializeMethod() {
        return new MessageSerializer() {

            @Override
            public Message serialize(SendMessageCommand sendMessageCommand) {
                return null;
            }

            @Override
            public SendMessageCommand deserialize(Message message) {
                return null;
            }

            @Override
            public MessageType getType() {
                return MessageType.of(TEST_TYPE);
            }
        };
    }
}
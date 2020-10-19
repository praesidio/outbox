package io.praesidio.outbox;

import io.praesidio.outbox.stubs.StubMessageRelayProvider;
import io.praesidio.outbox.stubs.StubMessageRepository;
import io.praesidio.outbox.stubs.StubMessageSerializer;
import io.praesidio.outbox.stubs.StubSendMessageCommand;
import io.praesidio.outbox.values.MessageContent;
import io.praesidio.outbox.values.MessageId;
import io.praesidio.outbox.values.MessageMetadata;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageRelayTest {

    private final StubMessageRepository stubMessageRepository = new StubMessageRepository();
    private final StubMessageSerializer stubMessageSerializer = new StubMessageSerializer();
    private final StubMessageRelayProvider stubMessageRelayProvider = new StubMessageRelayProvider();
    private final MessageRelay messageRelay = new MessageRelay(
            stubMessageRepository,
            Collections.singleton(stubMessageRelayProvider),
            Collections.singleton(stubMessageSerializer)
    );

    @Test
    public void whenMessageRelayIsInvokedThenItUsesProviderToSendTheMessages() {
        // given
        StubSendMessageCommand command = StubSendMessageCommand.builder()
                .content("Test content")
                .metadata("Test metadata")
                .build();
        Message serializedMessage = stubMessageSerializer.serialize(command);
        stubMessageRepository.save(serializedMessage);

        // when
        messageRelay.relayMessages();

        // then
        assertEquals(1, stubMessageRelayProvider.getMessages().size());
        assertEquals(serializedMessage, stubMessageRelayProvider.getMessages().iterator().next());
    }

    @Test
    public void whenMessageRelaySendsMessageThenItMarksItAsSent() {
        // given
        StubSendMessageCommand command = StubSendMessageCommand.builder()
                .content("Test content")
                .metadata("Test metadata")
                .build();
        stubMessageRepository.save(stubMessageSerializer.serialize(command));

        // when
        messageRelay.relayMessages();

        // then
        assertEquals(1, stubMessageRepository.getSentMessages().size());
        Message sentMessage = stubMessageRepository.getSentMessages().iterator().next();
        assertEquals(command.getContent(), sentMessage.getContent().getValue());
    }
}

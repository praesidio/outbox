package io.praesidio.outbox;

import io.praesidio.outbox.stubs.StubMessageRelayProvider;
import io.praesidio.outbox.stubs.StubMessageRepository;
import io.praesidio.outbox.stubs.StubMessageSerializer;
import io.praesidio.outbox.stubs.StubSendMessageCommand;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageRelayTest {

    private final StubMessageRepository stubMessageRepository = new StubMessageRepository();
    private final StubMessageSerializer stubMessageSerializer = new StubMessageSerializer();
    private final StubMessageRelayProvider stubMessageRelayProvider = new StubMessageRelayProvider();

    @Test
    public void whenDuplicatedSerializersAreRegisteredThenAnExceptionIsThrown() {
        // expected
        assertThrows(IllegalStateException.class,
                () -> new MessageRelay(
                        stubMessageRepository,
                        Collections.singleton(stubMessageRelayProvider),
                        Arrays.asList(stubMessageSerializer, stubMessageSerializer),
                        () -> true
                )
        );
    }

    @Test
    public void whenDuplicatedMessageRelayProvidersAreRegisteredThenAnExceptionIsThrown() {
        // expected
        assertThrows(IllegalStateException.class,
                () -> new MessageRelay(
                        stubMessageRepository,
                        Arrays.asList(stubMessageRelayProvider, stubMessageRelayProvider),
                        Collections.singleton(stubMessageSerializer),
                        () -> true
                )
        );
    }

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
        createMessageRelay(true).relayMessages();

        // then
        assertEquals(1, stubMessageRelayProvider.getCommands().size());
        assertEquals(command, stubMessageRelayProvider.getCommands().iterator().next());
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
        createMessageRelay(true).relayMessages();

        // then
        assertEquals(1, stubMessageRepository.getSentMessages().size());
        Message sentMessage = stubMessageRepository.getSentMessages().iterator().next();
        assertEquals(command.getContent(), sentMessage.getContent().getValue());
    }

    @Test
    public void whenMessageRelaySendsMessageWithoutAnActiveTransactionThenAnExceptionIsThrown() {
        // given
        StubSendMessageCommand command = StubSendMessageCommand.builder()
                .content("Test content")
                .metadata("Test metadata")
                .build();
        stubMessageRepository.save(stubMessageSerializer.serialize(command));

        // expect
        assertThrows(TransactionRequiredException.class, () -> createMessageRelay(false).relayMessages());
    }

    private MessageRelay createMessageRelay(boolean isTransactionActive) {
        return new MessageRelay(
                stubMessageRepository,
                Collections.singleton(stubMessageRelayProvider),
                Collections.singleton(stubMessageSerializer),
                () -> isTransactionActive
        );
    }
}

package io.praesidio.outbox;

import io.praesidio.outbox.spi.SendMessageCommand;
import io.praesidio.outbox.stubs.StubImplementationConstants;
import io.praesidio.outbox.stubs.StubMessageRepository;
import io.praesidio.outbox.stubs.StubMessageSerializer;
import io.praesidio.outbox.stubs.StubSendMessageCommand;
import io.praesidio.outbox.stubs.StubTransactionalEventPublisher;
import io.praesidio.outbox.values.MessageId;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OutboxSaveTest {

    private static final String CONTENT = "test content";
    private static final String METADATA = "test metadata";
    private final StubMessageRepository repository = new StubMessageRepository();
    private final StubTransactionalEventPublisher internalEventPublisher = new StubTransactionalEventPublisher();

    @Test
    void whenTransactionIsNotActiveThenExceptionIsThrown() {
        // given
        SendMessageCommand command = givenIsCommand();

        // and
        Outbox outbox = createOutbox(false);

        // expected
        assertThrows(TransactionRequiredException.class, () -> outbox.send(command));
    }

    @Test
    void whenMessageIsSentViaOutboxThenItIsSavedInTheRepository() {
        // given
        SendMessageCommand command = givenIsCommand();

        // and
        Outbox outbox = createOutbox(true);

        // when
        outbox.send(command);

        // then
        assertEquals(1, repository.getAll().size());
        Message message = repository.getAll().iterator().next();
        assertNotNull(message.getId().getValue());
        assertEquals(StubImplementationConstants.MESSAGE_TYPE, message.getType());
        assertEquals(METADATA, message.getMetadata().getValue());
        assertEquals(CONTENT, message.getContent().getValue());
    }

    @Test
    void whenMessageIsSentViaOutboxThenMessageReadinessEventIsToBePublishedAfterCommit() {
        // given
        SendMessageCommand command = givenIsCommand();

        // and
        Outbox outbox = createOutbox(true);

        // when
        outbox.send(command);

        // then
        assertEquals(1, internalEventPublisher.getMessageIds().size());

        // and
        MessageId messageIdFromEventPublisher = internalEventPublisher.getMessageIds().get(0);
        Message messageFromRepository = repository.getAll().iterator().next();
        assertEquals(messageFromRepository.getId(), messageIdFromEventPublisher);
    }

    private Outbox createOutbox(boolean isTransactionActive) {
        return new Outbox(
                repository,
                Collections.singleton(new StubMessageSerializer()),
                () -> isTransactionActive,
                internalEventPublisher
        );
    }

    private SendMessageCommand givenIsCommand() {
        return StubSendMessageCommand
                .builder()
                .content(CONTENT)
                .metadata(METADATA)
                .build();
    }
}

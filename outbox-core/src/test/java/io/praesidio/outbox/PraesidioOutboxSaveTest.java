package io.praesidio.outbox;

import io.praesidio.outbox.stubs.StubImplementationConstants;
import io.praesidio.outbox.stubs.StubPraesidioCommandRepository;
import io.praesidio.outbox.stubs.StubPraesidioMessageSerializer;
import io.praesidio.outbox.stubs.StubPraesidioSendMessageCommand;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PraesidioOutboxSaveTest {

    private final StubPraesidioCommandRepository praesidioMessageRepository = new StubPraesidioCommandRepository();
    private final PraesidioOutbox subject = new PraesidioOutbox(
            praesidioMessageRepository,
            Set.of(new StubPraesidioMessageSerializer())
    );

    @Test
    public void whenMessageIsSentViaOutboxThenItIsSavedInTheRepository() {
        // given
        String content = "test content";
        String metadata = "test metadata";
        PraesidioSendMessageCommand command = StubPraesidioSendMessageCommand
                .builder()
                .content(content)
                .metadata(metadata)
                .build();

        // when
        subject.send(command);

        // then
        assertEquals(1, praesidioMessageRepository.getAll().size());
        PraesidioMessage message = praesidioMessageRepository.getAll().iterator().next();
        assertNotNull(message.getId().getValue());
        assertEquals(StubImplementationConstants.PRAESIDIO_MESSAGE_TYPE, message.getType());
        assertEquals(metadata, message.getMetadata().getValue());
        assertEquals(content, message.getContent().getValue());
    }
}

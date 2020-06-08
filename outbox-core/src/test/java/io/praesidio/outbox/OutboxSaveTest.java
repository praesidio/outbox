package io.praesidio.outbox;

import io.praesidio.outbox.stubs.StubImplementationConstants;
import io.praesidio.outbox.stubs.StubMessageRepository;
import io.praesidio.outbox.stubs.StubMessageSerializer;
import io.praesidio.outbox.stubs.StubSendMessageCommand;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OutboxSaveTest {

    private final StubMessageRepository messageRepository = new StubMessageRepository();
    private final Outbox subject = new Outbox(
            messageRepository,
            Collections.singleton(new StubMessageSerializer())
    );

    @Test
    public void whenMessageIsSentViaOutboxThenItIsSavedInTheRepository() {
        // given
        String content = "test content";
        String metadata = "test metadata";
        SendMessageCommand command = StubSendMessageCommand
                .builder()
                .content(content)
                .metadata(metadata)
                .build();

        // when
        subject.send(command);

        // then
        assertEquals(1, messageRepository.getAll().size());
        Message message = messageRepository.getAll().iterator().next();
        assertNotNull(message.getId().getValue());
        assertEquals(StubImplementationConstants.MESSAGE_TYPE, message.getType());
        assertEquals(metadata, message.getMetadata().getValue());
        assertEquals(content, message.getContent().getValue());
    }
}

package io.praesidio.outbox.stubs;

import io.praesidio.outbox.PraesidioMessage;
import io.praesidio.outbox.PraesidioMessageSerializer;
import io.praesidio.outbox.PraesidioSendMessageCommand;
import io.praesidio.outbox.values.PraesidioMessageContent;
import io.praesidio.outbox.values.PraesidioMessageId;
import io.praesidio.outbox.values.PraesidioMessageMetadata;
import io.praesidio.outbox.values.PraesidioMessageType;

import java.util.UUID;

import static io.praesidio.outbox.stubs.StubImplementationConstants.PRAESIDIO_MESSAGE_TYPE;

public class StubPraesidioMessageSerializer implements PraesidioMessageSerializer {

    @Override
    public PraesidioMessage convert(PraesidioSendMessageCommand sendMessageCommand) {
        StubPraesidioSendMessageCommand command = (StubPraesidioSendMessageCommand) sendMessageCommand;
        return PraesidioMessage.builder()
                .id(PraesidioMessageId.of(UUID.randomUUID()))
                .metadata(PraesidioMessageMetadata.of(command.getMetadata()))
                .content(PraesidioMessageContent.of(command.getContent()))
                .type(getType())
                .build();
    }

    @Override
    public PraesidioMessageType getType() {
        return PRAESIDIO_MESSAGE_TYPE;
    }
}

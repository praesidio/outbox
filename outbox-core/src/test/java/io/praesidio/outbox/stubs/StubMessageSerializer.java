package io.praesidio.outbox.stubs;

import io.praesidio.outbox.Message;
import io.praesidio.outbox.MessageSerializer;
import io.praesidio.outbox.SendMessageCommand;
import io.praesidio.outbox.values.MessageContent;
import io.praesidio.outbox.values.MessageId;
import io.praesidio.outbox.values.MessageMetadata;
import io.praesidio.outbox.values.MessageType;

import java.util.UUID;

import static io.praesidio.outbox.stubs.StubImplementationConstants.MESSAGE_TYPE;

public class StubMessageSerializer implements MessageSerializer {

    @Override
    public Message convert(SendMessageCommand sendMessageCommand) {
        StubSendMessageCommand command = (StubSendMessageCommand) sendMessageCommand;
        return Message.builder()
                      .id(MessageId.of(UUID.randomUUID()))
                      .metadata(MessageMetadata.of(command.getMetadata()))
                      .content(MessageContent.of(command.getContent()))
                      .type(getType())
                      .build();
    }

    @Override
    public SendMessageCommand convert(Message message) {
        return StubSendMessageCommand
                .builder()
                .content(message.getContent().getValue())
                .metadata(message.getMetadata().getValue())
                .build();
    }

    @Override
    public MessageType getType() {
        return MESSAGE_TYPE;
    }
}

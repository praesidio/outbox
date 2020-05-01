package io.praesidio.outbox;

import io.praesidio.outbox.values.PraesidioMessageType;
import lombok.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PraesidioOutbox {

    private final PraesidioCommandRepository praesidioCommandRepository;
    private final Map<PraesidioMessageType, PraesidioMessageSerializer> serializers;

    public PraesidioOutbox(@NonNull PraesidioCommandRepository praesidioCommandRepository,
                           @NonNull Collection<PraesidioMessageSerializer> serializers) {
        this.praesidioCommandRepository = praesidioCommandRepository;
        this.serializers = serializers.stream().collect(Collectors.toMap(PraesidioMessageSerializer::getType, s -> s));
    }

    public void send(PraesidioSendMessageCommand command) {
        PraesidioMessageType type = command.getType();
        PraesidioMessage message = Optional.ofNullable(serializers.get(type))
                .map(s -> s.convert(command))
                .orElseThrow(() -> new CannotFindPraesidioMessageSerializer(type));
        // FIXME make sure this is always invoked inside a transaction
        praesidioCommandRepository.save(message);
    }
}

package io.praesidio.outbox;

import io.praesidio.outbox.values.PraesidioMessageId;

import java.util.List;
import java.util.Optional;

public interface PraesidioCommandRepository {

    void save(PraesidioMessage praesidioMessage);

    Optional<PraesidioMessage> findMessage(PraesidioMessageId id);

    List<PraesidioMessage> findMessagesToRelay();
}

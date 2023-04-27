package ru.itis.MyTube.dao;

import ru.itis.MyTube.model.Reaction;
import ru.itis.MyTube.model.Reactions;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ReactionRepository {
    void setReaction(Reaction reaction) throws RuntimeException;

    boolean isReactionExists(UUID videoUuid, String username) throws RuntimeException;

    void createReaction(Reaction reaction) throws RuntimeException;

    Optional<Byte> getReaction(UUID videoUuid, String username) throws RuntimeException;

    Map<String, Long> getVideoReactions(UUID videoUuid) throws RuntimeException;

    Reactions getReactions(UUID videoUuid, String username) throws RuntimeException;
}

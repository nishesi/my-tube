package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.dao.ReactionRepository;
import ru.itis.MyTube.model.Reaction;
import ru.itis.MyTube.model.Reactions;
import ru.itis.MyTube.dto.forms.ReactionForm;
import ru.itis.MyTube.services.ReactionService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;

    @Override
    public String updateReaction(ReactionForm form) throws ValidationException {
        UUID videoUuid = null;
        Byte reaction = null;
        String username = form.getUser() == null ? null : form.getUser().getUsername();

        Map<String, String> problems = new HashMap<>();

        try {
            videoUuid = UUID.fromString(form.getVideoUuid());
            reaction = Byte.parseByte(form.getReaction());
            if (reaction < -1 || reaction > 1) {
                problems.put("reaction", "invalid reaction");
            }
        } catch (NumberFormatException ex) {
            problems.put("reaction", "invalid reaction");
        } catch (IllegalArgumentException ex) {
            problems.put("videoUuid", "invalid video id");
        }
        if (!problems.isEmpty()) {
            throw new ValidationException(problems);
        }

        reactionRepository.setReaction(Reaction.builder()
                .videoUuid(videoUuid)
                .username(username)
                .reaction(reaction).build()
        );

        Optional<Byte> reaction1 = reactionRepository.getReaction(videoUuid, username);

        Byte reaction2 = reaction1.orElse((byte) 0);
        Map<String, Long> reactions = reactionRepository.getVideoReactions(videoUuid);


        return "{" +
                "\"reaction\": " + reaction2 +
                ",\"likes\": " + (reactions.get("likes") + ((reaction == 1) ? 1 : 0)) +
                ",\"dislikes\": " + (reactions.get("dislikes") + ((reaction == -1) ? 1 : 0)) +
                "}";
    }

    @Override
    public Reactions getReaction(ReactionForm form) throws ValidationException {
        Map<String, String> problems = new HashMap<>();
        String username = form.getUser() == null ? null : form.getUser().getUsername();
        UUID videoUuid = null;
        try {
            videoUuid = UUID.fromString(form.getVideoUuid());

        } catch (IllegalArgumentException | NullPointerException ex) {
            problems.put("videoUuid", "invalid video id");
        }
        if (!problems.isEmpty()) {
            throw new ValidationException(problems);
        }
        return reactionRepository.getReactions(videoUuid, username);
    }
}

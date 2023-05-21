package ru.itis.nishesi.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.dto.ViewDto;
import ru.itis.nishesi.MyTube.dto.rest.ReactionForm;
import ru.itis.nishesi.MyTube.entities.User;
import ru.itis.nishesi.MyTube.entities.Video;
import ru.itis.nishesi.MyTube.entities.View;
import ru.itis.nishesi.MyTube.enums.Reaction;
import ru.itis.nishesi.MyTube.exceptions.ContentNotFoundException;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.repositories.ViewRepository;
import ru.itis.nishesi.MyTube.services.ViewService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewRepository viewRepository;

    @Override
    public ViewDto createView(ReactionForm reactionForm, UserDto userDto) {
        Video video = Video.builder().uuid(UUID.fromString(reactionForm.getVideoId())).build();
        User user = User.builder().id(userDto.getId()).build();
        View view = new View(user, video, Reaction.valueOf(reactionForm.getReaction()));
        try {
            view = viewRepository.save(view);
            return ViewDto.builder().reaction(view.getReaction()).build();
        } catch (RuntimeException ex) {
            throw new ServiceException("Something go wrong, try again later.");
        }
    }

    @Override
    public ViewDto updateView(ReactionForm reactionForm, UserDto userDto) throws ServiceException {
        UUID videoId = UUID.fromString(reactionForm.getVideoId());
        Reaction reaction = Reaction.valueOf(reactionForm.getReaction());
        Optional<View> viewOptional = viewRepository.findById(videoId, userDto.getId());
        viewOptional.ifPresentOrElse(
                view -> {
                    view.setReaction(reaction);
                    viewRepository.save(view);
                }, () -> {
                    View view12 = new View(
                            User.builder().id(userDto.getId()).build(),
                            Video.builder().uuid(videoId).build(),
                            reaction
                    );
                    viewRepository.save(view12);
                });

        long views = viewRepository.countByVideoUuid(videoId);
        long likes = viewRepository.countByReaction(Reaction.LIKE);
        long dislikes = viewRepository.countByReaction(Reaction.DISLIKE);
        return new ViewDto(views, likes, dislikes, reaction);
    }

    @Override
    public ViewDto getView(UUID videoId, UserDto userDto) throws ServiceException {
        long views = viewRepository.countByVideoUuid(videoId);
        long likes = viewRepository.countByReaction(Reaction.LIKE);
        long dislikes = viewRepository.countByReaction(Reaction.DISLIKE);
        View view = viewRepository.findById(videoId, userDto.getId())
                .orElseThrow(() -> new ContentNotFoundException("View not found."));
        return new ViewDto(views, likes, dislikes, view.getReaction());
    }

    @Override
    public void deleteView(UUID videoId, UserDto userDto) {
        Video video = Video.builder().uuid(videoId).build();
        User user = User.builder().id(userDto.getId()).build();
        View view = View.builder().user(user).video(video).build();
        try {
            viewRepository.delete(view);
        } catch (RuntimeException ex) {
            throw new ServiceException("Something go wrong, try again later.");
        }
    }
}

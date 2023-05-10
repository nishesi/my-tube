package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.MyTube.dto.UserDto;
import ru.itis.MyTube.dto.ViewDto;
import ru.itis.MyTube.entities.User;
import ru.itis.MyTube.entities.Video;
import ru.itis.MyTube.entities.View;
import ru.itis.MyTube.enums.Reaction;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.repositories.ViewRepository;
import ru.itis.MyTube.services.ViewService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewRepository viewRepository;

    @Override
    public ViewDto updateView(UUID videoId, UserDto userDto, Reaction reaction) throws ServiceException {
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
        Optional<View> viewOptional = viewRepository.findById(videoId, userDto.getId());
        Optional<Reaction> reaction = viewOptional.map(View::getReaction);

        return new ViewDto(views, likes, dislikes, reaction.orElse(Reaction.NONE));
    }
}

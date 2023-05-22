package ru.itis.nishesi.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.dto.forms.comment.NewCommentForm;
import ru.itis.nishesi.MyTube.entities.Comment;
import ru.itis.nishesi.MyTube.entities.User;
import ru.itis.nishesi.MyTube.entities.Video;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.repositories.CommentRepository;
import ru.itis.nishesi.MyTube.repositories.UserRepository;
import ru.itis.nishesi.MyTube.repositories.VideoRepository;
import ru.itis.nishesi.MyTube.services.CommentService;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final Clock clock;

    @Override
    public void AddComment(NewCommentForm form, UserDto userDto) {
        UUID uuid = UUID.fromString(form.getVideoId());
        Video video = videoRepository.getReferenceById(uuid);
        User user = userRepository.getReferenceById(userDto.getId());
        ZonedDateTime addedDate = ZonedDateTime.now(clock);

        Comment comment = new Comment(user, video, addedDate, form.getText());

        try {
            commentRepository.save(comment);
        } catch (RuntimeException ex) {
            throw new ServiceException("something go wrong, please try again later.");
        }
    }
}

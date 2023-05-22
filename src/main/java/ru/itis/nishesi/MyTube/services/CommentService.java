package ru.itis.nishesi.MyTube.services;

import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.dto.forms.comment.NewCommentForm;

public interface CommentService {
    void AddComment(NewCommentForm form, UserDto userDto);
}

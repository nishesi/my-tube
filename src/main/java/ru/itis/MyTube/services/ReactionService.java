package ru.itis.MyTube.services;

import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.dto.Reactions;
import ru.itis.MyTube.dto.forms.ReactionForm;

public interface ReactionService {
    String updateReaction(ReactionForm form) throws ServiceException, ValidationException;
    Reactions getReaction(ReactionForm form) throws ServiceException, ValidationException;
}

package ru.itis.MyTube.model.services;

import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.model.dto.Reactions;
import ru.itis.MyTube.model.dto.forms.ReactionForm;

public interface ReactionService {
    String updateReaction(ReactionForm form) throws ServiceException, ValidationException;
    Reactions getReaction(ReactionForm form) throws ServiceException, ValidationException;
}

package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.storage.FileType;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.auxiliary.exceptions.ValidationException;
import ru.itis.MyTube.controllers.validators.ChannelCreateValidator;
import ru.itis.MyTube.dao.ChannelRepository;
import ru.itis.MyTube.dao.UserRepository;
import ru.itis.MyTube.model.Channel;
import ru.itis.MyTube.model.ChannelCover;
import ru.itis.MyTube.model.User;
import ru.itis.MyTube.dto.forms.ChannelForm;
import ru.itis.MyTube.services.ChannelService;
import ru.itis.MyTube.storage.Storage;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    private final Storage storage;

    private final UrlCreator urlCreator;
    private final ChannelCreateValidator channelCreateValidator;


    @Override
    public Channel getChannel(String formId) {
        long id;
        try {
            id = Long.parseLong(formId);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new ServiceException("Illegal channel id.");
        }

        Optional<Channel> channelOpt;
        try {
            channelOpt = channelRepository.get(id);
            channelOpt.ifPresent(channel -> channel.getChannelCover()
                    .setChannelImgUrl(
                            urlCreator.createResourceUrl(
                                    FileType.CHANNEL_ICON,
                                    channel.getId().toString()
                            )
                    )
            );

        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }
        return channelOpt.orElseThrow(() -> new ServiceException("Channel not found."));
    }

    @Override
    public Long create(ChannelForm form) throws ValidationException {
        channelCreateValidator.validate(form);

        Channel channel = Channel.builder()
                .channelCover(ChannelCover.builder()
                        .name(form.getName())
                        .build())
                .info(form.getInfo())
                .owner(form.getUser())
                .build();

        Optional<ChannelCover> channel1;
        User user = form.getUser();

        try {
            channelRepository.create(channel);


            channel1 = channelRepository.get(user.getEmail());
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }

        Long channelId = channel1.orElseThrow(() -> new ServiceException("Сan not create channel.")).getId();

        try {
            user.setChannelId(channelId);

            userRepository.update(user);

            storage.save(FileType.CHANNEL_ICON, channelId.toString(), form.getIconPart().getInputStream());
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
        return channelId;
    }
}

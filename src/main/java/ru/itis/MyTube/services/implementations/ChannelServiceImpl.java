package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.dao.ChannelRepository;
import ru.itis.MyTube.dao.UserRepository;
import ru.itis.MyTube.dto.forms.channel.NewChannelForm;
import ru.itis.MyTube.model.Channel;
import ru.itis.MyTube.model.ChannelCover;
import ru.itis.MyTube.model.User;
import ru.itis.MyTube.services.ChannelService;
import ru.itis.MyTube.storage.FileType;
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
    public Long create(NewChannelForm form, User user) {
        Channel channel = Channel.builder()
                .channelCover(ChannelCover.builder()
                        .name(form.getName())
                        .build())
                .info(form.getInfo())
                .owner(user)
                .build();

        try {
            channelRepository.create(channel);
            ChannelCover channel1 = channelRepository.get(user.getEmail())
                    .orElseThrow(() -> new ServiceException("Сan not create channel."));
            user.setChannelId(channel1.getId());
            userRepository.update(user);

            storage.save(FileType.CHANNEL_ICON, channel1.getId().toString(), form.getIconFile().getInputStream());
            return channel1.getId();

        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }
}

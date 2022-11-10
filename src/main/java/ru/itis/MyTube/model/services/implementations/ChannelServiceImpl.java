package ru.itis.MyTube.model.services.implementations;

import lombok.RequiredArgsConstructor;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.auxiliary.enums.FileType;
import ru.itis.MyTube.auxiliary.exceptions.ServiceException;
import ru.itis.MyTube.model.dao.interfaces.ChannelRepository;
import ru.itis.MyTube.model.dto.Channel;
import ru.itis.MyTube.model.dto.VideoCover;
import ru.itis.MyTube.model.services.ChannelService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UrlCreator urlCreator;


    @Override
    public Channel getChannel(String formId) {
        Long id = null;
        try {
            id = Long.parseLong(formId);
        } catch (NumberFormatException | NullPointerException ex) {
            throw new ServiceException("Illegal channel id.");
        }

        try {
            Optional<Channel> channelOpt = channelRepository.get(id);

            channelOpt.ifPresent(channel -> channel.getChannelCover()
                    .setChannelImgUrl(
                            urlCreator.createResourceUrl(
                                    FileType.CHANNEL_ICON,
                                    channel.getId().toString()
                            )
                    )
            );

            return channelOpt.orElseThrow(() -> new ServiceException("Channel not found."));
        } catch (RuntimeException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<VideoCover> getChannelVideoCovers(Long channelId) {
        return null;
    }
}

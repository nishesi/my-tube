package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.itis.MyTube.auxiliary.UrlCreator;
import ru.itis.MyTube.dao.ChannelRepository;
import ru.itis.MyTube.dao.UserRepository;
import ru.itis.MyTube.dto.ChannelDto;
import ru.itis.MyTube.dto.Converter;
import ru.itis.MyTube.dto.forms.channel.NewChannelForm;
import ru.itis.MyTube.entities.Video;
import ru.itis.MyTube.exceptions.NotFoundException;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.model.Channel;
import ru.itis.MyTube.model.ChannelCover;
import ru.itis.MyTube.model.UserDto;
import ru.itis.MyTube.repositories.SubscriptionRepository;
import ru.itis.MyTube.repositories.VideoRepository;
import ru.itis.MyTube.services.ChannelService;
import ru.itis.MyTube.storage.FileType;
import ru.itis.MyTube.storage.Storage;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    @Value("${spring.data.web.pageable.default-page-size}")
    private int pageSize;

    private final ChannelRepository channelRepository;
    private final ru.itis.MyTube.repositories.ChannelRepository channelRep;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final Converter converter;

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
    public ChannelDto getChannel(long id, int pageIndex) {
        ru.itis.MyTube.entities.Channel channel = channelRep.findById(id)
                .orElseThrow(() -> new NotFoundException("Channel not found."));

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("addedDate").descending());
        Page<Video> page = videoRepository.getByChannelId(id, pageable);
        return converter.from(channel, page);
    }

    @Override
    public ChannelDto getChannelRegardingUser(long id, int pageNum, UserDto user) {
        ChannelDto channelDto = this.getChannel(id, pageNum);
        channelDto.setSubscribed(subscriptionRepository.existsByUserIdAndChannelId(user.getId(), id));
        return channelDto;
    }

    @Override
    public Long create(NewChannelForm form, UserDto userDto) {
        Channel channel = Channel.builder()
                .channelCover(ChannelCover.builder()
                        .name(form.getName())
                        .build())
                .info(form.getInfo())
                .owner(userDto)
                .build();

        try {
            channelRepository.create(channel);
            ChannelCover channel1 = channelRepository.get(userDto.getEmail())
                    .orElseThrow(() -> new ServiceException("Сan not create channel."));
            userDto.setChannelId(channel1.getId());
            userRepository.update(userDto);

            storage.save(FileType.CHANNEL_ICON, channel1.getId().toString(), form.getIconFile().getInputStream());
            return channel1.getId();

        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }
}

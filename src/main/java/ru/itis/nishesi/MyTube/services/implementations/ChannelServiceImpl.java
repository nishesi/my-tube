package ru.itis.nishesi.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.nishesi.MyTube.dto.ChannelDto;
import ru.itis.nishesi.MyTube.auxiliary.Converter;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.dto.forms.channel.NewChannelForm;
import ru.itis.nishesi.MyTube.entities.Channel;
import ru.itis.nishesi.MyTube.entities.User;
import ru.itis.nishesi.MyTube.entities.Video;
import ru.itis.nishesi.MyTube.exceptions.ContentNotFoundException;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.repositories.ChannelRepository;
import ru.itis.nishesi.MyTube.repositories.SubscriptionRepository;
import ru.itis.nishesi.MyTube.repositories.UserRepository;
import ru.itis.nishesi.MyTube.repositories.VideoRepository;
import ru.itis.nishesi.MyTube.services.ChannelService;
import ru.itis.nishesi.MyTube.enums.FileType;
import ru.itis.nishesi.MyTube.services.FileService;
import ru.itis.nishesi.MyTube.services.ViewService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ViewService viewService;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final Converter converter;
    private final FileService fileService;
    @Value("${spring.data.web.pageable.default-page-size}")
    private int pageSize;

    @Override
    public ChannelDto getChannel(long id, int pageIndex) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException("channel"));

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("addedDate").descending());
        Page<Video> page = videoRepository.getByChannelId(id, pageable);

        List<UUID> videoIds = page.getContent().stream().map(Video::getUuid).toList();
        List<Long> views = viewService.getViews(videoIds);

        return converter.from(channel, page, views);
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
                .name(form.getName())
                .info(form.getInfo())
                .build();
        try {
            User user = userRepository.findById(userDto.getId()).orElseThrow();
            channel = channelRepository.save(channel);
            user.setChannel(channel);
            userRepository.save(user);
            userDto.setChannelId(channel.getId());

            fileService.save(FileType.CHANNEL_ICON, String.valueOf(channel.getId()), form.getIconFile().getInputStream());
            return channel.getId();

        } catch (RuntimeException | IOException ex) {
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }
}

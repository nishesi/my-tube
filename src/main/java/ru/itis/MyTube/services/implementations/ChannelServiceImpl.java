package ru.itis.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.MyTube.dto.ChannelDto;
import ru.itis.MyTube.dto.Converter;
import ru.itis.MyTube.dto.UserDto;
import ru.itis.MyTube.dto.forms.channel.NewChannelForm;
import ru.itis.MyTube.entities.Channel;
import ru.itis.MyTube.entities.User;
import ru.itis.MyTube.entities.Video;
import ru.itis.MyTube.exceptions.NotFoundException;
import ru.itis.MyTube.exceptions.ServiceException;
import ru.itis.MyTube.repositories.ChannelRepository;
import ru.itis.MyTube.repositories.SubscriptionRepository;
import ru.itis.MyTube.repositories.UserRepository;
import ru.itis.MyTube.repositories.VideoRepository;
import ru.itis.MyTube.services.ChannelService;
import ru.itis.MyTube.enums.FileType;
import ru.itis.MyTube.services.FileService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

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
                .name(form.getName())
                .info(form.getInfo())
                .build();
        try {
            User user = userRepository.findById(userDto.getId())
                    .orElseThrow(() -> new NotFoundException("User not found."));
            channel = channelRepository.save(channel);
            user.setChannel(channel);
            userRepository.save(user);
            userDto.setChannelId(channel.getId());

            fileService.save(FileType.CHANNEL_ICON, String.valueOf(channel.getId()), form.getIconFile().getInputStream());
            return channel.getId();

        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }
}

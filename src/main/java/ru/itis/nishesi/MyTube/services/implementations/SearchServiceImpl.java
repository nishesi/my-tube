package ru.itis.nishesi.MyTube.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.nishesi.MyTube.enums.VideoCollectionType;
import ru.itis.nishesi.MyTube.dto.Converter;
import ru.itis.nishesi.MyTube.dto.VideoCover;
import ru.itis.nishesi.MyTube.entities.Video;
import ru.itis.nishesi.MyTube.exceptions.ServiceException;
import ru.itis.nishesi.MyTube.dto.UserDto;
import ru.itis.nishesi.MyTube.repositories.SubscriptionRepository;
import ru.itis.nishesi.MyTube.repositories.VideoRepository;
import ru.itis.nishesi.MyTube.services.SearchService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final SubscriptionRepository subscriptionRepository;
    private final VideoRepository videoRepository;
    private final Converter converter;
    private final Sort sort = Sort.by("addedDate").descending();
    
    @Value("${spring.data.web.pageable.default-page-size}")
    private int pageSize;

    @Override
    public Page<VideoCover> findVideosByNameSubstring(String substring, int pageInd) {
        Pageable pageable = PageRequest.of(pageInd, pageSize, sort);
        try {
            Page<Video> page = videoRepository.getByNameLikeIgnoreCase(substring, pageable);
            return converter.from(page);

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ServiceException("Something go wrong, please try again later.");
        }
    }

    @Override
    public Page<VideoCover> getVideoCollection(VideoCollectionType type, UserDto user, int pageInd) throws ServiceException {
        Pageable pageable = PageRequest.of(pageInd, pageSize, sort);

        Page<Video> page = switch (type) {
            case RANDOM -> videoRepository.findAll(pageable);
            case SUBSCRIPTIONS -> {
                if (user == null) throw new ServiceException("User not found.");
                Collection<Long> channelId = subscriptionRepository.findSubscriptionChannelIdByUserId(user.getId());
                yield videoRepository.getByChannelIdIn(channelId, pageable);
            }
        };
        return converter.from(page);
    }
}

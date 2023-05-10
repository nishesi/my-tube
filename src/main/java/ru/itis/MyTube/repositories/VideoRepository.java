package ru.itis.MyTube.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.itis.MyTube.entities.Video;

import java.util.Collection;
import java.util.UUID;

public interface VideoRepository extends
        CrudRepository<Video, UUID>, PagingAndSortingRepository<Video, UUID> {

    Page<Video> getByChannelId(Long channelId, Pageable pageable);
    Page<Video> getByChannelIdIn(Collection<Long> channelId, Pageable pageable);
}

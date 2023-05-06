package ru.itis.MyTube.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.itis.MyTube.entities.Video;

public interface VideoRepository extends
        CrudRepository<Video, Long>, PagingAndSortingRepository<Video, Long> {

    Page<Video> getByChannelId(Long channelId, Pageable pageable);
}

package ru.itis.nishesi.MyTube.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.itis.nishesi.MyTube.entities.Video;
import ru.itis.nishesi.MyTube.enums.AgeCategory;
import ru.itis.nishesi.MyTube.repositories.common.ContentRepository;

import java.util.Collection;
import java.util.UUID;

public interface VideoRepository extends CrudRepository<Video, UUID>, PagingAndSortingRepository<Video, UUID>, ContentRepository<Video, UUID> {

    Page<Video> getByChannelId(Long channelId, Pageable pageable);

    Page<Video> getByChannelIdIn(Collection<Long> channelId, Pageable pageable);

    Page<Video> getByNameLikeIgnoreCase(String str, Pageable pageable);

    @Query("""
            select v
            from Video v
            where v.channel in (
                select s.channel
                from Subscription s
                where s.user.id = :userId)""")
    Page<Video> getSubscriptionsVideos(Long userId, Pageable pageable);

    @Query("""            
            select distinct v3.video
            from View v1,
                 View v2,
                 View v3
            where v1.reaction = 'LIKE'
              and v2.reaction = 'LIKE'
              and v3.reaction = 'LIKE'
              and v1.video = v2.video
              and v2.video <> v3.video
              and v2.user = v3.user
              and v1.user <> v3.user
              and v3.video not in (select v.video from View v where v.user.id = :userId)
              and v1.user.id = :userId
            """)
    Page<Video> getRecommendedVideos(Long userId, Pageable pageable);

    @Query(value = """
                select video
                from Video video inner join View view
                on video = view.video and view.reaction = 'LIKE'
                group by video
                order by count(view.reaction) desc
            """,
            countQuery = """
                    select count(v) from Video v
                    """)
    Page<Video> getPopularVideos(Pageable pageable);


    default Page<? extends Video> findByAgeCategory(AgeCategory ageCategory, Pageable pageable) {
        return findByAgeCategory(ageCategory, pageable, Video.class);
    }
}

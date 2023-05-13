package ru.itis.MyTube.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.itis.MyTube.entities.Channel;
import ru.itis.MyTube.enums.AgeCategory;

public interface ChannelRepository extends CrudRepository<Channel, Long>, ContentRepository<Channel, Long> {

    default Page<? extends Channel> findByAgeCategory(AgeCategory ageCategory, Pageable pageable) {
        return findByAgeCategory(ageCategory, pageable, Channel.class);
    }
}

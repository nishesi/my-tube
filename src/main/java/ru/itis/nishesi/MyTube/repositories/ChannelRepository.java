package ru.itis.nishesi.MyTube.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.itis.nishesi.MyTube.entities.Channel;
import ru.itis.nishesi.MyTube.enums.AgeCategory;
import ru.itis.nishesi.MyTube.repositories.common.ContentRepository;

public interface ChannelRepository extends CrudRepository<Channel, Long>, ContentRepository<Channel, Long> {

    default Page<? extends Channel> findByAgeCategory(AgeCategory ageCategory, Pageable pageable) {
        return findByAgeCategory(ageCategory, pageable, Channel.class);
    }
}

package ru.itis.MyTube.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import ru.itis.MyTube.entities.Channel;

public interface ChannelRepository extends CrudRepository<Channel, Long> {
}
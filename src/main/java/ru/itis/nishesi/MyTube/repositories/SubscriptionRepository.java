package ru.itis.nishesi.MyTube.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.itis.nishesi.MyTube.entities.Channel;
import ru.itis.nishesi.MyTube.entities.Subscription;
import ru.itis.nishesi.MyTube.entities.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<Subscription, Subscription.SubscriptionId> {
    default Optional<Subscription> findById(Long userId, Long channelId) {
        return findById(new Subscription.SubscriptionId(
                User.builder().id(userId).build(),
                Channel.builder().id(channelId).build()
        ));
    }

    boolean existsByUserIdAndChannelId(Long userId, Long channelId);


    @Query("SELECT s.channel.id FROM Subscription s WHERE s.user.id = :id")
    List<Long> findSubscriptionChannelIdByUserId(Long id);
}

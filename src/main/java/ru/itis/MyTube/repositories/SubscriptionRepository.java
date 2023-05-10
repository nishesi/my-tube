package ru.itis.MyTube.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.itis.MyTube.entities.Channel;
import ru.itis.MyTube.entities.Subscription;
import ru.itis.MyTube.entities.User;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<Subscription, Subscription.SubscriptionId> {
    default Optional<Subscription> findById(Long userId, Long channelId) {
        return findById(new Subscription.SubscriptionId(
                User.builder().id(userId).build(),
                Channel.builder().id(channelId).build()
        ));
    }
    boolean existsByUserEmailAndChannelId(String userEmail, Long channelId);
    boolean existsByUserIdAndChannelId(Long userId, Long channelId);


    @Query("SELECT s.channel.id FROM Subscription s WHERE s.user.id = :id")
    List<Long> findSubscriptionChannelIdByUserId(Long id);
}

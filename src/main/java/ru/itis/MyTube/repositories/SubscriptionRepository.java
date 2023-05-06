package ru.itis.MyTube.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itis.MyTube.entities.Channel;
import ru.itis.MyTube.entities.Subscription;
import ru.itis.MyTube.entities.User;

import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<Subscription, Subscription.SubscriptionId> {
    default Optional<Subscription> findById(User user, Channel channel) {
        return findById(new Subscription.SubscriptionId(user, channel));
    }
    boolean existsByUserEmailAndChannelId(String userEmail, Long channelId);
}

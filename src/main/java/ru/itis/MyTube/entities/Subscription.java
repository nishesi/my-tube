package ru.itis.MyTube.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscriptions")
@IdClass(Subscription.SubscriptionId.class)
public class Subscription {

    @Id
    @ManyToOne
    @JoinColumn
    private User user;

    @Id
    @ManyToOne
    @JoinColumn
    private Channel channel;

    @Builder
    public static class SubscriptionId implements Serializable {
        private User user;
        private Channel channel;
    }
}

package ru.itis.nishesi.MyTube.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VideoCollectionType {
    RANDOM("Random", false),
    SUBSCRIPTIONS("Subscriptions", true);

    private final String name;
    private final boolean authRequired;
}

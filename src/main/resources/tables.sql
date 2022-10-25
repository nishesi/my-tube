create table users
(
    user_id    bigserial primary key,
    password   char(50) not null,
    first_name varchar(15),
    last_name  varchar(15),
    birthdate  date,
    country    varchar(20)
);

create table video_covers
(
    video_cover_id   bigserial primary key,
    video_name       varchar(70) not null,
    icon             bytea,
    added_date       timestamp with time zone,
    channel_cover_id bigint references channel_covers (channel_cover_id),
    video_id         bigint references videos (video_id),
    duration         time        not null
);

create table videos
(
    video_id bigint primary key,
    source   varchar not null,
    info     varchar(1000),
    likes    bigint,
    dislikes bigint,
    views    bigint
);

create table channel_covers
(
    channel_cover_id bigserial primary key,
    channel_name     varchar(20) not null,
    channel_icon     bytea,
    channel_id       bigint references channels (channel_id)
);

create table channels
(
    channel_id   bigserial primary key,
    owner_id     bigint references users (user_id),
    subs_count   bigint,
    channel_info varchar(1000),
    videos_json  json not null
)

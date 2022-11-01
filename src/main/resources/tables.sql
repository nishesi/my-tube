drop table if exists users cascade;
drop table if exists videos_covers cascade;
drop table if exists videos;
drop table if exists channels_covers cascade;
drop table if exists channels cascade;
drop table if exists users_subscriptions;
drop table if exists channels_videos;



create table users
(
    username   varchar primary key,
    password   char(50)    not null,
    first_name varchar(15) not null,
    last_name  varchar(15) not null,
    birthdate  date        not null,
    country    varchar(20) not null
);

create table videos
(
--  video_id matches with video file name
--  and video_cover_uuid
    video_uuid uuid primary key,
    info       varchar(1000) default ''
--    TODO
--     likes    bigint        default 0,
--     dislikes bigint        default 0,
);

create table channels
(
--  channel_id matches with channel_cover_id
    channel_id     bigserial primary key,
    owner_username varchar references users (username),
    subs_count     bigint        default 0,
    channel_info   varchar(1000) default ''
);

create table channels_covers
(
--  channel_cover_id matches with channel_id
--  and channel icon file name
    channel_cover_id bigserial primary key,
    channel_name     varchar(20) not null
);

create table videos_covers
(

--  video_cover_uuid matches with video uuid, video file name and video icon name
    video_cover_uuid uuid primary key,
    video_name       varchar(70)                                          not null,
    added_date       timestamp with time zone                             not null,
    channel_cover_id bigint references channels_covers (channel_cover_id) not null,
    duration         time                                                 not null,
    views            bigint default 0
);

create table users_subscriptions
(
    username   varchar references users (username)                  not null,
    channel_id bigint references channels_covers (channel_cover_id) not null
);

create table channels_videos
(
    channel_id       bigint references channels (channel_id)          not null,
    video_cover_uuid uuid references videos_covers (video_cover_uuid) not null
)

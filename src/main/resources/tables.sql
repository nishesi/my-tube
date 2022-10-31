drop table if exists users;
drop table if exists video_covers;
drop table if exists videos;
drop table if exists channel_covers;
drop table if exists channels;



create table users
(
    user_id    bigserial primary key,
    password   char(50)    not null,
    first_name varchar(15) not null,
    last_name  varchar(15) not null,
    birthdate  date        not null,
    country    varchar(20) not null
);

create table videos
(
--  video_id is the name of the file
--  that store video content in the storage
    video_id uuid primary key,
    info     varchar(1000) default ''
--    TODO
--     likes    bigint        default 0,
--     dislikes bigint        default 0,
--     views    bigint        default 0
);

create table channels
(
    channel_id   bigserial primary key,
    owner_id     bigint references users (user_id),
    subs_count   bigint        default 0,
    channel_info varchar(1000) default '',
    videos_json  json not null
);

create table channel_covers
(
    channel_cover_id bigserial primary key,
    channel_name     varchar(20) not null,
--  channel_icon - name of the file that store image in storage
    channel_icon     uuid        not null,
    channel_id       bigint references channels (channel_id)
);

create table video_covers
(
    video_cover_id   bigserial primary key,
    video_name       varchar(70)                                         not null,
--  video_icon - name of the file that store image in storage
    video_icon       uuid                                                not null,
    added_date       timestamp with time zone                            not null,
    channel_cover_id bigint references channel_covers (channel_cover_id) not null,
    video_id         uuid references videos (video_id),
    duration         time                                                not null
);

create table user_subscriptions
(
    user_id    bigint references users (user_id)                   not null,
    channel_id bigint references channel_covers (channel_cover_id) not null
);

create table channel_videos
(
    channel_id     bigint references channels (channel_id)         not null,
    video_cover_id bigint references video_covers (video_cover_id) not null
)

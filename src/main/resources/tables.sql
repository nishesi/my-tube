-- SCRIPTS

drop view if exists video_covers;
drop view if exists channel_covers;

drop materialized view if exists channels_inf;
drop materialized view if exists videos_inf;

drop table if exists users_subscriptions;
drop table if exists channels_videos;
drop table if exists viewing;
drop table if exists videos;
drop table if exists channels;
drop table if exists users;

refresh materialized view channels_inf;
refresh materialized view videos_inf;

-- ENTITIES

create table users
(
    username   varchar(20) primary key,
    password   char(50)    not null,
    first_name varchar(15) not null,
    last_name  varchar(15) not null,
    birthdate  date        not null,
    country    varchar(20) not null
);

create table channels
(
--  channel_id matches with channel icon file name
    id             bigserial primary key,
    name           varchar(20) not null,
    owner_username varchar references users (username),
    info           varchar(1000) default ''
);

create table videos
(

--  uuid matches with video file name and video icon name
    uuid       uuid primary key,
    name       varchar(70)                     not null,
    added_date timestamp with time zone        not null,
    channel_id bigint references channels (id) not null,
    duration   time                            not null,
    info       varchar(2000) default ''
);

-- RELATIONS

create table users_subscriptions
(
    username   varchar references users (username) not null,
    channel_id bigint references channels (id)     not null
);

create table channels_videos
(
    video_uuid uuid references videos (uuid)   not null,
    channel_id bigint references channels (id) not null
);

create table viewing
(
    user_id    varchar references users (username),
    video_uuid uuid references videos (uuid),
--      true - like, false - dislike, null - no reaction
    type       boolean
);

-- MATERIALIZED VIEWS

-- CHANNEL INF AND SUBSCRIBERS COUNT

create materialized view channels_inf as
select ch.*, count(us.username) as subs_count
from users_subscriptions us
         inner join channels ch on us.channel_id = ch.id
group by ch.id;

-- VIDEO INFORMATION WITH VIEWS, LIKES AND DISLIKES

create materialized view videos_inf as
SELECT v.uuid,
       v.name as v_name,
       v.added_date,
       channel_id as ch_id,
       ch.ch_name,
       duration,
       info,
       views,
       likes,
       dislikes
FROM (select vd.*,
             count(*) - 1                                     as views,
             sum(case when vw.type = true then 1 else 0 end)  as likes,
             sum(case when vw.type = false then 1 else 0 end) as dislikes
      from videos vd
               left join viewing vw on vw.video_uuid = vd.uuid
      group by vd.uuid) v
         CROSS JOIN LATERAL ( SELECT c.id   AS ch_id,
                                     c.name AS ch_name
                              FROM channels c
                              WHERE v.channel_id = c.id) ch;


-- VIEWS

create view channel_covers(id, name) as
SELECT channels.id,
       channels.name
FROM channels;

create view video_covers as
select uuid, v_name, ch_id, ch_name, duration, views, added_date
from videos_inf;



insert into users (username, password, first_name, last_name, birthdate, country)
values ('NishEsI', '48690                                             ', 'Nurislam', 'Zaripov', '2003-03-22', 'Russia'),
       ('MEDICHI', '48690                                             ', 'Saydash', 'Gilyazov', '2003-09-27', 'Russia'),
       ('KishMishKuraga', '48690                                             ', 'Farit', 'Ibragimov', '2003-07-25',
        'Russia');

insert into channels_covers (channel_cover_id, channel_name)
values (1, 'channel number one'),
       (2, 'channel number two'),
       (3, 'channel number three');

insert into channels (channel_id, owner_username, subs_count, channel_info)
values (1, 'NishEsI', 10100, 'BEST OF THE BEST'),
       (2, 'MEDICHI', 20200, 'SECOND OF THE SECOND'),
       (3, 'KishMishKuraga', 30300, 'THIRD OF THE THIRD');

insert into videos_covers (video_cover_uuid, video_name, added_date, channel_cover_id, duration, views)
values ('d1ee017c-e45c-43c3-ad09-299509a504a3', 'video from fisrt channel', '2022-11-2', 1, '00:11:10', 100),
       ('01eca157-f7d7-43d8-9228-587804d6434e', 'video from second channel', '2022-11-2', 2, '00:12:10', 200),
       ('5483d754-540c-4acf-99cb-cc32653f256d', 'video from third channel', '2022-11-2', 3, '00:13:10', 300);

insert into videos (video_uuid, info)
values ('d1ee017c-e45c-43c3-ad09-299509a504a3', 'best video from channel one'),
       ('01eca157-f7d7-43d8-9228-587804d6434e', 'norm video from channel two'),
       ('5483d754-540c-4acf-99cb-cc32653f256d', 'worst video from channel three');

insert into users_subscriptions (username, channel_id)
values ('NishEsI', 2),
       ('NishEsI', 3),
       ('MEDICHI', 1),
       ('MEDICHI', 3),
       ('KishMishKuraga', 1),
       ('KishMishKuraga', 2);

insert into channels_videos (channel_id, video_cover_uuid)
values (1, 'd1ee017c-e45c-43c3-ad09-299509a504a3'),
       (2, '01eca157-f7d7-43d8-9228-587804d6434e'),
       (3, '5483d754-540c-4acf-99cb-cc32653f256d');

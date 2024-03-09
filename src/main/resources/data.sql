insert into authority (authority_name) values ('ROLE_USER');
insert into authority (authority_name) values ('ROLE_ADMIN');


INSERT INTO user_authority (user_id, authority_name) VALUES (35, 'ROLE_ADMIN');

insert into user_authority (user_id, authority_name) values (1, 'ROLE_USER');
insert into user_authority (user_id, authority_name) values (1, 'ROLE_ADMIN');
insert into user_authority (user_id, authority_name) values (2, 'ROLE_USER');

INSERT INTO SHORT_FORMS (short_form_id, IMG_URL, TITLE, CONTENT, CREATE_DATE_TIME, UPDATE_DATETIME, LIKES_COUNT, COMMENT_COUNTS)
VALUES (1, 'http://example.com/image.jpg', '제목', '내용입니다.', '2024-02-28 12:00:00', '2024-02-28 12:00:00', 0, 0);

INSERT INTO communities (community_id, title, img_url, content, create_date_time, update_date_time, nickname, category) VALUES (1,'제목','http://example.com/image.jpg','내용입니다.','2024-02-28 12:00:00','2024-02-28 12:00:00','닉네임','코알랑');
insert into authority (authority_name) values ('ROLE_USER');
insert into authority (authority_name) values ('ROLE_ADMIN');

insert into user_authority (user_id, authority_name) values (1, 'ROLE_ADMIN');

insert into COMM(CMT_COUNT, LIKES_COUNT, USER_ID, CATEGORY, CONTENT, TITLE) values (0, 0, 1, '자유게시판', 'ㅈㄱㄴ', '동묘앞 구제 시장 가본 후기');

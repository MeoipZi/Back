insert into authority (authority_name) values ('ROLE_USER');
insert into authority (authority_name) values ('ROLE_ADMIN');

insert into user_authority (user_id, authority_name) values (1, 'ROLE_ADMIN');

insert into COMM(CMT_COUNT, LIKES_COUNT, USER_ID, CATEGORY, CONTENT, TITLE) values (0, 0, 1, '자유게시판', 'ㅈㄱㄴ', '동묘앞 구제 시장 가본 후기');
insert into CMTCOMM(COMMUNITY_ID, USER_ID, CONTENTS) values (1, 1, '동묘앞 구제 시장 가본 후기');
insert into CMTCOMM(COMMUNITY_ID, PARENT_CMT_ID, USER_ID, CONTENTS) values (1, 1, 1, '동묘앞 구제 시장 가본 후기');

insert into OUTFIT(LIKES_COUNT, MODEL_HEIGHT, MODEL_WEIGHT, CONTENT, MODEL_GENDER) values (0, 172, 54, '최신유행 스타일 어쩌고', 'W');

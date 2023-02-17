-- <Member> --
insert into member (id, created_time, modified_time, account, birthday, email, login_type, mobile, name, nickname,
                    password, medium_file_path, original_file_path, small_file_path, social_id, pay_point, certified)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'admin@naver.com', NULL, 'admin@naver.com',
        'BASIC', NULL, NULL, '조대호', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0, false),
       (2, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student1@naver.com', NULL, 'student1@naver.com',
        'BASIC', NULL, NULL, '철수', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0, false),
       (3, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student2@naver.com', NULL, 'student2@naver.com',
        'BASIC', NULL, NULL, '민수', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0, false),
       (4, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student3@naver.com', NULL, 'student3@naver.com',
        'BASIC', NULL, NULL, '재영', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0, false),
       (5, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student4@naver.com', NULL, 'student4@naver.com',
        'BASIC', NULL, NULL, '민서', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0, false),
       (6, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student5@naver.com', NULL, 'student5@naver.com',
        'BASIC', NULL, NULL, '민지', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0, false);

insert into member_roles (member_id, roles)
values (1, 'ROLE_USER'),
       (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_USER'),
       (4, 'ROLE_USER'),
       (5, 'ROLE_USER'),
       (6, 'ROLE_USER');


-- <CreatorProfile> --
insert into creator_profile (id, member_id, activated)
values ('1', '1', true);

-- <Event> --
insert into event (id, created_time, modified_time, medium_file_path, original_file_path, small_file_path, content,
                   end_date, start_date, title, writer_id)
values ('1', '2023-02-08 14:18:13.411223', '2023-02-08 14:18:13.411223', '/static/images/open_event_thumbnail_m.jpg',
        '/static/images/open_event_thumbnail.jpg', '/static/images/open_event_thumbnail_s.jpg',
        '다봄 오픈 기념으로 선착순 2000명에게 강좌 할인 쿠폰을 드려요 !', '2024-02-08',
        '2023-02-08', '다봄 오픈 이벤트 !', '1');

-- <Course> --
insert into course (id, created_time, modified_time, category, description, medium_file_path, original_file_path,
                    small_file_path, title, creator_profile_id, active)
values ('2', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'BACK_END',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '/static/images/jo_oa_pil_thumbnail_m.jpg',
        '/static/images/jo_oa_pil_thumbnail.jpg', '/static/images/jo_oa_pil_thumbnail_s.jpg', '사무자동화산업기사 필기', '1',
        true),
       ('3', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'BACK_END',
        '<오피스 최신강의, 최신출제기준반영,스마트폰수강,무료연장서비스>', '/static/images/os_sil_thumbnail_m.jpg',
        '/static/images/os_sil_thumbnail.jpg', '/static/images/os_sil_thumbnail_s.jpg', '사무자동화산업기사 실기', '1', true),
       ('4', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'BACK_END',
        '컴퓨터그래픽스운용기능사 자격증 취득희망자, 웹디자이너로 취업 희망자', '/static/images/graphics_thumbnail_m.jpg',
        '/static/images/graphics_thumbnail.jpg', '/static/images/graphics_thumbnail_s.jpg', '컴퓨터그래픽스운용기능사 실기', '1', true);

insert into description_image (description_image_id, medium_file_path, original_file_path, small_file_path)
values ('2', '/static/images/jo_oa_pil_description_m.jpg', '/static/images/jo_oa_pil_description.jpg',
        '/static/images/jo_oa_pil_description_s.jpg'),
       ('3', '/static/images/os_sil_description1_m.jpg', '/static/images/os_sil_description1.jpg',
        '/static/images/os_sil_description1_s.jpg'),
       ('3', '/static/images/os_sil_description2_m.jpg', '/static/images/os_sil_description2.jpg',
        '/static/images/os_sil_description2_s.jpg'),
       ('4', '/static/images/graphics_description_m.jpg', '/static/images/graphics_description.jpg',
        '/static/images/graphics_description_s.jpg');


-- <Item> --
insert into item (dtype, id, created_time, modified_time, cost_price, discounted_price, product_detail, product_name,
                  medium_file_path, original_file_path, small_file_path, item_type)
values ('ticket', '4', '2023-02-13 11:01:49.369901', '2023-02-13 11:01:49.369901', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 필기 3개월 수강권',
        '/static/images/jo_oa_pil_thumbnail_m.jpg',
        '/static/images/jo_oa_pil_thumbnail.jpg', '/static/images/jo_oa_pil_thumbnail_s.jpg', 'TICKET'),
       ('ticket', '5', '2023-02-13 11:01:49.371984', '2023-02-13 11:01:49.371984', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 필기 6개월 수강권',
        '/static/images/jo_oa_pil_thumbnail_m.jpg',
        '/static/images/jo_oa_pil_thumbnail.jpg', '/static/images/jo_oa_pil_thumbnail_s.jpg', 'TICKET'),
       ('ticket', '6', '2023-02-13 11:01:49.372482', '2023-02-13 11:01:49.372482', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 필기 영구 수강권',
        '/static/images/jo_oa_pil_thumbnail_m.jpg',
        '/static/images/jo_oa_pil_thumbnail.jpg', '/static/images/jo_oa_pil_thumbnail_s.jpg', 'TICKET'),
       ('ticket', '7', '2023-02-13 11:01:49.369901', '2023-02-13 11:01:49.369901', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 실기 3개월 수강권',
        '/static/images/os_sil_thumbnail_m.jpg',
        '/static/images/os_sil_thumbnail.jpg', '/static/images/os_sil_thumbnail_s.jpg', 'TICKET'),
       ('ticket', '8', '2023-02-13 11:01:49.371984', '2023-02-13 11:01:49.371984', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 실기 6개월 수강권',
        '/static/images/os_sil_thumbnail_m.jpg',
        '/static/images/os_sil_thumbnail.jpg', '/static/images/os_sil_thumbnail_s.jpg', 'TICKET'),
       ('ticket', '9', '2023-02-13 11:01:49.372482', '2023-02-13 11:01:49.372482', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 실기 영구 수강권',
        '/static/images/os_sil_thumbnail_m.jpg',
        '/static/images/os_sil_thumbnail.jpg', '/static/images/os_sil_thumbnail_s.jpg', 'TICKET'),
       ('ticket', '10', '2023-02-13 11:01:49.372482', '2023-02-13 11:01:49.372482', '0', '0',
        '컴퓨터그래픽스운용기능사 자격증 취득희망자, 웹디자이너로 취업 희망자', '컴퓨터그래픽스운용기능사 영구 수강권',
        '/static/images/graphics_thumbnail_m.jpg',
        '/static/images/graphics_thumbnail.jpg', '/static/images/graphics_thumbnail_s.jpg', 'TICKET');

-- <Coupon> --
insert into coupon (id, discount, discount_policy, end_date, minimum_amount, name)
values ('1', '10', 'RATE', '2024-02-13', '100000', '신규 회원 환영 쿠폰');

-- <Coupon Code> --
insert into coupon_code (coupon_id, code)
values ('1', 'abcdabcdabcdabcd');

-- <Issue> --
insert into issue (id, coupon_id, member_id, used)
values ('1', '1', '1', false),
       ('2', '1', '2', false),
       ('3', '1', '3', false),
       ('4', '1', '4', false),
       ('5', '1', '5', false),
       ('6', '1', '6', false);


-- <Ticket> --
insert into ticket (course_period, id, course_id, charge_type)
values ('THREE_MONTH', '4', '2', 'PAID'),
       ('SIX_MONTH', '5', '2', 'PAID'),
       ('UNLIMITED', '6', '2', 'PAID'),
       ('THREE_MONTH', '7', '3', 'PAID'),
       ('SIX_MONTH', '8', '3', 'PAID'),
       ('UNLIMITED', '9', '3', 'PAID'),
       ('UNLIMITED', '10', '4', 'FREE');

-- <Enroll> --
insert into enroll (id, created_time, modified_time, course_id, member_id, end_date)
values ('2', '2023-02-16 13:45:24.036603', '2023-02-14 13:45:24.036603', '2', '1', '2024-02-13 11:01:49.369901'),
       ('3', '2023-02-16 13:45:24.036603', '2023-02-14 13:45:24.036603', '3', '1', '2024-02-13 11:01:49.369901'),
       ('5', '2023-02-16 13:45:24.036603', '2023-02-14 13:45:24.036603', '4', '2', '2024-02-13 11:01:49.369901');

-- <Post> --
insert into post (dtype, id, created_time, modified_time, content, likes, course_id, member_id)
values ('review', '1', '2023-02-08 13:49:21.145507', '2023-02-08 13:49:21.145507', '정말 좋은 강의에요 !', '0', '2', '2'),
       ('review', '2', '2023-02-08 13:49:21.145507', '2023-02-08 13:49:21.145507', '유익해요', '0', '2', '3'),
       ('inquiry', '3', '2023-02-08 13:49:21.145507', '2023-02-08 13:49:21.145507', '강의 난이도가 어떻게 되나요?', '0', '2', '4'),
       ('review', '5', '2023-02-08 13:49:21.145507', '2023-02-08 13:49:21.145507', '정말 좋은 강의에요 !', '0', '3', '6'),
       ('review', '6', '2023-02-08 13:49:21.145507', '2023-02-08 13:49:21.145507', '유익해요', '0', '3', '3'),
       ('inquiry', '7', '2023-02-08 13:49:21.145507', '2023-02-08 13:49:21.145507', '강의 난이도가 어떻게 되나요?', '0', '3', '5');

insert into review (score, id)
values (5, 1),
       (4, 2),
       (5, 5),
       (4, 6);

insert into inquiry (id)
values (3),
       (7);

-- <Chapter> --
insert into chapter (id, course_id, sequence, title, is_default)
values ('12', '2', '1', '[공지] 대호쌤의 동영상 공지사항', false),
       ('13', '2', '2', '[제1과목] 사무자동화 시스템', false),
       ('14', '2', '3', '[제2과목] 사무경영관리 개론', false),
       ('15', '2', '4', '[제3과목] 프로그래밍 일반', false),
       ('16', '2', '5', '[제4과목] 정보통신 개론', false),

       ('17', '3', '1', '▶ [특강] 100점 합격을 위한 대호쌤의 세심한 특강', false),
       ('18', '3', '2', '▶ [본강] 단계별로 따라하는 사무자동화실기 시험방식', false),
       ('19', '4', '1', '컴퓨터그래픽스운용기능사', false);


-- <Unit> --
insert into unit (id, created_time, modified_time, description, sequence, title, file_path, chapter_id, course_id)
values ('64', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[OT] 합격을 할 수 밖에 없는 이유~!!	',
        '/static/videos/jo_oa_pil_ot_1-m3u8/jo_oa_pil_ot_1.m3u8', '12', '2'),
       ('65', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[1-1강] 사무자동화 개념	',
        '/static/videos/jo_oa_pil_1-1-m3u8/jo_oa_pil_1-1.m3u8', '13', '2'),
       ('66', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '2', '[1-2강] 사무자동화 기술	',
        '/static/videos/jo_oa_pil_1-2-m3u8/jo_oa_pil_1-2.m3u8', '13', '2'),
       ('67', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '[1-3강] 사무자동화 응용	',
        '/static/videos/jo_oa_pil_1-3-m3u8/jo_oa_pil_1-3.m3u8', '13', '2'),
       ('68', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '[1-4강] 통합 사무자동화	',
        '/static/videos/jo_oa_pil_1-4-m3u8/jo_oa_pil_1-4.m3u8', '13', '2'),
       ('69', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[2-1강] 사무정보 관리	',
        '/static/videos/jo_oa_pil_2-1-m3u8/jo_oa_pil_2-1.m3u8', '14', '2'),
       ('70', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[2-2강] 사무관리 표준화	',
        '/static/videos/jo_oa_pil_2-2-m3u8/jo_oa_pil_2-2.m3u8', '14', '2'),
       ('71', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '[2-3강] 자료관리 운용	',
        '/static/videos/jo_oa_pil_2-3-m3u8/jo_oa_pil_2-3.m3u8', '14', '2'),
       ('72', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '[2-4강] 사무작업 형태',
        '/static/videos/jo_oa_pil_2-4-m3u8/jo_oa_pil_2-4.m3u8', '14', '2'),
       ('73', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[3-1강] 프로그래밍 언어	',
        '/static/videos/jo_oa_pil_3-1-m3u8/jo_oa_pil_3-1.m3u8', '15', '2'),
       ('74', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '2', '[3-2강] 언어의 설계와 구현	',
        '/static/videos/jo_oa_pil_3-2-m3u8/jo_oa_pil_3-2.m3u8', '15', '2'),
       ('75', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '[3-3강] 자료형,
        C언어 ', '/static/videos/jo_oa_pil_3-3-m3u8/jo_oa_pil_3-3.m3u8', '15', '2'),
       ('76', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '[3-4강] C언어, 객체지향,
        순서제어 ', '/static/videos/jo_oa_pil_3-4-m3u8/jo_oa_pil_3-4.m3u8', '15', '2'),
       ('77', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '5', '[3-5강] 운영체제	',
        '/static/videos/jo_oa_pil_3-5-m3u8/jo_oa_pil_3-5.m3u8', '15', '2'),
       ('78', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[4-1강] 정보통신의 개념	',
        '/static/videos/jo_oa_pil_4-1-m3u8/jo_oa_pil_4-1.m3u8', '16', '2'),
       ('79', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '2', '[4-2강] 정보통신 기기	',
        '/static/videos/jo_oa_pil_4-2-m3u8/jo_oa_pil_4-2.m3u8', '16', '2'),
       ('80', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '[4-3강] 정보전송 기술	',
        '/static/videos/jo_oa_pil_4-3-m3u8/jo_oa_pil_4-3.m3u8', '16', '2'),
       ('81', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '[4-4강] 정보전송 방식과 프로토콜	',
        '/static/videos/jo_oa_pil_4-4-m3u8/jo_oa_pil_4-4.m3u8', '16', '2'),
       ('82', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '5', '[4-5강] 정보통신망	',
        '/static/videos/jo_oa_pil_4-5-m3u8/jo_oa_pil_4-5.m3u8', '16', '2'),
       ('83', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[대호쌤 공지] 시험기간 중 시험정보에 대한 팁',
        '/static/videos/oa_sil_notice_161109-m3u8/oa_sil_notice_161109.m3u8', '17', '3'),
       ('84', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '2', '[오리엔테이션] 100점으로 합격하는 대호쌤의 합격전략	',
        '/static/videos/oa_sil_special_lecture_ot_jdh-m3u8/oa_sil_special_lecture_ot_jdh.m3u8', '17', '3'),
       ('85', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '[인쇄작업특강] 시험장에서 일어나는 시뮬레이션',
        '/static/videos/oa_sil_special_lecture_print_jdh-m3u8/oa_sil_special_lecture_print_jdh.m3u8', '17', '3'),
       ('86', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '[엑셀함수특강] 시험에 자주나오는 함수특강',
        '/static/videos/oa_sil_special_lecture_function_jdh-m3u8/oa_sil_special_lecture_function_jdh.m3u8', '17', '3'),
       ('87', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '5', '[엑셀함수특강] 엑셀 달러($)사용법과 고급함수',
        '/static/videos/oa_sil_special_lecture_function_jdh2-m3u8/oa_sil_special_lecture_function_jdh2.m3u8', '17',
        '3'),
       ('88', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '6', '[엑셀함수특강] 문자열 함수 CONCATENATE',
        '/static/videos/oa_sil_special_lecture_function_concatenate-m3u8/oa_sil_special_lecture_function_concatenate.m3u8',
        '17', '3'),
       ('89', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '7', '[엑셀함수특강] SUMIFS, AVERAGEIFS,
        COUNTIFS', '/static/videos/oa_sil_special_lecture_function_ifs-m3u8/oa_sil_special_lecture_function_ifs.m3u8',
        '17', '3'),
       ('90', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '8', '[엑셀함수특강] Frequency 함수',
        '/static/videos/oa_sil_special_lecture_function_frequency-m3u8/oa_sil_special_lecture_function_frequency.m3u8',
        '17', '3'),
       ('91', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '9',
        '[엑셀함수특강] Sumproduct, Isnumber, Find',
        '/static/videos/oa_sil_special_lecture_function_sif-m3u8/oa_sil_special_lecture_function_sif.m3u8', '17', '3'),
       ('92', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[엑셀-1단계] 작업표형식에 맞춰 데이터 입력하기',
        '/static/videos/os_sil_excel_step_01-m3u8/os_sil_excel_step_01.m3u8', '18', '3'),
       ('93', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '2', '[엑셀-2단계] 항목계산과 정렬작업',
        '/static/videos/os_sil_excel_step_02-m3u8/os_sil_excel_step_02.m3u8', '18', '3'),
       ('94', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '[엑셀-3단계] 합계라인 계산과 서식작업',
        '/static/videos/os_sil_excel_step_03-m3u8/os_sil_excel_step_03.m3u8', '18', '3'),
       ('95', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '[엑셀-4단계] 그래프작업과 인쇄준비작업',
        '/static/videos/os_sil_excel_step_04-m3u8/os_sil_excel_step_04.m3u8', '18', '3'),
       ('96', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '5', '[액세스-1단계] 테이블 만들기',
        '/static/videos/os_sil_access_step_01-m3u8/os_sil_access_step_01.m3u8', '18', '3'),
       ('97', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '6', '[액세스-2단계] 쿼리 만들기',
        '/static/videos/os_sil_access_step_02-m3u8/os_sil_access_step_02.m3u8', '18', '3'),
       ('98', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '7', '[액세스-3단계] 폼 만들기, 인쇄작업',
        '/static/videos/os_sil_access_step_03-m3u8/os_sil_access_step_03.m3u8', '18', '3'),
       ('99', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '8', '[액세스-4단계] 보고서 만들기, 인쇄작업',
        '/static/videos/os_sil_access_step_04-m3u8/os_sil_access_step_04.m3u8', '18', '3'),
       ('100', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '9', '[파워포인트-1, 2단계] 기본설정과 제1슬라이드 만들기',
        '/static/videos/os_sil_ppt_step_01-m3u8/os_sil_ppt_step_01.m3u8', '18', '3'),
       ('101', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '10', '[파워포인트-3, 4단계] 제2슬라이드 만들고 인쇄설정하기',
        '/static/videos/os_sil_ppt_step_02-m3u8/os_sil_ppt_step_02.m3u8', '18', '3'),
       ('102', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '강의소개',
        '/static/videos/graphic_silgi_01-m3u8/graphic_silgi_01.m3u8', '19', '4'),
       ('103', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '2', '기본기능익히기',
        '/static/videos/graphic_silgi_02-m3u8/graphic_silgi_02.m3u8', '19', '4'),
       ('104', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '트랜스폼활용하기',
        '/static/videos/graphic_silgi_03-m3u8/graphic_silgi_03.m3u8', '19', '4'),
       ('105', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '패스파인더활용하기',
        '/static/videos/graphic_silgi_04-m3u8/graphic_silgi_04.m3u8', '19', '4'),
       ('106', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '5', '그래프그리기',
        '/static/videos/graphic_silgi_05-m3u8/graphic_silgi_05.m3u8', '19', '4'),
       ('107', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '6', '로고와문양만들기',
        '/static/videos/graphic_silgi_06-m3u8/graphic_silgi_06.m3u8', '19', '4'),
       ('108', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '7', '펜툴활용하기',
        '/static/videos/graphic_silgi_07-m3u8/graphic_silgi_07.m3u8', '19', '4'),
       ('109', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '8', '포토샵의기본기능',
        '/static/videos/graphic_silgi_08-m3u8/graphic_silgi_08.m3u8', '19', '4'),
       ('110', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '9', '선택툴이해하기',
        '/static/videos/graphic_silgi_09-m3u8/graphic_silgi_09.m3u8', '19', '4'),
       ('111', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '10', '펜툴활용하기',
        '/static/videos/graphic_silgi_10-m3u8/graphic_silgi_10.m3u8', '19', '4'),
       ('112', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '11', '이미지수정하기',
        '/static/videos/graphic_silgi_11-m3u8/graphic_silgi_11.m3u8', '19', '4'),
       ('113', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '12', '레이어활용하기',
        '/static/videos/graphic_silgi_12-m3u8/graphic_silgi_12.m3u8', '19', '4'),
       ('114', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '13', '필터이해하기',
        '/static/videos/graphic_silgi_13-m3u8/graphic_silgi_13.m3u8', '19', '4'),
       ('115', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '14', '페이지메이커기본기능',
        '/static/videos/graphic_silgi_14-m3u8/graphic_silgi_14.m3u8', '19', '4'),
       ('116', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '15', '페이지메이커핵심기능',
        '/static/videos/graphic_silgi_15-m3u8/graphic_silgi_15.m3u8', '19', '4'),
       ('117', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '16', '1회출제예상문제풀이',
        '/static/videos/graphic_silgi_16-m3u8/graphic_silgi_16.m3u8', '19', '4'),
       ('118', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '17', '1회출제예상문제풀이',
        '/static/videos/graphic_silgi_17-m3u8/graphic_silgi_17.m3u8', '19', '4'),
       ('119', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '18', '1회출제예상문제풀이',
        '/static/videos/graphic_silgi_18-m3u8/graphic_silgi_18.m3u8', '19', '4'),
       ('120', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '19', '1회출제예상문제풀이',
        '/static/videos/graphic_silgi_19-m3u8/graphic_silgi_19.m3u8', '19', '4'),
       ('121', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '20', '2회출제예상문제풀이',
        '/static/videos/graphic_silgi_20-m3u8/graphic_silgi_20.m3u8', '19', '4'),
       ('122', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '21', '2회출제예상문제풀이',
        '/static/videos/graphic_silgi_21-m3u8/graphic_silgi_21.m3u8', '19', '4'),
       ('123', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '22', '2회출제예상문제풀이',
        '/static/videos/graphic_silgi_22-m3u8/graphic_silgi_22.m3u8', '19', '4'),
       ('124', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '23', '2회출제예상문제풀이',
        '/static/videos/graphic_silgi_23-m3u8/graphic_silgi_23.m3u8', '19', '4'),
       ('125', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '24', '3회출제예상문제풀이',
        '/static/videos/graphic_silgi_24-m3u8/graphic_silgi_24.m3u8', '19', '4'),
       ('126', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '25', '3회출제예상문제풀이',
        '/static/videos/graphic_silgi_25-m3u8/graphic_silgi_25.m3u8', '19', '4'),
       ('127', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '26', '3회출제예상문제풀이',
        '/static/videos/graphic_silgi_26-m3u8/graphic_silgi_26.m3u8', '19', '4'),
       ('128', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '27', '3회출제예상문제풀이',
        '/static/videos/graphic_silgi_27-m3u8/graphic_silgi_27.m3u8', '19', '4'),
       ('129', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '28', '4회출제예상문제풀이',
        '/static/videos/graphic_silgi_28-m3u8/graphic_silgi_28.m3u8', '19', '4'),
       ('130', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '29', '4회출제예상문제풀이',
        '/static/videos/graphic_silgi_29-m3u8/graphic_silgi_29.m3u8', '19', '4'),
       ('131', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '30', '4회출제예상문제풀이',
        '/static/videos/graphic_silgi_30-m3u8/graphic_silgi_30.m3u8', '19', '4'),
       ('132', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '31', '4회출제예상문제풀이',
        '/static/videos/graphic_silgi_31-m3u8/graphic_silgi_31.m3u8', '19', '4'),
       ('133', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '32', '실기시험시 유의사항',
        '/static/videos/graphic_silgi_32-m3u8/graphic_silgi_32.m3u8', '19', '4');

-- <Question> --
insert into question (id, created_time, modified_time, content, title, member_id, unit_id)
values ('1', '2023-02-08 14:00:36.192330', '2023-02-08 14:00:36.192330', '다른 강의에는 수업자료에 있는데 여기는 없는 것 같아 글 남겨봅니다.',
        '이 강의는 소스코드 파일이 별도로 제공되지 않나요?', '2', '65');

-- <Answer> --
insert into answer (id, created_time, modified_time, content, member_id, question_id)
values ('1', '2023-02-08 14:01:01.810008', '2023-02-08 14:01:01.810008', '네, 본 강의는 별도의 소스코드가 제공되지 않습니다. 감사합니다.', '1',
        '1');

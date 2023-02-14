-- <Member> --
insert into member (id, created_time, modified_time, account, birth_date, email, login_type, mobile, name, nickname,
                    password, medium_file_path, original_file_path, small_file_path, social_id, pay_point)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'admin@naver.com', NULL, 'admin@naver.com',
        'BASIC', NULL, NULL, '조대호', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0),
       (2, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student1@naver.com', NULL, 'student1@naver.com',
        'BASIC', NULL, NULL, 'student1', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0),
       (3, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student2@naver.com', NULL, 'student2@naver.com',
        'BASIC', NULL, NULL, 'student2', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0),
       (4, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student3@naver.com', NULL, 'student3@naver.com',
        'BASIC', NULL, NULL, 'student3', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0),
       (5, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student4@naver.com', NULL, 'student4@naver.com',
        'BASIC', NULL, NULL, 'student4', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0),
       (6, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student5@naver.com', NULL, 'student5@naver.com',
        'BASIC', NULL, NULL, 'student5', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK',
        '/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg',
        '/static/images/default/profile_image_s.jpg', NULL, 0);

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
        '/static/images/open_event_thumbnail.jpg', '/static/images/open_event_thumbnail_s.jpg', '다봄 오픈 기념으로 선착순 2000명에게 강좌 할인 쿠폰을 드려요 !', '2024-02-08',
        '2023-02-08', '다봄 오픈 이벤트 !', '1');

-- <Course> --
insert into course (id, created_time, modified_time, category, description, medium_file_path, original_file_path,
                    small_file_path, title, creator_profile_id)
values ('1', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'BACK_END',
        '스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.', '/static/images/default/thumbnail_m.jpg',
        '/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '스프링 핵심 원리 - 기본편', '1'),
       ('2', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'BACK_END',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '/static/images/jo_oa_pil_thumbnail_m.jpg',
        '/static/images/jo_oa_pil_thumbnail.jpg', '/static/images/jo_oa_pil_thumbnail_s.jpg', '사무자동화산업기사 필기', '1'),
       ('3', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'BACK_END',
        '<오피스 최신강의, 최신출제기준반영,스마트폰수강,무료연장서비스>', '/static/images/os_sil_thumbnail_m.jpg',
        '/static/images/os_sil_thumbnail.jpg', '/static/images/os_sil_thumbnail_s.jpg', '사무자동화산업기사 실기', '1');

insert into description_image (description_image_id, medium_file_path, original_file_path, small_file_path)
values ('1', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg',
        '/static/images/default/description_s.jpg'),
       ('2', '/static/images/jo_oa_pil_description_m.jpg', '/static/images/jo_oa_pil_description.jpg',
        '/static/images/jo_oa_pil_description_s.jpg'),
       ('3', '/static/images/os_sil_description1_m.jpg', '/static/images/os_sil_description1.jpg',
        '/static/images/os_sil_description1_s.jpg'),
       ('3', '/static/images/os_sil_description2_m.jpg', '/static/images/os_sil_description2.jpg',
        '/static/images/os_sil_description2_s.jpg');


-- <Item> --
insert into item (dtype, id, created_time, modified_time, cost_price, discounted_price, product_detail, product_name,
                  medium_file_path, original_file_path, small_file_path)
values ('ticket', '1', '2023-02-13 11:01:41.918716', '2023-02-13 11:01:41.918716', '1', '1',
        '스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.', '스프링 핵심 원리 - 기본편 3개월 수강권',
        '/static/images/default/thumbnail_m.jpg',
        '/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg'),
       ('ticket', '2', '2023-02-13 11:01:41.933261', '2023-02-13 11:01:41.933261', '1000', '1000',
        '스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.', '스프링 핵심 원리 - 기본편 6개월 수강권',
        '/static/images/default/thumbnail_m.jpg',
        '/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg'),
       ('ticket', '3', '2023-02-13 11:01:41.939458', '2023-02-13 11:01:41.939458', '1000', '1000',
        '스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.', '스프링 핵심 원리 - 기본편 영구 수강권',
        '/static/images/default/thumbnail_m.jpg',
        '/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg'),
       ('ticket', '4', '2023-02-13 11:01:49.369901', '2023-02-13 11:01:49.369901', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 필기 3개월 수강권',
        '/static/images/jo_oa_pil_thumbnail_m.jpg',
        '/static/images/jo_oa_pil_thumbnail.jpg', '/static/images/jo_oa_pil_thumbnail_s.jpg'),
       ('ticket', '5', '2023-02-13 11:01:49.371984', '2023-02-13 11:01:49.371984', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 필기 6개월 수강권',
        '/static/images/jo_oa_pil_thumbnail_m.jpg',
        '/static/images/jo_oa_pil_thumbnail.jpg', '/static/images/jo_oa_pil_thumbnail_s.jpg'),
       ('ticket', '6', '2023-02-13 11:01:49.372482', '2023-02-13 11:01:49.372482', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 필기 영구 수강권',
        '/static/images/jo_oa_pil_thumbnail_m.jpg',
        '/static/images/jo_oa_pil_thumbnail.jpg', '/static/images/jo_oa_pil_thumbnail_s.jpg'),
       ('ticket', '7', '2023-02-13 11:01:49.369901', '2023-02-13 11:01:49.369901', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 실기 3개월 수강권',
        '/static/images/os_sil_thumbnail_m.jpg',
        '/static/images/os_sil_thumbnail.jpg', '/static/images/os_sil_thumbnail_s.jpg'),
       ('ticket', '8', '2023-02-13 11:01:49.371984', '2023-02-13 11:01:49.371984', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 실기 6개월 수강권',
        '/static/images/os_sil_thumbnail_m.jpg',
        '/static/images/os_sil_thumbnail.jpg', '/static/images/os_sil_thumbnail_s.jpg'),
       ('ticket', '9', '2023-02-13 11:01:49.372482', '2023-02-13 11:01:49.372482', '1000', '1000',
        '<최신 출제기준반영,스마트폰수강,무료연장서비스,기출문제풀이>', '사무자동화산업기사 실기 영구 수강권',
        '/static/images/os_sil_thumbnail_m.jpg',
        '/static/images/os_sil_thumbnail.jpg', '/static/images/os_sil_thumbnail_s.jpg');

-- <Coupon> --
insert into coupon (id, code, discount, discount_policy, end_date, minimum_amount, name)
values ('1', 'ebf7ebef-cd11-47f7-831f-52b01983019c', '10', 'RATE', '2024-02-13', '100000', '신규 회원 환영 쿠폰');

-- <Issue> --
insert into issue (id, coupon_id, member_id, used)
values ('1', '1', '1', false),
       ('2', '1', '2', false),
       ('3', '1', '3', false),
       ('4', '1', '4', false),
       ('5', '1', '5', false),
       ('6', '1', '6', false);


-- <Ticket> --
insert into ticket (course_period, id, course_id)
values ('THREE_MONTH', '1', '1'),
       ('SIX_MONTH', '2', '1'),
       ('UNLIMITED', '3', '1'),
       ('THREE_MONTH', '4', '2'),
       ('SIX_MONTH', '5', '2'),
       ('UNLIMITED', '6', '2'),
       ('THREE_MONTH', '7', '3'),
       ('SIX_MONTH', '8', '3'),
       ('UNLIMITED', '9', '3');

-- <Enroll> --
insert into enroll (id, created_time, modified_time, course_id, member_id)
values ('1', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '1', '1'),
       ('2', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '1', '2'),
       ('3', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '2', '2');

-- <Wish> --
insert into wish (id, created_time, modified_time, course_id, member_id)
values ('1', '2023-02-08 14:15:45.945695', '2023-02-08 14:15:45.945695', '1', '1'),
       ('2', '2023-02-08 14:15:45.945695', '2023-02-08 14:15:45.945695', '1', '2');


-- <Post> --
insert into post (dtype, id, created_time, modified_time, content, likes, course_id, member_id)
values ('review', '1', '2023-02-08 13:49:21.145507', '2023-02-08 13:49:21.145507', '정말 좋은 강의에요 !', '0', '1', '2'),
       ('inquiry', '2', '2023-02-08 13:49:21.145507', '2023-02-08 13:49:21.145507', '자바 잘 몰라도 수강할 수 있나요?', '0', '1', '3');

insert into review (score, id)
values (5, 1);

insert into inquiry (id)
values (2);

-- <Chapter> --
insert into chapter (id, course_id, sequence, title, is_default)
values ('1', '1', '1', '강의 소개', false),
       ('2', '1', '2', '객체 지향 설계와 스프링', false),
       ('3', '1', '3', '스프링 핵심 원리 이해1 - 예제 만들기', false),
       ('4', '1', '4', '스프링 핵심 원리 이해2 - 객체 지향 원리 적용', false),
       ('5', '1', '5', '스프링 컨테이너와 스프링 빈', false),
       ('6', '1', '6', '싱글톤 컨테이너', false),
       ('7', '1', '7', '컴포넌트 스캔', false),
       ('8', '1', '8', '의존관계 자동 주입', false),
       ('9', '1', '9', '빈 생명주기 콜백', false),
       ('10', '1', '10', '빈 스코프', false),

       ('12', '2', '1', '[공지] 대호쌤의 동영상 공지사항', false),
       ('13', '2', '2', '[제1과목] 사무자동화 시스템', false),
       ('14', '2', '3', '[제2과목] 사무경영관리 개론', false),
       ('15', '2', '4', '[제3과목] 프로그래밍 일반', false),
       ('16', '2', '5', '[제4과목] 정보통신 개론', false),

       ('17', '3', '1', '▶ [특강] 100점 합격을 위한 대호쌤의 세심한 특강', false),
       ('18', '3', '2', '▶ [본강] 단계별로 따라하는 사무자동화실기 시험방식', false);

-- <Unit> --
insert into unit (id, created_time, modified_time, description, sequence, title, file_path, chapter_id, course_id)
values ('1', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '강의 소개',
        '/static/videos/sample-m3u8/sample.m3u8', '1', '1'),

       ('2', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '이야기 - 자바 진영의 추운 겨울과 스프링의 탄생',
        '/static/videos/sample-m3u8/sample.m3u8', '2', '1'),
       ('3', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '스프링이란?',
        '/static/videos/sample-m3u8/sample.m3u8', '2', '1'),
       ('4', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '좋은 객체 지향 프로그래밍이란?',
        '/static/videos/sample-m3u8/sample.m3u8', '2', '1'),
       ('5', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '좋은 객체 지향 설계의 5가지 원칙(SOLID)',
        '/static/videos/sample-m3u8/sample.m3u8', '2', '1'),
       ('6', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '객체 지향 설계와 스프링',
        '/static/videos/sample-m3u8/sample.m3u8', '2', '1'),

       ('7', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '프로젝트 생성',
        '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
       ('8', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '비즈니스 요구사항과 설계',
        '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
       ('9', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '회원 도메인 설계',
        '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
       ('10', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '회원 도메인 개발',
        '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
       ('11', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '회원 도메인 실행과 테스트',
        '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
       ('12', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6', '주문과 할인 도메인 설계',
        '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
       ('13', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '7', '주문과 할인 도메인 개발',
        '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
       ('14', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '8', '주문과 할인 도메인 실행과 테스트',
        '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),

       ('15', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '새로운 할인 정책 개발',
        '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
       ('16', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '새로운 할인 정책 적용과 문제점',
        '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
       ('17', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '관심사의 분리',
        '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
       ('18', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', 'AppConfig 리팩터링',
        '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
       ('19', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '새로운 구조와 할인 정책 적용',
        '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
       ('20', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6', '전체 흐름 정리',
        '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
       ('21', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '7', '좋은 객체 지향 설계의 5가지 원칙의 적용',
        '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
       ('22', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '8', 'IoC, DI, 그리고 컨테이너',
        '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
       ('23', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '9', '스프링으로 전환하기',
        '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),

       ('24', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '스프링 컨테이너 생성',
        '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
       ('25', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '컨테이너에 등록된 모든 빈 조회',
        '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
       ('26', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '스프링 빈 조회 - 기본',
        '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
       ('27', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '스프링 빈 조회 - 동일한 타입이 둘 이상',
        '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
       ('28', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '스프링 빈 조회 - 상속 관계',
        '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
       ('29', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6', 'BeanFactory와 ApplicationContext',
        '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
       ('30', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '7', '다양한 설정 형식 지원 - 자바 코드, XML',
        '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
       ('31', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '8', '스프링 빈 설정 메타 정보 - BeanDefinition',
        '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),

       ('32', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '웹 애플리케이션과 싱글톤',
        '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),
       ('33', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '싱글톤 패턴',
        '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),
       ('34', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '싱글톤 컨테이너',
        '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),
       ('35', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '싱글톤 방식의 주의점',
        '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),
       ('36', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '@Configuration과 싱글톤',
        '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),
       ('37', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6', '@Configuration과 바이트코드 조작의 마법',
        '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),

       ('38', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '컴포넌트 스캔과 의존관계 자동 주입 시작하기',
        '/static/videos/sample-m3u8/sample.m3u8', '7', '1'),
       ('39', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '탐색 위치와 기본 스캔 대상',
        '/static/videos/sample-m3u8/sample.m3u8', '7', '1'),
       ('40', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '필터',
        '/static/videos/sample-m3u8/sample.m3u8', '7', '1'),
       ('41', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '중복 등록과 충돌',
        '/static/videos/sample-m3u8/sample.m3u8', '7', '1'),

       ('42', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '다양한 의존관계 주입 방법',
        '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
       ('43', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '옵션 처리',
        '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
       ('44', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '생성자 주입을 선택해라!',
        '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
       ('45', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '롬복과 최신 트랜드',
        '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
       ('46', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '조회 빈이 2개 이상 - 문제',
        '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
       ('47', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6',
        '@Autowired 필드 명, @Qualifier, @Primary', '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
       ('48', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '7', '애노테이션 직접 만들기',
        '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
       ('49', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '8', '조회한 빈이 모두 필요할 때, List, Map',
        '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
       ('50', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '9', '자동, 수동의 올바른 실무 운영 기준',
        '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),

       ('51', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '빈 생명주기 콜백 시작',
        '/static/videos/sample-m3u8/sample.m3u8', '9', '1'),
       ('52', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2',
        '인터페이스 InitializingBean, DisposableBean', '/static/videos/sample-m3u8/sample.m3u8', '9', '1'),
       ('53', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '빈 등록 초기화, 소멸 메서드',
        '/static/videos/sample-m3u8/sample.m3u8', '9', '1'),
       ('54', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4',
        '애노테이션 @PostConstruct, @PreDestroy', '/static/videos/sample-m3u8/sample.m3u8', '9', '1'),

       ('55', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '빈 스코프란?',
        '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
       ('56', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '프로토타입 스코프',
        '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
       ('57', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3',
        '프로토타입 스코프 - 싱글톤 빈과 함께 사용시 문제점', '/static/videos/sample-m3u8/sample.m3u8', '10',
        '1'),
       ('58', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4',
        '프로토타입 스코프 - 싱글톤 빈과 함께 사용시 Provider로 문제 해결', '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
       ('59', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '웹 스코프',
        '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
       ('60', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6', 'request 스코프 예제 만들기',
        '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
       ('61', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '7', '스코프와 Provider',
        '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
       ('62', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '8', '스코프와 프록시',
        '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
       ('63', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '9', '다음으로',
        '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),

       ('64', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[OT] 합격을 할 수 밖에 없는 이유~!!	',
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
        '/static/videos/sample-m3u8/sample.m3u8', '14', '2'),
       ('71', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '[2-3강] 자료관리 운용	','/static/videos/sample-m3u8/sample.m3u8', '14', '2'),
       ('72', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '[2-4강] 사무작업 형태', '/static/videos/sample-m3u8/sample.m3u8', '14', '2'),
       ('73', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[3-1강] 프로그래밍 언어	', '/static/videos/sample-m3u8/sample.m3u8', '15', '2'),
       ('74', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '2', '[3-2강] 언어의 설계와 구현	', '/static/videos/sample-m3u8/sample.m3u8', '15', '2'),
       ('75', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '[3-3강] 자료형, C언어	', '/static/videos/sample-m3u8/sample.m3u8', '15', '2'),
       ('76', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '[3-4강] C언어, 객체지향, 순서제어	', '/static/videos/sample-m3u8/sample.m3u8', '15', '2'),
       ('77', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '5', '[3-5강] 운영체제	', '/static/videos/sample-m3u8/sample.m3u8', '15', '2'),
       ('78', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[4-1강] 정보통신의 개념	', '/static/videos/sample-m3u8/sample.m3u8', '16', '2'),
       ('79', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '2', '[4-2강] 정보통신 기기	', '/static/videos/sample-m3u8/sample.m3u8', '16', '2'),
       ('80', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '[4-3강] 정보전송 기술	', '/static/videos/sample-m3u8/sample.m3u8', '16', '2'),
       ('81', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '[4-4강] 정보전송 방식과 프로토콜	', '/static/videos/sample-m3u8/sample.m3u8', '16', '2'),
       ('82', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '5', '[4-5강] 정보통신망	', '/static/videos/sample-m3u8/sample.m3u8', '16', '2'),
       ('83', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[대호쌤 공지] 시험기간 중 시험정보에 대한 팁', '/static/videos/sample-m3u8/sample.m3u8', '17', '3'),
       ('84', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '2', '[오리엔테이션] 100점으로 합격하는 대호쌤의 합격전략	', '/static/videos/sample-m3u8/sample.m3u8', '17', '3'),
       ('85', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '[인쇄작업특강] 시험장에서 일어나는 시뮬레이션', '/static/videos/sample-m3u8/sample.m3u8', '17', '3'),
       ('86', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '[엑셀함수특강] 시험에 자주나오는 함수특강', '/static/videos/sample-m3u8/sample.m3u8', '17', '3'),
       ('87', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '5', '[엑셀함수특강] 엑셀 달러($)사용법과 고급함수', '/static/videos/sample-m3u8/sample.m3u8', '17', '3'),
       ('88', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '6', '[엑셀함수특강] 문자열 함수 CONCATENATE', '/static/videos/sample-m3u8/sample.m3u8', '17', '3'),
       ('89', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '7', '[엑셀함수특강] SUMIFS, AVERAGEIFS, COUNTIFS', '/static/videos/sample-m3u8/sample.m3u8', '17', '3'),
       ('90', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '8', '[엑셀함수특강] Frequency 함수', '/static/videos/sample-m3u8/sample.m3u8', '17', '3'),
       ('91', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '9', '[엑셀함수특강] Sumproduct, Isnumber, Find', '/static/videos/sample-m3u8/sample.m3u8', '17', '3'),
       ('92', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '1', '[엑셀-1단계] 작업표형식에 맞춰 데이터 입력하기', '/static/videos/sample-m3u8/sample.m3u8', '18', '3'),
       ('93', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '2', '[엑셀-2단계] 항목계산과 정렬작업', '/static/videos/sample-m3u8/sample.m3u8', '18', '3'),
       ('94', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '3', '[엑셀-3단계] 합계라인 계산과 서식작업', '/static/videos/sample-m3u8/sample.m3u8', '18', '3'),
       ('95', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '4', '[엑셀-4단계] 그래프작업과 인쇄준비작업', '/static/videos/sample-m3u8/sample.m3u8', '18', '3'),
       ('96', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '5', '[액세스-1단계] 테이블 만들기', '/static/videos/sample-m3u8/sample.m3u8', '18', '3'),
       ('97', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '6', '[액세스-2단계] 쿼리 만들기', '/static/videos/sample-m3u8/sample.m3u8', '18', '3'),
       ('98', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '7', '[액세스-3단계] 폼 만들기, 인쇄작업', '/static/videos/sample-m3u8/sample.m3u8', '18', '3'),
       ('99', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '8', '[액세스-4단계] 보고서 만들기, 인쇄작업', '/static/videos/sample-m3u8/sample.m3u8', '18', '3'),
       ('100', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '9', '[파워포인트-1, 2단계] 기본설정과 제1슬라이드 만들기', '/static/videos/sample-m3u8/sample.m3u8', '18', '3'),
       ('101', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '', '10', '[파워포인트-3, 4단계] 제2슬라이드 만들고 인쇄설정하기', '/static/videos/sample-m3u8/sample.m3u8', '18', '3');

-- <Question> --
insert into question (id, created_time, modified_time, content, title, member_id, unit_id)
values ('1', '2023-02-08 14:00:36.192330', '2023-02-08 14:00:36.192330', '다른 강의에는 수업자료에 있는데 여기는 없는 것 같아 글 남겨봅니다.',
        '이 강의는 소스코드 파일이 별도로 제공되지 않나요?', '2', '1');

-- <Answer> --
insert into answer (id, created_time, modified_time, content, member_id, question_id)
values ('1', '2023-02-08 14:01:01.810008', '2023-02-08 14:01:01.810008', '네, 본 강의는 별도의 소스코드가 제공되지 않습니다. 감사합니다.', '1',
        '1');

-- <View> --
insert into view (id, created_time, modified_time, member_id, unit_id)
values ('1', '2023-02-08 14:19:06.113157', '2023-02-08 14:19:06.113157', '1', '1'),
       ('2', '2023-02-08 14:19:06.113157', '2023-02-08 14:19:06.113157', '1', '2'),
       ('3', '2023-02-08 14:19:06.113157', '2023-02-08 14:19:06.113157', '2', '1'),
       ('4', '2023-02-08 14:19:06.113157', '2023-02-08 14:19:06.113157', '2', '2'),
       ('5', '2023-02-08 14:19:06.113157', '2023-02-08 14:19:06.113157', '2', '3');

-- <Feedback> --
insert into feedback (id, created_time, modified_time, thumbs_down, thumbs_up, member_id, unit_id)
values ('1', '2023-02-08 14:18:05.184415', '2023-02-08 14:18:05.184415', false, true, '2', '1'),
       ('2', '2023-02-08 14:18:05.184415', '2023-02-08 14:18:05.184415', true, false, '2', '2'),
       ('3', '2023-02-08 14:18:05.184415', '2023-02-08 14:18:05.184415', false, false, '2', '3');

-- <Order> --
insert into orders (id, created_time, modified_time, item_id, member_id, amount, pay_type, name)
values ('test', '2023-02-08 14:18:05.184415', '2023-02-08 14:18:05.184415', '1', '1', '1', 'CARD', 'test');
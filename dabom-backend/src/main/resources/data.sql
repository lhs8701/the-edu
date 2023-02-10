-- <Member> --
insert into member (id, created_time, modified_time, account, birth_date, email, login_type, mobile, name, nickname, password, medium_file_path, original_file_path, small_file_path, social_id)
values
    (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'admin@naver.com', NULL, 'admin@naver.com','BASIC', NULL, NULL, 'admin', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK','/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg','/static/images/default/profile_image_s.jpg', NULL),
    (2, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student1@naver.com', NULL, 'student1@naver.com','BASIC', NULL, NULL, 'student1', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK','/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg','/static/images/default/profile_image_s.jpg', NULL),
    (3, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student2@naver.com', NULL, 'student2@naver.com','BASIC', NULL, NULL, 'student2', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK','/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg','/static/images/default/profile_image_s.jpg', NULL),
    (4, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student3@naver.com', NULL, 'student3@naver.com','BASIC', NULL, NULL, 'student3', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK','/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg','/static/images/default/profile_image_s.jpg', NULL),
    (5, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student4@naver.com', NULL, 'student4@naver.com','BASIC', NULL, NULL, 'student4', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK','/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg','/static/images/default/profile_image_s.jpg', NULL),
    (6, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', 'student5@naver.com', NULL, 'student5@naver.com','BASIC', NULL, NULL, 'student5', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK','/static/images/default/profile_image_m.jpg', '/static/images/default/profile_image.jpg','/static/images/default/profile_image_s.jpg', NULL);

insert into member_roles (member_id, roles)
values
    (1, 'ROLE_USER'),
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
insert into event (id, created_time, modified_time, medium_file_path, original_file_path, small_file_path, content, end_date, start_date, title, writer_id)
values
    ('1', '2023-02-08 14:18:13.411223', '2023-02-08 14:18:13.411223', '/static/images/default/thumbnail_m.jpg', '/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '재입대 바람', '2024-02-08', '2023-02-08', '연초 지정대상자 지휘서신(직책자용)', '1'),
    ('2', '2023-02-08 14:18:13.411223', '2023-02-08 14:18:13.411223', '/static/images/default/thumbnail_m.jpg', '/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '지금 무료 온라인 컨퍼런스에 등록해보세요!', '2024-02-08', '2023-02-08', 'AWS 클라우드 기초 200% 활용하는 방법은?', '1'),
    ('3', '2023-02-08 14:18:13.411223', '2023-02-08 14:18:13.411223', '/static/images/default/thumbnail_m.jpg', '/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', ' 2021 코리아세일페스타 국내선 최대 88% 할인 및 부산항공마켓까지!', '2024-02-08', '2023-02-08', '★떠나요! 괌으로★ 인천-괌 편도총액 380,500원~', '1'),
    ('4', '2023-02-08 14:18:13.411223', '2023-02-08 14:18:13.411223', '/static/images/default/thumbnail_m.jpg', '/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '종료된 이벤트1', '2023-01-08', '2023-01-07', '종료된 이벤트1', '1'),
    ('5', '2023-02-08 14:18:13.411223', '2023-02-08 14:18:13.411223', '/static/images/default/thumbnail_m.jpg', '/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '종료된 이벤트2', '2023-01-08', '2023-01-07', '종료된 이벤트2', '1'),
    ('6', '2023-02-08 14:18:13.411223', '2023-02-08 14:18:13.411223', '/static/images/default/thumbnail_m.jpg', '/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '종료된 이벤트3', '2023-01-08', '2023-01-07', '종료된 이벤트3', '1');

-- <Course> --
insert into course (id, created_time, modified_time, category, description, price, medium_file_path, original_file_path, small_file_path, title, creator_profile_id)
values
    ('1', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'BACK_END','스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.', '143000', '/static/images/default/thumbnail_m.jpg','/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '스프링 핵심 원리 - 기본편', '1'),
    ('2', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'BACK_END','스프링 중급자가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.', '143000', '/static/images/default/thumbnail_m.jpg','/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '스프링 핵심 원리 - 중급편', '1'),
    ('3', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'BACK_END','스프링 고수가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.', '143000', '/static/images/default/thumbnail_m.jpg','/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '스프링 핵심 원리 - 심화편', '1'),
    ('4', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'BACK_END','스프링 마스터가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.', '143000', '/static/images/default/thumbnail_m.jpg','/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '스프링 핵심 원리 - 극악편', '1'),

    ('5', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'FRONT_END','리액트 입문자가 예제를 만들어가면서 리액트의 핵심 원리를 이해할 수 있습니다.', '143000', '/static/images/default/thumbnail_m.jpg','/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '리액트 핵심 원리 - 기본편', '1'),
    ('6', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'FRONT_END','리액트 중급자가 예제를 만들어가면서 리액트의 핵심 원리를 이해할 수 있습니다.', '143000', '/static/images/default/thumbnail_m.jpg','/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '리액트 핵심 원리 - 중급편', '1'),
    ('7', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'FRONT_END','리액트 고수가 예제를 만들어가면서 리액트의 핵심 원리를 이해할 수 있습니다.', '143000', '/static/images/default/thumbnail_m.jpg','/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '리액트 핵심 원리 - 심화편', '1'),
    ('8', '2023-02-08 12:36:46.459130', '2023-02-08 12:36:46.459130', 'FRONT_END','리액트 마스터가 예제를 만들어가면서 리액트의 핵심 원리를 이해할 수 있습니다.', '143000', '/static/images/default/thumbnail_m.jpg','/static/images/default/thumbnail.jpg', '/static/images/default/thumbnail_s.jpg', '리액트 핵심 원리 - 극악편', '1');

insert into description_image (description_image_id, medium_file_path, original_file_path, small_file_path)
values
    ('1', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('1', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('2', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('2', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('3', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('3', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('4', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('4', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('5', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('5', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('6', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('6', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('7', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('7', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('8', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg'),
    ('8', '/static/images/default/description_m.jpg', '/static/images/default/description.jpg', '/static/images/default/description_s.jpg');

-- <Enroll> --
insert into enroll (id, created_time, modified_time, course_id, member_id)
values
    ('1', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '1', '1'),

    ('2', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '1', '2'),
    ('3', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '2', '2'),
    ('4', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '3', '2'),
    ('5', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '4', '2'),

    ('6', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '5', '3'),
    ('7', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '6', '3'),
    ('8', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '7', '3'),
    ('9', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '8', '3'),
    ('10', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '2', '1'),
    ('11', '2023-02-08 13:45:24.036603', '2023-02-08 13:45:24.036603', '3', '1');

-- <Wish> --
insert into wish (id, created_time, modified_time, course_id, member_id)
values
    ('1', '2023-02-08 14:15:45.945695', '2023-02-08 14:15:45.945695', '1', '1'),
    ('2', '2023-02-08 14:15:45.945695', '2023-02-08 14:15:45.945695', '1', '2');


-- <Post> --
insert into post (dtype, id, created_time, modified_time, content, likes, course_id, member_id)
    values
        ('review', '1', '2023-02-08 13:49:21.145507', '2023-02-08 13:49:21.145507', '정말 좋은 강의에요 !', '0', '1', '2'),
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
       ('11', '1', '11', '다음으로', false),

       ('12', '2', '1', '챕터1', false),

       ('13', '3', '1', '챕터1', false),

       ('14', '4', '1', '챕터1', false),
       ('15', '4', '1', '챕터1', false),
       ('16', '4', '1', '챕터1', false),
       ('17', '4', '1', '챕터1', false);




-- <Unit> --
insert into unit (id, created_time, modified_time, description, sequence, title, file_path, chapter_id, course_id)
values
    ('1', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '강의 소개', '/static/videos/sample-m3u8/sample.m3u8', '1', '1'),

    ('2', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '이야기 - 자바 진영의 추운 겨울과 스프링의 탄생', '/static/videos/sample-m3u8/sample.m3u8', '2', '1'),
    ('3', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '스프링이란?', '/static/videos/sample-m3u8/sample.m3u8', '2', '1'),
    ('4', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '좋은 객체 지향 프로그래밍이란?', '/static/videos/sample-m3u8/sample.m3u8', '2', '1'),
    ('5', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '좋은 객체 지향 설계의 5가지 원칙(SOLID)', '/static/videos/sample-m3u8/sample.m3u8', '2', '1'),
    ('6', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '객체 지향 설계와 스프링', '/static/videos/sample-m3u8/sample.m3u8', '2', '1'),

    ('7', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '프로젝트 생성', '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
    ('8', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '비즈니스 요구사항과 설계', '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
    ('9', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '회원 도메인 설계', '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
    ('10', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '회원 도메인 개발', '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
    ('11', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '회원 도메인 실행과 테스트', '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
    ('12', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6', '주문과 할인 도메인 설계', '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
    ('13', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '7', '주문과 할인 도메인 개발', '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),
    ('14', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '8', '주문과 할인 도메인 실행과 테스트', '/static/videos/sample-m3u8/sample.m3u8', '3', '1'),

    ('15', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '새로운 할인 정책 개발', '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
    ('16', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '새로운 할인 정책 적용과 문제점', '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
    ('17', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '관심사의 분리', '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
    ('18', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', 'AppConfig 리팩터링', '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
    ('19', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '새로운 구조와 할인 정책 적용', '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
    ('20', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6', '전체 흐름 정리', '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
    ('21', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '7', '좋은 객체 지향 설계의 5가지 원칙의 적용', '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
    ('22', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '8', 'IoC, DI, 그리고 컨테이너', '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),
    ('23', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '9', '스프링으로 전환하기', '/static/videos/sample-m3u8/sample.m3u8', '4', '1'),

    ('24', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '스프링 컨테이너 생성', '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
    ('25', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '컨테이너에 등록된 모든 빈 조회', '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
    ('26', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '스프링 빈 조회 - 기본', '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
    ('27', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '스프링 빈 조회 - 동일한 타입이 둘 이상', '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
    ('28', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '스프링 빈 조회 - 상속 관계', '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
    ('29', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6', 'BeanFactory와 ApplicationContext', '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
    ('30', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '7', '다양한 설정 형식 지원 - 자바 코드, XML', '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),
    ('31', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '8', '스프링 빈 설정 메타 정보 - BeanDefinition', '/static/videos/sample-m3u8/sample.m3u8', '5', '1'),

    ('32', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '웹 애플리케이션과 싱글톤', '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),
    ('33', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '싱글톤 패턴', '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),
    ('34', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '싱글톤 컨테이너', '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),
    ('35', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '싱글톤 방식의 주의점', '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),
    ('36', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '@Configuration과 싱글톤', '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),
    ('37', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6', '@Configuration과 바이트코드 조작의 마법', '/static/videos/sample-m3u8/sample.m3u8', '6', '1'),

    ('38', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '컴포넌트 스캔과 의존관계 자동 주입 시작하기', '/static/videos/sample-m3u8/sample.m3u8', '7', '1'),
    ('39', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '탐색 위치와 기본 스캔 대상', '/static/videos/sample-m3u8/sample.m3u8', '7', '1'),
    ('40', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '필터', '/static/videos/sample-m3u8/sample.m3u8', '7', '1'),
    ('41', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '중복 등록과 충돌', '/static/videos/sample-m3u8/sample.m3u8', '7', '1'),

    ('42', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '다양한 의존관계 주입 방법', '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
    ('43', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '옵션 처리', '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
    ('44', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '생성자 주입을 선택해라!', '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
    ('45', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '롬복과 최신 트랜드', '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
    ('46', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '조회 빈이 2개 이상 - 문제', '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
    ('47', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6', '@Autowired 필드 명, @Qualifier, @Primary', '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
    ('48', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '7', '애노테이션 직접 만들기', '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
    ('49', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '8', '조회한 빈이 모두 필요할 때, List, Map', '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),
    ('50', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '9', '자동, 수동의 올바른 실무 운영 기준', '/static/videos/sample-m3u8/sample.m3u8', '8', '1'),

    ('51', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '빈 생명주기 콜백 시작', '/static/videos/sample-m3u8/sample.m3u8', '9', '1'),
    ('52', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '인터페이스 InitializingBean, DisposableBean', '/static/videos/sample-m3u8/sample.m3u8', '9', '1'),
    ('53', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '빈 등록 초기화, 소멸 메서드', '/static/videos/sample-m3u8/sample.m3u8', '9', '1'),
    ('54', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '애노테이션 @PostConstruct, @PreDestroy', '/static/videos/sample-m3u8/sample.m3u8', '9', '1'),

    ('55', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '빈 스코프란?', '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
    ('56', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '2', '프로토타입 스코프', '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
    ('57', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '3', '프로토타입 스코프 - 싱글톤 빈과 함께 사용시 문제점', '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
    ('58', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '4', '프로토타입 스코프 - 싱글톤 빈과 함께 사용시 Provider로 문제 해결', '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
    ('59', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '5', '웹 스코프', '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
    ('60', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '6', 'request 스코프 예제 만들기', '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
    ('61', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '7', '스코프와 Provider', '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
    ('62', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '8', '스코프와 프록시', '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),
    ('63', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '9', '다음으로', '/static/videos/sample-m3u8/sample.m3u8', '10', '1'),

    ('64', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '유닛1', '/static/videos/sample-m3u8/sample.m3u8', '11', '2'),
    ('65', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '유닛1', '/static/videos/sample-m3u8/sample.m3u8', '12', '3'),
    ('66', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '유닛1', '/static/videos/sample-m3u8/sample.m3u8', '13', '4'),

    ('67', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '유닛1', '/static/videos/sample-m3u8/sample.m3u8', '14', '5'),
    ('68', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '유닛1', '/static/videos/sample-m3u8/sample.m3u8', '15', '6'),
    ('69', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '유닛1', '/static/videos/sample-m3u8/sample.m3u8', '16', '7'),
    ('70', '2023-02-08 13:07:33.914238', '2023-02-08 13:07:33.914238', '소개', '1', '유닛1', '/static/videos/sample-m3u8/sample.m3u8', '17', '8');


-- <Question> --
insert into question (id, created_time, modified_time, content, title, member_id, unit_id)
values
    ('1', '2023-02-08 14:00:36.192330', '2023-02-08 14:00:36.192330', '다른 강의에는 수업자료에 있는데 여기는 없는 것 같아 글 남겨봅니다.', '이 강의는 소스코드 파일이 별도로 제공되지 않나요?', '2', '1');

-- <Answer> --
insert into answer (id, created_time, modified_time, content, member_id, question_id)
values
    ('1', '2023-02-08 14:01:01.810008', '2023-02-08 14:01:01.810008', '네, 본 강의는 별도의 소스코드가 제공되지 않습니다. 감사합니다.', '1', '1');

-- <View> --
insert into view (id, created_time, modified_time, member_id, unit_id)
values
    ('1', '2023-02-08 14:19:06.113157', '2023-02-08 14:19:06.113157', '1', '1'),
    ('2', '2023-02-08 14:19:06.113157', '2023-02-08 14:19:06.113157', '1', '2'),
    ('3', '2023-02-08 14:19:06.113157', '2023-02-08 14:19:06.113157', '2', '1'),
    ('4', '2023-02-08 14:19:06.113157', '2023-02-08 14:19:06.113157', '2', '2'),
    ('5', '2023-02-08 14:19:06.113157', '2023-02-08 14:19:06.113157', '2', '3');

-- <Feedback> --
insert into feedback (id, created_time, modified_time, thumbs_down, thumbs_up, member_id, unit_id)
values
    ('1', '2023-02-08 14:18:05.184415', '2023-02-08 14:18:05.184415', false, true, '2', '1'),
    ('2', '2023-02-08 14:18:05.184415', '2023-02-08 14:18:05.184415',  true, false, '2', '2'),
    ('3', '2023-02-08 14:18:05.184415', '2023-02-08 14:18:05.184415',  false, false, '2', '3');

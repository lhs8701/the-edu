package joeuncamp.dabombackend.global.constant;

import javax.swing.text.html.HTML;

public class ExampleValue {

    public static class Event{
        public final static String TITLE = "연초 지정대상자 지휘서신(직책자용)";
        public final static String CONTENT = "재입대 바람";
    }

    public static class Feedback{
        public final static String COMMENT = "최고의 강의입니다.";
    }

    public static class Video{
        public final static String URL = "/static/videos/sample-m3u8/sample.m3u8";
    }

    public static class Image{
        public final static String SAMPLE = "/static/images/default/sample.jpg";
        public final static String THUMBNAIL = "/static/images/default/thumbnail.jpg";
        public final static String PROFILE_IMAGE = "/static/images/default/profile_image.jpg";
        public final static String DESCRIPTION_IMAGE = "/static/images/default/description.jpg";
    }

    public static class Time{
        public final static String DATE = "2023.02.02";
    }

    public static class Answer{
        public final static String DESCRIPTION = "@RequestBody 어노테이션이 빠졌는지 한번 확인해보세요.";
    }

    public static class Question{
        public final static String TITLE = "RequestBody에 DTO 정보가 넘어오지 않는 현상";
        public final static String DESCRIPTION = "RequestBody를 resolve할 수 없다는 에러가 발생합니다. 이유가 뭘까요?";
    }

    public static class Unit{
        public final static String TITLE = "스프링 빈에 대한 이해";
        public final static String DESCRIPTION = "스프링 빈에 대해 알아봅시다.";
        public static final String CHAPTER = "스프링 빈";

    }

    public static class Course{
        public final static String TITLE = "스프링 핵심 원리 - 기본편";
        public final static String DESCRIPTION = "스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.";
        public final static String CATEGORY = "백엔드";
    }

    public static class JWT{
        public final static String ACCESS = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiIxIiwiaWF0IjoxNjc1MzAwMTIxLCJleHAiOjE3MDY4MzYxMjEsImp0aSI6IjZlMTMwYmZiLTU3NDgtNDI0NS1iZjQ0LTYzMjk0MzVkOTQxYiIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIn0.uOgQjAj8hdt32hOUptLT8MgkJ5H9kms6Yx1WPOBL7K8";
        public final static String REFRESH = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiIxIiwiaWF0IjoxNjc1MzAwMTIxLCJleHAiOjE3MDY4MzYxMjEsImp0aSI6ImU4YTY4MzMzLWE2ZWYtNDQwOC1hYjNkLWE1MWJiNjEyZTdkOCIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIn0.Rw3hOW6ldyV8uJbIMdv2w6buulSAPoh4a9KZaJ2gexg";
    }

    public static class Member{
        public final static String ACCOUNT = "user@naver.com";
        public final static String PASSWORD = "Abc1234!";
        public static final String NAME = "심재헌";
        public final static String NICKNAME = "hello";
        public final static String MOBILE = "010-1234-5678";
        public final static String BIRTH_DATE = "1999.01.01";
        public final static String EMAIL = "user@naver.com";
    }

    public static class Post{
        public final static String CONTENT = "정말 좋은 강의에요 !";
        public final static int RATING = 5;
    }
}

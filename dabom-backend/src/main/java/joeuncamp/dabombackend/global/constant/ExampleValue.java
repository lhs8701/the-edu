package joeuncamp.dabombackend.global.constant;

import javax.swing.text.html.HTML;

public class ExampleValue {

    public static class Unit{
        public final static String TITLE = "스프링 빈에 대한 이해";
        public final static String DESCRIPTION = "스프링 빈에 대해 알아봅시다.";
        public final static String VIDEO_URL = "E:\\ROOM\\Github\\dabom\\dabom-backend\\src\\test\\resources\\videos\\sample-m3u8\\sample.m3u8";
    }

    public static class Course{
        public final static String TITLE = "스프링 핵심 원리 - 기본편";
        public final static String DESCRIPTION = "스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해할 수 있습니다.";
        public final static String CATEGORY = "백엔드";
        public final static long PRICE = 143000L;
        public final static String THUMBNAIL_IMAGE_URL = "E:\\ROOM\\Github\\dabom\\dabom-backend\\src\\test\\resources\\images\\sample.jpg";
        public final static double score = 3.5;
    }

    public static class JWT{
        public final static String ACCESS = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiIyIiwiaWF0IjoxNjc1MjMyMzI5LCJleHAiOjE3MDY3NjgzMjksImp0aSI6ImYxOTJlMDZhLWNiMjMtNGVhOS1hYTQ1LWMwNGY1Y2NkZWFlMyIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIn0.-mgeim9fz7gLkJDo9E8soLuZ5Y1FAZ-Kvkhir00Y_Zk";
        public final static String REFRESH = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiIyIiwiaWF0IjoxNjc1MjMyMzI5LCJleHAiOjE3MDY3NjgzMjksImp0aSI6IjQ4YTUwOGFlLWViNWUtNDk0OC05ZTU1LWQxZDk2MGZhY2UwYyIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIn0.HwiXtUoqClrQptsTTISIyd5dIsKF8MuAAUvR0IjIQFQ";
    }

    public static class Member{
        public final static String ACCOUNT = "user@naver.com";
        public final static String PASSWORD = "Abc1234!";
        public static final String NAME = "심재헌";
        public final static String NICKNAME = "hello";
        public final static String MOBILE = "010-1234-5678";
        public final static String BIRTH_DATE = "1999.01.01";
        public final static String EMAIL = "user@naver.com";
        public final static String PROFILE_IMAGE = "https://t1.daumcdn.net/cfile/tistory/2513B53E55DB206927";
    }

    public static class Post{
        public final static String CONTENT = "정말 좋은 강의에요 !";
        public final static int RATING = 5;
    }
}

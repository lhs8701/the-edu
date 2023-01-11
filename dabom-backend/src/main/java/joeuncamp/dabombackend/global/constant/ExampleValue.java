package joeuncamp.dabombackend.global.constant;

import javax.swing.text.html.HTML;

public class ExampleValue {
    public static class CreatorProfile{

        public static final String CREATOR_NICKNAME = "심재헌";
    }

    public static class Course{
        public final static String TITLE = "스프링 핵심 원리 - 기본편";
        public final static String DESCRIPTION = "스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해하고, 스프링 기본기를 확실히 다질 수 있습니다.";
        public final static String CATEGORY = "백엔드";
        public final static Long PRICE = 143000L;
    }

    public static class JWT{
        public final static String ACCESS = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQG5hdmVyLmNvbSIsImFjY291bnQiOiJ1c2VyQG5hdmVyLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNjczMzM2MzgxLCJleHAiOjE3MDQ4NzIzODF9.6d5Ai_OEyCUzTclcyj70WWrqK6gfZOYPDFBtdJhfP8M";
        public final static String REFRESH = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQG5hdmVyLmNvbSIsImFjY291bnQiOiJ1c2VyQG5hdmVyLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNjczMzM2MzgxLCJleHAiOjE3MzY0MDgzODF9.F_ra_Hl7IEWIGCkb9e3DseomDeI9LDoeHLbFedD8k-M";
    }

    public static class Member{
        public final static String ACCOUNT = "user@naver.com";
        public final static String PASSWORD = "Abc1234!";
        public final static String NICKNAME = "hello";
        public final static String MOBILE = "010-1234-5678";
        public final static String BIRTH_DATE = "1999.01.01";
        public final static String EMAIL = "user@naver.com";
    }
}

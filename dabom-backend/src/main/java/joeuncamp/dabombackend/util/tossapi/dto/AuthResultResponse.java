package joeuncamp.dabombackend.util.tossapi.dto;

import lombok.Getter;

@Getter
public class AuthResultResponse {
    String resultType;
    @Getter
    public static class Success{
        String signature;
        PersonalData personalData;
        @Getter
        public static class PersonalData {
            String name;
            String phone;
            String birthday;
        }
    }
    @Getter
    public static class Error{
        String errorCode;
        String reason;
    }
}

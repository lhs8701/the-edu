package joeuncamp.dabombackend.util.kakaoapi;

import lombok.Getter;
import lombok.ToString;

@Getter
public class KakaoProfile {
    private Long id;
    private KakaoAccount kakao_account;

    @Getter
    @ToString
    public static class KakaoAccount {
        private String name;
        private String email;
        private Profile profile;
        private boolean is_email_valid;
        private boolean is_email_verified;

        @Getter
        public static class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
            private boolean is_default_image;
        }
    }
}

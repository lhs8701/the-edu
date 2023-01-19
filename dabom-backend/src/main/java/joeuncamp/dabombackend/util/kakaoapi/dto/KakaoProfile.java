package joeuncamp.dabombackend.util.kakaoapi.dto;

import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;

@Getter
@ToString
public class KakaoProfile {
    private long id;
    private KakaoAccount kakao_account;

    @Getter
    @ToString
    @AllArgsConstructor
    public static class KakaoAccount {
        private String email;
        private Profile profile;

        @Getter
        @ToString
        @AllArgsConstructor
        public static class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
            private boolean is_default_image;
        }
    }

    public Member toEntity(){
        return Member.builder()
                .account(this.kakao_account.email)
                .password(null)
                .name(null)
                .nickname(this.kakao_account.profile.nickname)
                .mobile(null)
                .birthDate(null)
                .email(this.kakao_account.email)
                .loginType(LoginType.KAKAO)
                .socialId(String.valueOf(this.id))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    @Builder
    public KakaoProfile(Long id, String email, String nickname, String thumbnail_image_url, String profile_image_url, boolean is_default_image){
        KakaoAccount.Profile profile = new KakaoAccount.Profile(nickname, thumbnail_image_url, profile_image_url, is_default_image);
        KakaoAccount kakaoAccount = new KakaoAccount(email, profile);
        this.id = id;
        this.kakao_account = kakaoAccount;
    }
}

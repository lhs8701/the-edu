package joeuncamp.dabombackend.util.kakaoapi.dto;

import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.global.constant.StaticFilePath;
import lombok.*;

import java.util.Collections;

@Getter
@ToString
@NoArgsConstructor
public class KakaoProfile {
    private long id;
    private KakaoAccount kakao_account;

    @Getter
    public static class KakaoAccount {
        private String email;
        private Profile profile;
        @Getter
        public static class Profile {
            private String nickname;
        }
    }

    public Member toEntity() {
        return Member.builder()
                .account(this.kakao_account.email)
                .nickname(this.kakao_account.profile.nickname)
                .email(this.kakao_account.email)
                .profileImage(new ImageInfo(StaticFilePath.DEFAULT_PROFILE_IMAGE.getUrl()))
                .loginType(LoginType.KAKAO)
                .socialId(String.valueOf(this.id))
                .roles(Collections.singletonList("ROLE_USER"))
                .payPoint(0)
                .certified(false)
                .build();
    }
}

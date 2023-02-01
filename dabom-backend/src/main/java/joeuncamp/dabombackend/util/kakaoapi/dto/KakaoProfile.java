package joeuncamp.dabombackend.util.kakaoapi.dto;

import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.LoginType;
import lombok.*;

import java.util.Collections;

@Getter
@ToString
@NoArgsConstructor
public class KakaoProfile {
    private long id;
    private KakaoAccount kakao_account;

    public static class KakaoAccount {
        private String email;
        private Profile profile;

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
                .nickname(this.kakao_account.profile.nickname)
                .email(this.kakao_account.email)
                .profileImage(ImageInfo.builder()
                        .smallFilePath(this.kakao_account.profile.thumbnail_image_url)
                        .mediumFilePath(this.kakao_account.profile.thumbnail_image_url)
                        .originalFilePath(this.kakao_account.profile.profile_image_url)
                        .build())
                .loginType(LoginType.KAKAO)
                .socialId(String.valueOf(this.id))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}

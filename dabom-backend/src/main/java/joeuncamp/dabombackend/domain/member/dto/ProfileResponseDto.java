package joeuncamp.dabombackend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.file.video.entity.VideoInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseDto {
    @Schema(description = "아이디넘버", example = "1")
    Long id;
    @Schema(description="계정", example = ExampleValue.Member.ACCOUNT)
    String account;
    @Schema(description="별명", example = ExampleValue.Member.NICKNAME)
    String nickname;
    @Schema(description="전화번호", example = ExampleValue.Member.MOBILE)
    String mobile;
    @Schema(description = "생년월일", example = ExampleValue.Member.BIRTH_DATE)
    String birthDate;
    @Schema(description = "이메일", example = ExampleValue.Member.EMAIL)
    String email;
    @Schema(description = "프로필 이미지")
    ImageInfo profileImage;
    @Schema(description = "로그인 유형", example = "basic")
    LoginType loginType;

    public ProfileResponseDto(Member member) {
        this.id = member.getId();
        this.account = member.getAccount();
        this.nickname = member.getNickname();
        this.mobile = member.getMobile();
        this.birthDate = member.getBirthDate();
        this.email = member.getEmail();
        this.loginType = member.getLoginType();
        this.profileImage = new ImageInfo("https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/436/8142f53e51d2ec31bc0fa4bec241a919_crop.jpeg");
    }
}

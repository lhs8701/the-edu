package joeuncamp.dabombackend.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.ExampleValue;
import joeuncamp.dabombackend.global.constant.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ProfileDto {
    @Getter
    public static class Request{

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest{
        @Schema(hidden = true)
        Long memberId;
        @Schema(description="별명", example = ExampleValue.Member.NICKNAME)
        String nickname;
        @Schema(description = "이메일", example = ExampleValue.Member.EMAIL)
        String email;
        @Schema(description = "프로필 이미지 경로", example = ExampleValue.Image.URL)
        String profileImage;
    }
    @Getter
    @AllArgsConstructor
    @Builder
    public static class ShortResponse {
        @Schema(description = "아이디넘버", example = "1")
        Long id;
        @Schema(description="별명", example = ExampleValue.Member.NICKNAME)
        String nickname;
        @Schema(description = "프로필 이미지")
        ImageInfo profileImage;

        public ShortResponse(Member member){
            this.id = member.getId();
            this.nickname = member.getNickname();
            this.profileImage = member.getProfileImage();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response{
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
        @Schema(description = "소셜 아이디넘버")
        String socialId;
        @Schema(description = "로그인 유형")
        LoginType loginType;
        @Schema(description = "프로필 이미지")
        ImageInfo profileImage;

        public Response(Member member) {
            this.id = member.getId();
            this.account = member.getAccount();
            this.nickname = member.getNickname();
            this.mobile = member.getMobile();
            this.birthDate = member.getBirthDate();
            this.email = member.getEmail();
            this.socialId = member.getSocialId();
            this.loginType = member.getLoginType();
            this.profileImage = member.getProfileImage();
        }
    }
}

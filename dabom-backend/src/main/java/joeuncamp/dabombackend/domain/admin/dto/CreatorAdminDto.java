package joeuncamp.dabombackend.domain.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.LoginType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CreatorAdminDto {
    @Getter
    public static class Response{
        Long memberId;
        Long creatorId;
        String account;
        String name;
        String nickname;
        String mobile;
        String birthDate;
        String email;
        ImageInfo profileImage;
        @Enumerated(value = EnumType.STRING)
        LoginType loginType;
        String socialId;
        List<String> roles;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime joinedDate;
        boolean activated;

        public Response(CreatorProfile creatorProfile){
            Member member = creatorProfile.getMember();
            this.memberId = member.getId();
            this.creatorId = creatorProfile.getId();
            this.account = member.getAccount();
            this.name = member.getName();
            this.nickname = member.getNickname();
            this.mobile = member.getMobile();
            this.birthDate = member.getBirthDate();
            this.email = member.getEmail();
            this.profileImage = member.getProfileImage();
            this.loginType = member.getLoginType();
            this.socialId = member.getSocialId();
            this.roles = member.getRoles();
            this.joinedDate = member.getCreatedTime();
            this.activated = creatorProfile.isActivated();
        }
    }
}

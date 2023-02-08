package joeuncamp.dabombackend.domain.member.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.LoginType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class MemberAdminDto {
    @Getter
    public static class ShortResponse {
        Long id;
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
        boolean isCreator;
        List<String> roles;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime joinedDate;
        public ShortResponse(Member member){
            this.id = member.getId();
            this.account = member.getAccount();
            this.name = member.getName();
            this.nickname = member.getNickname();
            this.mobile = member.getMobile();
            this.birthDate = member.getBirthDate();
            this.email = member.getEmail();
            this.profileImage = member.getProfileImage();
            this.loginType = member.getLoginType();
            this.socialId = member.getSocialId();
            this.isCreator = !Objects.isNull(member.getCreatorProfile());
            this.roles = member.getRoles();
            this.joinedDate = member.getCreatedTime();
        }
    }
}

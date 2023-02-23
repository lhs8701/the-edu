package joeuncamp.dabombackend.domain.admin.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.util.tossapi.dto.MemberPrivacy;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class MemberAdminDto {
    @Getter
    public static class ShortResponse {
        Long id;
        String account;
        String nickname;
        String email;
        ImageInfo profileImage;
        @Enumerated(value = EnumType.STRING)
        LoginType loginType;
        String socialId;
        boolean isCreator;
        boolean certified;
        boolean locked;
        MemberPrivacy memberPrivacy;
        List<String> roles;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime joinedDate;
        public ShortResponse(Member member){
            this.id = member.getId();
            this.account = member.getAccount();
            this.nickname = member.getNickname();
            this.email = member.getEmail();
            this.profileImage = member.getProfileImage();
            this.loginType = member.getLoginType();
            this.socialId = member.getSocialId();
            this.isCreator = !Objects.isNull(member.getCreatorProfile());
            this.roles = member.getRoles();
            this.joinedDate = member.getCreatedTime();
            this.certified = member.isCertified();
            this.locked = member.isLocked();
            if (this.certified){
                this.memberPrivacy = member.getMemberPrivacy();
            }
        }
    }
}

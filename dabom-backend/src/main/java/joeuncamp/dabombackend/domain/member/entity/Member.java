package joeuncamp.dabombackend.domain.member.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.file.image.entity.ImageInfo;
import joeuncamp.dabombackend.domain.order.entity.Issue;
import joeuncamp.dabombackend.domain.post.entity.Post;
import joeuncamp.dabombackend.domain.wish.entity.Wish;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import joeuncamp.dabombackend.global.constant.LoginType;
import joeuncamp.dabombackend.util.tossapi.dto.MemberPrivacy;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String account;
    String password;
    String nickname;
    String email;
    ImageInfo profileImage;
    boolean emailCertified;
    boolean certified;
    MemberPrivacy memberPrivacy;
    long payPoint;

    @Enumerated(value = EnumType.STRING)
    LoginType loginType;

    String socialId;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    CreatorProfile creatorProfile;

    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Enroll> enrollList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Wish> wishList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Post> postList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Issue> issueList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    boolean locked;

    public void updateProfile(String nickname, String email, String imageUrl) {
        if (nickname != null) {
            this.nickname = nickname;
        }
        if (email != null) {
            this.email = email;
        }
        if (imageUrl != null) {
            this.profileImage = new ImageInfo(imageUrl);
        }
    }

    public void updatePoint(long amount) {
        this.payPoint += amount;
    }


    public boolean isCreator() {
        return this.getCreatorProfile() != null && this.getCreatorProfile().isActivated();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void setCertified(MemberPrivacy memberPrivacy) {
        this.certified = true;
        this.memberPrivacy = memberPrivacy;
    }
    public void setEmailCertified() {
        this.emailCertified = true;
    }
    public void lock() {
        this.locked = true;
    }
    public void unlock() {
        this.locked = false;
    }
}

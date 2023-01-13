package joeuncamp.dabombackend.domain.member.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.member.dto.ProfileUpdateParam;
import joeuncamp.dabombackend.global.common.BaseTimeEntity;
import joeuncamp.dabombackend.global.constant.LoginType;
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

    String name;

    String nickname;

    String mobile;

    String birthDate;

    String email;

    @Enumerated(value = EnumType.STRING)
    LoginType loginType;

    String loginToken;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    CreatorProfile creatorProfile;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    List<Enroll> enrollList = new ArrayList<>();

    /* @ElementCollection
        @OneToMany 처럼 엔티티를 컬렉션으로 사용하는 것이 아닌, Integer, String, 임베디드 타입 같은 값 타입을 컬렉션으로 사용
        컬렉션과 같은 형태의 데이터를 컬럼에 저장할 수 없기 때문에, 별도의 테이블을 생성하여 컬렉션을 관리
     */
    /* @Builder.Default
    특정 속성에 기본값을 지정할 때 사용
    컬렉션을 생성자로 생성할 경우, null로 초기화 되지 않지만,
    builder 패턴으로 생성하면서 해당 컬렉션을 초기화하지 않으면, null로 초기화 된다.
    이 때, 컬렉션 필드에 @Builder.Default 어노테이션을 붙여주면 builder 패턴으로 생성시에도 컬렉션으로 초기화된다.
    */
    @Schema(description = "권한", example = "일반")
    @ElementCollection(fetch = FetchType.EAGER) //LAZY -> 오류
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void updateProfile(ProfileUpdateParam updateParam) {
        if (updateParam.getNickname() != null) {
            this.nickname = updateParam.getNickname();
        }
        if (updateParam.getEmail() != null) {
            this.email = updateParam.getEmail();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

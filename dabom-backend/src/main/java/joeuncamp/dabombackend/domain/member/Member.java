package joeuncamp.dabombackend.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Member {
    Long id;
    String account;
    String password;
    String nickname;
    String mobile;
    String role;
}

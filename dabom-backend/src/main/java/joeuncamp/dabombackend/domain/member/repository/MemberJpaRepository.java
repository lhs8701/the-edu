package joeuncamp.dabombackend.domain.member.repository;


import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.global.constant.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByAccount(String account);
    Optional<Member> findByAccountAndLoginTypeAndLockedIsFalse(String account, LoginType loginType);
    Optional<Member> findByLoginTypeAndSocialIdAndLockedIsFalse(LoginType loginType, String kakaoId);
    Optional<Member> findByIdAndLockedIsFalse(Long memberId);
    List<Member> findByLockedIsTrue();

}

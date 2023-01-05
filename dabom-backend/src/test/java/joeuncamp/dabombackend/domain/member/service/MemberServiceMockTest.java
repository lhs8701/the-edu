package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.Member;
import joeuncamp.dabombackend.domain.member.dto.MemberCreationRequestDto;
import joeuncamp.dabombackend.domain.member.repository.MemberMemoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MemberServiceMockTest {

    @InjectMocks
    @Autowired
    MemberManageService memberManageService;

    @Mock
    @Autowired
    MemberMemoryRepository memberMemoryRepository;

    @Test
    @DisplayName("Mock 테스트 - 회원을 등록하고 조회한다.")
    void Mock_테스트_회원을_등록하고_조회한다() {
        // given
        MemberCreationRequestDto personalData = new MemberCreationRequestDto("test");
//        when(memberMemoryRepository.save(any(Member.class))).thenReturn(1L);

        // when
        Long createdId = memberManageService.createMember(personalData);
        Member member = memberManageService.getMember(createdId);

        // then
        assertThat(member.getId()).isEqualTo(1L);
    }
}

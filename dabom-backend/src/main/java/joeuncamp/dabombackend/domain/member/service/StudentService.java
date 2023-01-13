package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.course.dto.MyCourseShortResponseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final MemberJpaRepository memberJpaRepository;
    private final EnrollJpaRepository enrollJpaRepository;

    /**
     * 내가 등록한 강좌 목록을 조회합니다.
     *
     * @param memberId 회원 아이디넘버
     * @return 등록한 강좌 목록
     */
    public List<MyCourseShortResponseDto> getMyCourses(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        List<Enroll> enrolls = enrollJpaRepository.findAllByMember(member);
        List<Course> myCourses = enrolls.stream().map(Enroll::getCourse).toList();

        return myCourses.stream().map(MyCourseShortResponseDto::new).toList();
    }
}

package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.course.dto.CourseShortResponseDto;
import joeuncamp.dabombackend.domain.course.dto.MyCourseShortResponseDto;
import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.entity.Enroll;
import joeuncamp.dabombackend.domain.course.repository.EnrollJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.wish.entity.Wish;
import joeuncamp.dabombackend.domain.wish.repository.WishJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final MemberJpaRepository memberJpaRepository;
    private final EnrollJpaRepository enrollJpaRepository;

    private final WishJpaRepository wishJpaRepository;

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

    /**
     * 찜한 모든 강좌를 조회합니다.
     *
     * @param memberId 회원 아이디넘버
     * @return 찜한 강좌 목록
     */
    public List<CourseShortResponseDto> getWishedCourses(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(CResourceNotFoundException::new);
        List<Wish> wishes = wishJpaRepository.findAllByMember(member);
        List<Course> wishedCourses = wishes.stream().map(Wish::getCourse).toList();
        return wishedCourses.stream().map(CourseShortResponseDto::new).toList();
    }
}

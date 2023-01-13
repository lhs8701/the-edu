package joeuncamp.dabombackend.domain.wish.service;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.course.repository.CourseJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.wish.dto.WishRequestDto;
import joeuncamp.dabombackend.domain.wish.entity.Wish;
import joeuncamp.dabombackend.domain.wish.repository.WishJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final MemberJpaRepository memberJpaRepository;
    private final CourseJpaRepository courseJpaRepository;
    private final WishJpaRepository wishJpaRepository;

    public void toggleWish(WishRequestDto wishRequestDto){
        Member member = memberJpaRepository.findById(wishRequestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Course course = courseJpaRepository.findById(wishRequestDto.getCourseId()).orElseThrow(CResourceNotFoundException::new);
        Optional<Wish> wish = wishJpaRepository.findByMemberAndCourse(member, course);
        if (wish.isPresent()){
            wishJpaRepository.delete(wish.get());
            return;
        }
        CreateAndSaveWish(wishRequestDto, member, course);
    }

    private void CreateAndSaveWish(WishRequestDto wishRequestDto, Member member, Course course) {
        Wish wish = wishRequestDto.toEntity(member, course);
        wishJpaRepository.save(wish);
    }
}

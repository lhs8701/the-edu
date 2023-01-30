package joeuncamp.dabombackend.domain.course.service;

import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.repository.RankingJpaRepository;
import joeuncamp.dabombackend.global.constant.CategoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RankingService {
    private final RankingJpaRepository rankingJpaRepository;

    public List<CourseDto.ShortResponse> findTop4FromCategory(String category){
        CategoryType categoryType = CategoryType.findByTitle(category);
        return rankingJpaRepository.findAllByCategory(categoryType).stream()
                .map(CourseDto.ShortResponse::new)
                .toList();
    }
}

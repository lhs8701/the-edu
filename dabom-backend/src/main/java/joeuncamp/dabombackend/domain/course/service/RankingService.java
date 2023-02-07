package joeuncamp.dabombackend.domain.course.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.course.dto.CourseDto;
import joeuncamp.dabombackend.domain.course.dto.RankingDto;
import joeuncamp.dabombackend.domain.course.entity.RankedCourse;
import joeuncamp.dabombackend.domain.course.repository.RankingJpaRepository;
import joeuncamp.dabombackend.global.constant.CategoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RankingService {
    private final RankingJpaRepository rankingJpaRepository;

    /**
     * 강좌별로 일주일 간 수강 등록이 가장 많았던 4개의 강좌를 조회합니다.
     *
     * @return 강좌 랭킹
     */
    public List<RankingDto> getCourseRanking() {
        CategoryType[] categoryTypes = CategoryType.values();
        List<RankingDto> responseDto = new ArrayList<>();
        for (CategoryType category : categoryTypes) {
            String title = category.getTitle();
            List<CourseDto.ShortResponse> courses = rankingJpaRepository.findAllByCategory(category).stream()
                    .map(CourseDto.ShortResponse::new)
                    .toList();
            responseDto.add(new RankingDto(title, courses));
        }

        return responseDto;
    }
}

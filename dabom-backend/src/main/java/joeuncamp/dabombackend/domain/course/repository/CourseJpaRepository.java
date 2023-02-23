package joeuncamp.dabombackend.domain.course.repository;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.global.constant.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseJpaRepository extends JpaRepository<Course, Long> {
    Page<Course> findCourseByCategoryAndActiveIsTrue(CategoryType categoryType, Pageable pageable);
    Page<Course> findByActiveIsTrue(Pageable pageable);
    List<Course> findByActiveIsFalse();
    @Query("select c from Course c" + " join Review r on r.course = c " + " where c.active = true and c.locked = false " + "group by c " + "order by avg(r.score) desc ")
    Page<Course> findCourseByCategoryOrderByScore(CategoryType categoryType, Pageable pageable);
    @Query("select c from Course c" + " join Enroll e on e.course = c " + " where c.active = true and c.locked = false " + "group by c " + "order by count(e) desc ")
    Page<Course> findCourseByCategoryOrderByEnrollCount(CategoryType categoryType, Pageable pageable);
    @Query("select c from Course c" + " join Wish w on w.course = c " + " where c.active = true and c.locked = false " + "group by c " + "order by count(w) desc ")
    Page<Course> findCourseByCategoryOrderByWishCount(CategoryType categoryType, Pageable pageable);
    @Query(" select c from Course c " + " join CreatorProfile p on c.creatorProfile = p " + " join Member m on p.member = m " + " where c.active = true and c.locked = false and (c.title like %:keyword% or m.memberPrivacy.name like %:keyword%) ")
    Page<Course> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
    @Query(" select c from Course c " + " join Enroll e on e.course = c " + " where c.active = true and c.locked = false and c.category = :category and e.createdTime > :time " + " group by c " + " order by count(e) desc ")
    Page<Course> findByEnrolledCountFromPast(@Param("category") CategoryType categoryType, @Param("time") LocalDateTime time, Pageable pageable);
    default Page<Course> findByEnrolledCountFromWeek(CategoryType categoryType) {
        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0, 4);
        return findByEnrolledCountFromPast(categoryType, now.minusDays(7), pageable);
    }
    List<Course> findByCreatorProfile(CreatorProfile creatorProfile);
    List<Course> findByLockedIsTrue();
}
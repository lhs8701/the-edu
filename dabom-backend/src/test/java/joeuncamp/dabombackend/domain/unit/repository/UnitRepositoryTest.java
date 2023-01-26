package joeuncamp.dabombackend.domain.unit.repository;

import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.global.config.JpaAuditingConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UnitRepositoryTest {
    @Autowired
    UnitJpaRepository unitJpaRepository;

//    @Test
//    @DisplayName("강의를 생성하고 저장한다.")
//    void 강의를_생성하고_저장한다() {
//        // given
//        Unit unit = Unit.builder().build();
//        Unit saved = unitJpaRepository.save(unit);
//
//        // when
//        Optional<Unit> found = unitJpaRepository.findById(saved.getId());
//
//        // then
//        assertThat(found.isPresent()).isEqualTo(true);
//        assertThat(found.get().getId()).
//    }
}

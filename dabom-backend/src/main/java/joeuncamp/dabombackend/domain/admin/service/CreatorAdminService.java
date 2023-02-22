package joeuncamp.dabombackend.domain.admin.service;

import joeuncamp.dabombackend.domain.admin.dto.CreatorAdminDto;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.creator.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.domain.unit.dto.UnitDto;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import joeuncamp.dabombackend.domain.unit.repository.UnitJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAccessDeniedException;
import joeuncamp.dabombackend.global.error.exception.CAlreadyCreatorException;
import joeuncamp.dabombackend.global.error.exception.CNotCreatorException;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import joeuncamp.dabombackend.util.email.Email;
import joeuncamp.dabombackend.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatorAdminService {
    private final CreatorProfileJpaRepository creatorProfileJpaRepository;
    private final EmailService emailService;
    private final UnitJpaRepository unitJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    /**
     * 회원의 크리에이터 프로필을 활성화합니다.
     *
     * @param creatorId 회원 아이디넘버
     */
    public void activateCreator(Long creatorId) {
        CreatorProfile creator = creatorProfileJpaRepository.findById(creatorId).orElseThrow(CNotCreatorException::new);
        if (creator == null || creator.isActivated()) {
            throw new CAlreadyCreatorException();
        }
        creator.activate();
        creatorProfileJpaRepository.save(creator);
        emailService.sendMail(Email.creatorAcceptEmail(creator.getMember().getEmail(), creator.getMember().getMemberPrivacy().getName()));
    }

    /**
     * 대기 중인 크리에이터 목록을 조회합니다.
     *
     * @return 크리에이터 목록
     */
    public List<CreatorAdminDto.Response> getStandbyCreators() {
        List<CreatorProfile> creators = creatorProfileJpaRepository.findByActivatedIsFalse();
        return creators.stream()
                .map(CreatorAdminDto.Response::new)
                .toList();
    }

    /**
     * 전체 크리에이터 목록을 조회합니다.
     *
     * @return 크리에이터 목록
     */
    public List<CreatorAdminDto.Response> getCreators() {
        List<CreatorProfile> creators = creatorProfileJpaRepository.findAll();
        return creators.stream()
                .map(CreatorAdminDto.Response::new)
                .toList();
    }

    /**
     * 강의 재생을 위한 세부정보를 조회합니다.
     *
     * @param unitId 재생할 강의 아이디넘버
     * @return 강의 세부 정보
     */
    public UnitDto.Response playUnit(Long unitId) {
        Unit unit = unitJpaRepository.findById(unitId).orElseThrow(CResourceNotFoundException::new);
        return new UnitDto.Response(unit);
    }
}

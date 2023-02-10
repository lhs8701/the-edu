package joeuncamp.dabombackend.domain.admin.service;

import joeuncamp.dabombackend.domain.admin.dto.CreatorAdminDto;
import joeuncamp.dabombackend.domain.creator.entity.CreatorProfile;
import joeuncamp.dabombackend.domain.creator.repository.CreatorProfileJpaRepository;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CAlreadyCreatorException;
import joeuncamp.dabombackend.global.error.exception.CNotCreatorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatorAdminService {
    private final MemberJpaRepository memberJpaRepository;
    private final CreatorProfileJpaRepository creatorProfileJpaRepository;

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
}

package joeuncamp.dabombackend.domain.event.service;

import jakarta.transaction.Transactional;
import joeuncamp.dabombackend.domain.event.dto.EventDto;
import joeuncamp.dabombackend.domain.event.entity.Event;
import joeuncamp.dabombackend.domain.event.repository.EventJpaRepository;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.member.repository.MemberJpaRepository;
import joeuncamp.dabombackend.global.error.exception.CResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventJpaRepository eventJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    /**
     * 이벤트를 생성합니다.
     *
     * @param requestDto dto
     * @return 생성된 이벤트의 아이디넘버
     */
    public Long createEvent(EventDto.CreateRequest requestDto) {
        Member member = memberJpaRepository.findById(requestDto.getMemberId()).orElseThrow(CResourceNotFoundException::new);
        Event event = requestDto.toEntity(member);
        return eventJpaRepository.save(event).getId();
    }

    /**
     * 이벤트를 조회합니다.
     *
     * @param eventId 조회할 이벤트
     * @return 이벤트 정보
     */
    public EventDto.Response getEvent(Long eventId) {
        Event event = eventJpaRepository.findById(eventId).orElseThrow(CResourceNotFoundException::new);
        return new EventDto.Response(event);
    }

    /**
     * 진행 중인 이벤트 목록을 조회합니다.
     *
     * @return 진행 중인 이벤트 목록
     */
    public List<EventDto.ShortResponse> getOngoingEvents() {
        List<Event> events = eventJpaRepository.findByEndDateAfter(LocalDate.now());
        return events.stream()
                .map(EventDto.ShortResponse::new)
                .toList();
    }
}

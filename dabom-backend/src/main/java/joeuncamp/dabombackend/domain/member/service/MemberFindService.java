package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.Member;
import org.springframework.stereotype.Service;

@Service
public interface MemberFindService {

    Member findById(Long id);
}

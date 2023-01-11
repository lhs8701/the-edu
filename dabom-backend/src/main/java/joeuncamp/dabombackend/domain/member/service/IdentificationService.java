package joeuncamp.dabombackend.domain.member.service;

import joeuncamp.dabombackend.domain.member.entity.Member;
import org.springframework.stereotype.Service;

@Service
public class IdentificationService {
    public boolean isCreator(Member member) {
        return true;
    }
}

package joeuncamp.dabombackend.domain.player.view.service;

import joeuncamp.dabombackend.domain.player.view.repository.ViewRedisRepository;
import joeuncamp.dabombackend.domain.unit.dto.UnitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewService {
    private final ViewRedisRepository viewRedisRepository;

    public void saveView(){
    }
}

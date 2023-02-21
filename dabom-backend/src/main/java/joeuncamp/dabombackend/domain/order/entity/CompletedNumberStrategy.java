package joeuncamp.dabombackend.domain.order.entity;

import joeuncamp.dabombackend.domain.course.entity.Course;
import joeuncamp.dabombackend.domain.member.entity.Member;
import joeuncamp.dabombackend.domain.player.record.service.ViewChecker;
import joeuncamp.dabombackend.domain.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class CompletedNumberStrategy implements RefundStrategy {
    private final ViewChecker viewChecker;

    @Override
    public boolean isRefundable(Order order) {
        Member member = order.getMember();
        Ticket ticket = (Ticket) order.getItem();
        Course course = ticket.getCourse();
        viewChecker.getCompletedUnit(member, course);
        List<Unit> completedUnit = viewChecker.getCompletedUnit(member, course);
        return completedUnit.size() <= 2;
    }
}

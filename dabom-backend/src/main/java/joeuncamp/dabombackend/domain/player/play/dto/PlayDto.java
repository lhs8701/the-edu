package joeuncamp.dabombackend.domain.player.play.dto;

public class PlayDto {
    Long unitId;
    Long memberId;
    public static class PlayRequest{
        Long prevUnitId;
        Double playTime;
        Boolean completed;
    }
}

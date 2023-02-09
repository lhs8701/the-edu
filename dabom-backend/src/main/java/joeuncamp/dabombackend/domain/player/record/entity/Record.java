package joeuncamp.dabombackend.domain.player.record.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@RedisHash("record")
public class Record {
    @Id
    private Long id;

    @Indexed
    private Long memberId;

    @Indexed
    private Long courseId;

    @Indexed
    private Long unitId;

    private double time;

    @Indexed
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime recentTime;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", courseId=" + courseId +
                ", unitId=" + unitId +
                ", time=" + time +
                ", recentTime=" + recentTime +
                '}';
    }
}

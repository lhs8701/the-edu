package joeuncamp.dabombackend.domain.file.video.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MediaInfo {
    @Schema(description = "파일명")
    String fileName;
    @Schema(description = "포맷명")
    String formatName;
    @Schema(description = "영상 길이(초)")
    double duration;
    @Schema(description = "영상 크기")
    long size;
    @Schema(description = "비트레이트")
    long bitRate;
    @Schema(description = "가로 해상도")
    int width;
    @Schema(description = "세로 해상도")
    int height;

    public MediaInfo(FFmpegProbeResult probeResult) {
        this.fileName = probeResult.getFormat().filename;
        this.formatName = probeResult.getFormat().format_name;
        this.duration = probeResult.getFormat().duration;
        this.size = probeResult.getFormat().size;
        this.bitRate = probeResult.getStreams().get(0).bit_rate;
        this.width = probeResult.getStreams().get(0).width;
        this.height = probeResult.getStreams().get(0).height;
    }

    @Override
    public String toString() {
        return "MediaInfo{" +
                "fileName='" + fileName + '\'' +
                ", formatName='" + formatName + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", bitRate=" + bitRate +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}

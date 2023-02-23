package joeuncamp.dabombackend.util.hls.service;

import joeuncamp.dabombackend.domain.file.video.entity.MediaInfo;
import joeuncamp.dabombackend.domain.file.video.entity.VideoInfo;
import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class HlsConvertor {
    private final FFprobe ffprobe;
    private final FFmpeg ffmpeg;
    @Value("${path.videos}")
    String VIDEO_PATH;
    @Value("${path.root}")
    String ROOT_PATH;

    final String DELIMITER = "/";
    final static String M3U8_POSTFIX = "-m3u8";
    final static String M3U8_EXTENSION = ".m3u8";

    /**
     * m3u8 포맷으로 변환합니다.
     *
     * @param file 변환할 파일
     * @return 변환된 파일의 절대경로
     */
    public VideoInfo convertToM3u8(File file) {
        String fileName = file.getName();
        String onlyFileName = fileName.substring(0, fileName.lastIndexOf("."));
        String m3u8FilePath = VIDEO_PATH + DELIMITER + onlyFileName + M3U8_POSTFIX + DELIMITER + onlyFileName + M3U8_EXTENSION;
        makeDirectory(ROOT_PATH + VIDEO_PATH + DELIMITER + onlyFileName + M3U8_POSTFIX);
        String inputPath = file.getAbsolutePath();
        String outputPath = ROOT_PATH + m3u8FilePath;

        log.info("[input file information]");
        getMediaInfo(inputPath);

        executeConvertor(inputPath, outputPath);
        log.info("[output file information]");
        getMediaInfo(outputPath);
        return new VideoInfo(m3u8FilePath);
    }

    private void executeConvertor(String inputPath, String outputPath) {
        FFmpegBuilder builder = new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(inputPath)
                .addOutput(outputPath)
                .addExtraArgs("-profile:v", "baseline")
                .addExtraArgs("-level", "3.0")
                .addExtraArgs("-start_number", "0")
                .addExtraArgs("-hls_time", "1")
                .addExtraArgs("-hls_list_size", "0")
                .addExtraArgs("-f", "hls")
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
    }

    private void getMediaInfo(String filePath) {
        try {
            FFmpegProbeResult probeResult = ffprobe.probe(filePath);
            MediaInfo mediaInfo = new MediaInfo(probeResult);
            log.info(mediaInfo.toString());
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new CInternalServerException();
        }
    }

    private void makeDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}

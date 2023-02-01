package joeuncamp.dabombackend.util.hls.service;

import joeuncamp.dabombackend.global.error.exception.CInternalServerException;
import joeuncamp.dabombackend.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class HlsConvertor {
    private final FFprobe ffprobe;
    private final FFmpeg ffmpeg;
    @Value("${path.videos}")
    String VIDEO_STORAGE_PATH;

    final static String DELIMITER = "\\";
    final static String M3U8_POSTFIX = "-m3u8";
    final static String M3U8_EXTENSION = ".m3u8";

    /**
     * m3u8 포맷으로 변환합니다.
     * @param multipartFile multipartFile
     * @return 변환된 파일의 절대경로
     */
    public String convertToM3u8(MultipartFile multipartFile) {
        try {
            File originalFile = FileUtil.createFromMultipart(multipartFile);
            String fileName = originalFile.getName();
            String onlyFileName = fileName.substring(0, fileName.lastIndexOf("."));

            mkdirM3u8Directory(onlyFileName);
            String inputPath = originalFile.getAbsolutePath();
            String outputPath = VIDEO_STORAGE_PATH + DELIMITER + onlyFileName + M3U8_POSTFIX + DELIMITER + onlyFileName + M3U8_EXTENSION;

            log.info("[input file information]");
            getMediaInfo(inputPath);

            executeConvertor(inputPath, outputPath);
            log.info("[output file information]");
            getMediaInfo(outputPath);
            return outputPath;
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new CInternalServerException();
        }
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
            log.info("--Media Info--");
            log.info("파일명: {}", probeResult.getFormat().filename); //파일명
            log.info("포맷명: {}", probeResult.getFormat().format_name);//포맷명
            log.info("영상 길이(초): {}", probeResult.getFormat().duration);//길이
            log.info("영상 크기: {}", probeResult.getFormat().size);//크기
            log.info("비트레이트: {}", probeResult.getStreams().get(0).bit_rate); //비트레이트
            log.info("가로해상도: {}", probeResult.getStreams().get(0).width);//가로해상도
            log.info("세로해상도: {}", probeResult.getStreams().get(0).height);//세로해상도
            log.info("코덱 이름: {}", probeResult.getStreams().get(0).codec_name); //코덱 이름
            log.info("코덱 타입: {}", probeResult.getStreams().get(0).codec_type.name()); //코덱 타입(비디오/오디오)
            log.info("--");
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new CInternalServerException();
        }
    }

    private void mkdirM3u8Directory(String onlyFileName) {
        File tsPath = new File(VIDEO_STORAGE_PATH + DELIMITER + onlyFileName + M3U8_POSTFIX);
        if (!tsPath.exists()) {
            tsPath.mkdir();
        }
    }
}

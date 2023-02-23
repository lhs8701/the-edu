package joeuncamp.dabombackend.global.config;

import im.toss.cert.sdk.TossCertSessionGenerator;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@Configuration
public class AppConfig {
    @Value("${hls.ffmpeg}")
    private String FFMPEG_PATH;

    @Value("${hls.ffprobe}")
    private String FFPROBE_PATH;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public Tika tika(){
        return new Tika();
    }

    @Bean
    public TossCertSessionGenerator tossCertSessionGenerator(){
        return new TossCertSessionGenerator();
    }

    @Bean
    public FFmpeg ffmpeg() throws IOException {
        return new FFmpeg(FFMPEG_PATH);
    }

    @Bean
    public FFprobe ffprobe() throws IOException{
        return new FFprobe(FFPROBE_PATH);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/message/**")
                        .allowedOrigins("*")
                        .allowedMethods(HttpMethod.POST.name())
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }
}

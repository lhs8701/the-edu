package joeuncamp.dabombackend.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "DABOM API 명세서",
                description = "온라인 강의 플랫폼 DABOM의 API 명세서입니다.",
                version = "0.0.1",
                contact = @Contact(
                        name = "hansol",
                        email = "hansol8701@naver.com"
                )
        )
)
@Configuration
public class SpringDocConfig {

}

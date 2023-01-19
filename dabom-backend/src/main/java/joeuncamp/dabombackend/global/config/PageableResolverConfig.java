package joeuncamp.dabombackend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class PageableResolverConfig implements WebMvcConfigurer {
    private final CustomPageableArgumentResolver customPageableArgumentResolver;
    public PageableResolverConfig(CustomPageableArgumentResolver customPageableArgumentResolver){
        this.customPageableArgumentResolver = customPageableArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customPageableArgumentResolver);
    }
}

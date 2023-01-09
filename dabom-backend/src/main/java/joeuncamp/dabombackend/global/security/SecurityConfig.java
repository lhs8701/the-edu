package joeuncamp.dabombackend.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(securedEnabled = true) //@PreAuthorize, @Secured 사용을 위함
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // configure HTTP security...
        return http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .authenticationProvider(customAuthenticationProvider)
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // configure Web security...
        return (web) -> web
                .ignoring()
                .requestMatchers("/api/usage",
                        "/v3/api-docs",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // js/css/image 파일 등 보안 필터가 필요없는 리소스 지정
    }
}

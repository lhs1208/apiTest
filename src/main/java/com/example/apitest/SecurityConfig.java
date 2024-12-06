package com.example.apitest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${allowed.ip}") // application.properties에서 허용 IP 읽기
    private String allowedIp;

    private final RateLimitingFilter rateLimitingFilter;
    private final LoggingFilter loggingFilter;

    public SecurityConfig(RateLimitingFilter rateLimitingFilter, LoggingFilter loggingFilter) {
        this.rateLimitingFilter = rateLimitingFilter;
        this.loggingFilter = loggingFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().access((authentication, context) -> {
                            String clientIp = context.getRequest().getRemoteAddr();
                            boolean isAllowed = clientIp.equals(allowedIp);

                            // IP 로그 찍기
                            System.out.println("클라이언트 IP: " + clientIp);
                            System.out.println("허용 여부: " + isAllowed);

                            // AuthorizationDecision으로 반환
                            return new AuthorizationDecision(isAllowed);
                        })
                )
                // 요청 필터 추가 (RateLimitingFilter, LoggingFilter)
                .addFilterBefore(loggingFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(rateLimitingFilter, LoggingFilter.class); // LoggingFilter 뒤에 RateLimitingFilter 추가

        return http.build();
    }
}
package com.example.apitest;

import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter implements Filter {

    private static final int LIMIT = 10; // 초당 최대 요청 수
    private final ConcurrentHashMap<String, RequestTracker> requestCounts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = httpRequest.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        requestCounts.putIfAbsent(clientIp, new RequestTracker(currentTime, 0));

        synchronized (requestCounts.get(clientIp)) {
            RequestTracker tracker = requestCounts.get(clientIp);

            // 1초가 지났으면 카운트 초기화
            if (currentTime - tracker.timestamp > 1000) {
                tracker.timestamp = currentTime;
                tracker.count = 0;
            }

            // 요청 수를 로그로 출력
            System.out.println("클라이언트 IP: " + clientIp + ", 현재 요청 수: " + tracker.count);

            // 요청 제한 초과 시 HTTP 429 반환
            if (tracker.count >= LIMIT) {
                System.out.println("요청 제한 초과: 클라이언트 IP " + clientIp);
                httpResponse.setStatus(429); // HTTP 429 상태 코드
                httpResponse.getWriter().write("Rate limit exceeded. Try again later.");
                return;
            }

            // 요청 카운트 증가
            tracker.count++;
        }

        // 다음 필터 체인 실행
        chain.doFilter(request, response);
    }

    // 요청 추적 클래스
    private static class RequestTracker {
        long timestamp; // 마지막 요청 시간
        int count; // 요청 횟수

        RequestTracker(long timestamp, int count) {
            this.timestamp = timestamp;
            this.count = count;
        }
    }
}
package com.example.apitest;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 요청 시작 시간
        long startTime = System.currentTimeMillis();

        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();

        // 요청 처리
        chain.doFilter(request, response);

        // 요청 종료 시간
        long endTime = System.currentTimeMillis();

        // 걸린 시간 계산
        long duration = endTime - startTime;

        // 로그 메시지 작성
        String logMessage = formatLogMessage(method, uri, startTime, endTime, duration);

        // 로그 파일에 저장
        writeLogToFile(logMessage);
    }

    private String formatLogMessage(String method, String uri, long startTime, long endTime, long duration) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeFormatted = sdf.format(new Date(startTime));
        String endTimeFormatted = sdf.format(new Date(endTime));
        return String.format("[%s]-INFO-[%s] Request: %s %s | Duration: %d ms", startTimeFormatted, method, uri, endTimeFormatted, duration);
    }

    private void writeLogToFile(String logMessage) {
        String logFileName = String.format("ApiProcess_%s.log", new SimpleDateFormat("yyyyMMddHH").format(new Date()));
        try (FileWriter writer = new FileWriter(logFileName, true)) {
            writer.write(logMessage + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

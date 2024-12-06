package com.example.apitest;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

    private final CsvReaderService csvReaderService;
    private final TransactionService transactionService;

    public ScheduledTasks(CsvReaderService csvReaderService, TransactionService transactionService) {
        this.csvReaderService = csvReaderService;
        this.transactionService = transactionService;
    }

    // 매일 자정에 실행 (0시 0분)
    @Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(cron = "*/5 * * * * ?")
    public void processCsvAtMidnight() {
        String filePath = "D:/data/sample.csv"; // 처리할 CSV 파일 경로
        List<Transaction> transactions = csvReaderService.readCsv(filePath);

        if (!transactions.isEmpty()) {
            transactionService.saveAll(transactions);
            System.out.println("CSV 데이터가 성공적으로 저장되었습니다.");
        } else {
            System.out.println("CSV 파일이 비어있거나 읽을 수 없습니다.");
        }
    }
}
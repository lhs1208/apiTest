package com.example.apitest;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService {

    public List<Transaction> readCsv(String filePath) {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] fields = line.split("\\|");

                // 유효성 검증: 필드 개수와 데이터 유형 확인
                if (fields.length != 6) { // 필드 수가 6개가 아니면 건너뜀
                    System.err.println("유효하지 않은 데이터 형식 (라인: " + lineNumber + "): " + line);
                    continue;
                }

                try {
                    Transaction transaction = new Transaction();
                    transaction.setTime(fields[0]);
                    transaction.setSubscribers(Integer.parseInt(fields[1]));
                    transaction.setUnsubscribers(Integer.parseInt(fields[2]));
                    transaction.setPaymentAmount(fields[3]);
                    transaction.setUsageAmount(fields[4]);
                    transaction.setSalesAmount(fields[5]);

                    transactions.add(transaction);
                } catch (NumberFormatException e) {
                    System.err.println("데이터 파싱 오류 (라인: " + lineNumber + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("CSV 파일 읽기 오류", e);
        }

        return transactions;
    }


    private Transaction parseLine(String line) throws InvalidCsvException {
        String[] tokens = line.split(",");
        if (tokens.length != 6) {
            throw new InvalidCsvException("Invalid CSV format: Expected 6 fields but found " + tokens.length);
        }

        try {
            Transaction transaction = new Transaction();
            transaction.setTime(tokens[0]);
            transaction.setSubscribers(Integer.parseInt(tokens[1]));
            transaction.setUnsubscribers(Integer.parseInt(tokens[2]));
            transaction.setPaymentAmount(tokens[3]);
            transaction.setUsageAmount(tokens[4]);
            transaction.setSalesAmount(tokens[5]);
            return transaction;
        } catch (NumberFormatException e) {
            throw new InvalidCsvException("Invalid number format in line: " + line);
        }
    }
}
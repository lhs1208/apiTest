package com.example.apitest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class HelloWorldController {

    private final CsvReaderService csvReaderService;
    private final TransactionService transactionService;

    public HelloWorldController(CsvReaderService csvReaderService, TransactionService transactionService) {
        this.csvReaderService = csvReaderService;
        this.transactionService = transactionService;
    }

    // CSV 파일에서 데이터를 읽고 데이터베이스에 저장
    @PostMapping("/upload")
    public String uploadCsvToDatabase(@RequestParam String filePath) {
        List<Transaction> transactions = csvReaderService.readCsv(filePath);
        transactionService.saveAll(transactions);
        System.out.println("CSV 데이터가 성공적으로 데이터베이스에 저장되었습니다!");
        return "CSV 데이터가 성공적으로 데이터베이스에 저장되었습니다!";
    }

    // 데이터베이스에서 모든 데이터 조회
    @GetMapping
    public List<Transaction> getAllTransactions() {
        System.out.println("조회 완료");
        return transactionService.findAll();
    }

    // 특정 ID의 데이터 수정
    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction updatedTransaction) {
        System.out.println("수정 완료");
        return transactionService.update(id, updatedTransaction);
    }

    // 특정 ID의 데이터 삭제
    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        System.out.println("ID " + id + "의 데이터가 성공적으로 삭제되었습니다.");
        return "ID " + id + "의 데이터가 성공적으로 삭제되었습니다.";
    }
}
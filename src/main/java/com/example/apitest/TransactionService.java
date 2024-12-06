package com.example.apitest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // 모든 트랜잭션 저장
    public void saveAll(List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }

    // 모든 트랜잭션 조회
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction update(Long id, Transaction updatedTransaction) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setTime(updatedTransaction.getTime());
                    transaction.setSubscribers(updatedTransaction.getSubscribers());
                    transaction.setUnsubscribers(updatedTransaction.getUnsubscribers());
                    transaction.setPaymentAmount(updatedTransaction.getPaymentAmount());
                    transaction.setUsageAmount(updatedTransaction.getUsageAmount());
                    transaction.setSalesAmount(updatedTransaction.getSalesAmount());
                    return transactionRepository.save(transaction);
                })
                .orElseThrow(() -> new RuntimeException("ID " + id + "에 해당하는 데이터가 존재하지 않습니다."));
    }

    public void delete(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
        } else {
            throw new RuntimeException("ID " + id + "에 해당하는 데이터가 존재하지 않습니다.");
        }
    }
}
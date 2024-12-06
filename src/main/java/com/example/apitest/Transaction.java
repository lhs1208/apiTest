package com.example.apitest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성
    private Long id;
    private String time;          // 시간A
    private int subscribers;      // 가입자 수
    private int unsubscribers;    // 탈퇴자 수
    private String paymentAmount; // 결제 금액
    private String usageAmount;   // 사용 금액
    private String salesAmount;   // 매출 금액

    public Transaction(){}

    public Transaction(String time, int subscribers, int unsubscribers, String paymentAmount, String usageAmount, String salesAmount) {
        this.time = time;
        this.subscribers = subscribers;
        this.unsubscribers = unsubscribers;
        this.paymentAmount = paymentAmount;
        this.usageAmount = usageAmount;
        this.salesAmount = salesAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public int getUnsubscribers() {
        return unsubscribers;
    }

    public void setUnsubscribers(int unsubscribers) {
        this.unsubscribers = unsubscribers;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getUsageAmount() {
        return usageAmount;
    }

    public void setUsageAmount(String usageAmount) {
        this.usageAmount = usageAmount;
    }

    public String getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(String salesAmount) {
        this.salesAmount = salesAmount;
    }
}

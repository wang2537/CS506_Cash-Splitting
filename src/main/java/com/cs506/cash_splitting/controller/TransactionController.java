package com.cs506.cash_splitting.controller;
import com.cs506.cash_splitting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cs506.cash_splitting.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/balance/{uid}")
    public Object get_total_balance(@PathVariable int uid) {
        return transactionService.getTotalBalance(uid);
    }

    @PostMapping("/transaction/individual")
    public Object add(@RequestBody Transaction transaction) {
        BigDecimal bg = new BigDecimal(transaction.getAmount());
        transaction.setAmount(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        return transactionService.createTransaction(transaction);
    }

    @PostMapping("/balance")
    public Object get_balance(@RequestBody Map<String, Integer> map) {
        return transactionService.getBalance(map.get("source"), map.get("destination"));
    }

    @GetMapping("/transaction/{uid}")
    public Object get_transaction(@PathVariable int uid) {
        return transactionService.getTransaction(uid);
    }


}

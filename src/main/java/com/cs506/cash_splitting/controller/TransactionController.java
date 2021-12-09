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
import java.util.List;
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

    @GetMapping("/transaction/user/{uid}")
    public Object get_transaction(@PathVariable int uid) {
        return transactionService.getTransaction(uid);
    }

    @GetMapping("/transaction/{tid}")
    public Object update_one_transaction(@PathVariable int tid) {
        return transactionService.updateOneTransaction(tid);
    }

    @GetMapping("/transaction/reminder/{uid}")
    public Object get_reminder(@PathVariable int uid) {
        return transactionService.getReminder(uid);
    }

    @PostMapping("/transaction/batch")
    public Object batch_transaction(@RequestBody Map<String, List<Object>> map) {
        int destination_size = map.get("destination").size();
        for(int i = 0; i < destination_size; i++){
            Transaction transaction = new Transaction();
            double amount = (double) map.get("amount").get(i);
            BigDecimal bg = new BigDecimal(amount);
            transaction.setCurrency((String) map.get("currency").get(0));
            transaction.setComment((String) map.get("comment").get(0));
            transaction.setAmount(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            transaction.setDestination((Integer) map.get("destination").get(i));
            transaction.setSource((Integer) map.get("source").get(0));
            transaction.setGid((Integer) map.get("gid").get(0));
            transactionService.createTransaction(transaction);
        }
        return true;
    }

    @PostMapping("/transaction/settleAll")
    public Object settle_all(@RequestBody Map<String, Object> map) {
        return transactionService.settleAll((int)map.get("source"),(int)map.get("destination"), (String)map.get("currency"));
    }
}

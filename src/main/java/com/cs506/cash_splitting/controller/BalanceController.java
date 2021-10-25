package com.cs506.cash_splitting.controller;

import com.cs506.cash_splitting.model.Balance;
import com.cs506.cash_splitting.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/transaction")
    public Object get() {
        return balanceService.get();
    }

    @GetMapping("/transaction/{username}")
    public Object getRenterBalance(@PathVariable String username) {
        return balanceService.getRenterBalance(username);
    }

    @PostMapping("/transaction")
    public boolean changeBalance(@RequestBody Balance balance) {
        return balanceService.changeBalance(balance);
    }

}

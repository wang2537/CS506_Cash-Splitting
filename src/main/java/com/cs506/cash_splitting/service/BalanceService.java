package com.cs506.cash_splitting.service;
import com.cs506.cash_splitting.model.Balance;

public interface BalanceService {
    Object get();
    Object getRenterBalance(String renterName);
    boolean changeBalance(Balance balance);
}

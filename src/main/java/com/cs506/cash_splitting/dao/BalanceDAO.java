package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.Balance;

public interface BalanceDAO {
    Object get();
    Object getRenterBalance(String renterName);
    boolean change_pay(Balance balance);
}

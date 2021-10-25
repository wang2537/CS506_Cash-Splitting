package com.cs506.cash_splitting.service;


import com.cs506.cash_splitting.dao.BalanceDAO;
import com.cs506.cash_splitting.model.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BalanceServiceImpI implements BalanceService{

    @Autowired
    private BalanceDAO balanceDAO;

    @Transactional
    @Override
    public Object get() {
        return balanceDAO.get();
    }

    @Transactional
    @Override
    public Object getRenterBalance(String status) {
        return balanceDAO.getRenterBalance(status);
    }

    @Transactional
    @Override
    public boolean changeBalance(Balance balance) {
        return balanceDAO.change_pay(balance);
    }

}

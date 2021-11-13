package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.dao.TransactionDAO;
import com.cs506.cash_splitting.dao.UserDAO;
import com.cs506.cash_splitting.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionDAO transactionDAO;

    @Transactional
    @Override
    public Object getTotalBalance(int uid) {
        return transactionDAO.getTotalBalance(uid);
    }

    @Transactional
    @Override
    public Object createTransaction(Transaction transaction) {
        return transactionDAO.createTransaction(transaction);
    }

    @Override
    public Object getBalance(int src_id, int des_id) {
        return transactionDAO.getBalance(src_id, des_id);
    }

    @Override
    public Object getTransaction(int uid) {
        return transactionDAO.getTransaction(uid);
    }

    @Transactional
    @Override
    public Object updateOneTransaction(int tid) {
        return transactionDAO.updateOneTransaction(tid);
    }
}

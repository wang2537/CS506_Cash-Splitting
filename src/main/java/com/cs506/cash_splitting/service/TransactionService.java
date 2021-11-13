package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.model.Transaction;

public interface TransactionService {
    Object getTotalBalance(int uid);
    Object createTransaction(Transaction transaction);
    Object getBalance(int src_id, int des_id);
    Object getTransaction(int uid);
    Object updateOneTransaction(int tid);

}

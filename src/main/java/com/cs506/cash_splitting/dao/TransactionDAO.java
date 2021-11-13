package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.Transaction;

public interface TransactionDAO {
    Object getTotalBalance(int uid);
    Object createTransaction(Transaction transaction);
    Object getBalance(int source_id, int destination_id);
    double double_two(double number);
    Object getTransaction(int uid);
    Object updateOneTransaction(int tid);
}

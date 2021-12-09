package com.cs506.cash_splitting.model;

import java.util.List;

public class ReminderList {

    private int source;
    private int destination;
    private int rid;
    private List<Transaction> transactionList;

    public ReminderList(int rid, int source, int destination, List<Transaction> transactionList) {
        this.rid = rid;
        this.source = source;
        this.destination = destination;
        this.transactionList = transactionList;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getRid() {
        return this.rid;
    }

    public int getSource() {
        return source;
    }

}

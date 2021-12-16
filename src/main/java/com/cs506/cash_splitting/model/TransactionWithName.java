package com.cs506.cash_splitting.model;

public class TransactionWithName {


    private int tid;

    private int source;

    private int destination;

    private String sourceName;

    private String destinationName;

    private String currency = "USD";

    private String status = "unpaid";

    private double amount;

    private String comment = "Transaction";

    private int gid = -1;

    private String create_time = FriendChat.getStringToday();

    public TransactionWithName(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.destination = transaction.getDestination();
        this.source = transaction.getSource();
        this.status = transaction.getStatus();
        this.create_time = transaction.getCreate_time();
        this.currency = transaction.getCurrency();
        this.gid = transaction.getGid();
        this.comment = transaction.getComment();
        this.tid = transaction.getTid();
    }


    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

}

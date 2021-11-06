package com.cs506.cash_splitting.model;


public class FriendAppWithName {
    private int aid;


    private int source;

    private String sourceName;

    private int destination;

    private String destinationName;


    private String status;


    private String modify_time;

    public FriendAppWithName(FriendApp friendApp) {
        this.aid = friendApp.getAid();
        this.destination = friendApp.getDestination();
        this.source = friendApp.getSource();
        this.status = friendApp.getStatus();
        this.modify_time = friendApp.getModify_time();
        this.sourceName = "";
        this.destinationName = "";
    }


    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
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

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationName() {
        return destinationName;
    }

}

package com.cs506.cash_splitting.model;

import java.util.List;

public class FriendList {
    private User user;
    private List<User> friendList;

    public User getUser() {
        return user;
    }

    public FriendList() {

    }

    public FriendList(User user, List<User> friendList) {
        this.user = user;
        this.friendList = friendList;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }

}

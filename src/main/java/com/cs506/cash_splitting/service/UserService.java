package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.model.*;

public interface UserService {
//    Object get();
    Object get(String username);
    String getUserName(int uid);
    boolean addOrUpdateUser(User user);
    boolean addOrUpdatePassword(Password password);
    int getUid(String username);
    boolean login(String username, String password);

    boolean createGroup(Group group);
    boolean addMember(int gid, int uid);
    boolean quitGroup(int gid, int uid);
    boolean changeGroupname(int gid, String newGroupName);
    Object getGroupname(int uid);
    Object getGroupMember(int gid);

    Object sendFriendRequest(FriendApp friendApp);
    Object getFriendRequest(int uid);
    Object updateFriendApp(FriendApp friendApp);
    Object updateFriend(Friend friend);
    Object getFriend(int uid);

    Object sendReminder(Reminder reminder);
    boolean updateReminder(int rid);
    Object getReminder(int destination);
}

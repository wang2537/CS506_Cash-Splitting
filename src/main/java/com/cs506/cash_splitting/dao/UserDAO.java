package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.*;
import java.util.List;

public interface UserDAO {
//    Object get();
    Object get(String username);
    boolean addOrUpdateUser(User user);
    boolean addOrUpdatePassword(Password password);
    String getUserName (int uid);
    int get_uid(String username);
    List<?> check(String username, String password);

    boolean createGroup(Group group);
    boolean addMember(int gid, int uid);
    boolean quitGroup(int gid, int uid);
    boolean changeGroupname(int gid, String newGroupName);
    Object getGroupname(int uid);

    boolean sendFriendRequest(FriendApp friendApp);
    Object getFriendRequest(int uid);
    Object updateFriendApp(FriendApp friendApp);
    Object updateFriend(Friend friend);
    Object getFriend(int uid);

    Object sendReminder(Reminder reminder);
    boolean updateReminder(int rid);
    Object getReminder(int destination);
}

package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.dao.UserDAO;
import com.cs506.cash_splitting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userdao;

//    @Transactional
//    @Override
//    public Object get() {
//        return userdao.get();
//    }

    @Transactional
    @Override
    public Object get(String username) {
        return userdao.get(username);
    }

    @Override
    public String getUserName(int uid) {
        return userdao.getUserName(uid);
    }

    @Override
    public int getUid(String username) {
        return userdao.get_uid(username);
    }

    @Transactional
    @Override
    public boolean addOrUpdateUser(User user) {
        return userdao.addOrUpdateUser(user);
    }

    @Transactional
    @Override
    public boolean addOrUpdatePassword(Password password) {
        return userdao.addOrUpdatePassword(password);
    }
    @Override
    public boolean login(String username, String password) {
        return userdao.check(username, password).isEmpty();
    }

    @Transactional
    @Override
    public boolean createGroup(Group group) {return userdao.createGroup(group);}

    @Transactional
    @Override
    public boolean addMember(int gid, int uid) {return userdao.addMember(gid, uid);}

    @Transactional
    @Override
    public boolean quitGroup(int gid, int uid) {return userdao.quitGroup(gid, uid);}

    @Transactional
    @Override
    public boolean changeGroupname(int gid, String newGroupName) {return userdao.changeGroupname(gid, newGroupName);}

    @Override
    public Object getGroupname(int uid){ return userdao.getGroupname(uid);}

    @Override
    public Object getGroupMember(int gid) {
        return userdao.getGroupMember(gid);
    }

    @Transactional
    @Override
    public Object sendFriendRequest(FriendApp friendApp) {
        return userdao.sendFriendRequest(friendApp);
    }

    @Override
    public Object getFriendRequest(int uid) {
        return userdao.getFriendRequest(uid);
    }

    @Transactional
    @Override
    public Object updateFriendApp(FriendApp friendApp) {
        return userdao.updateFriendApp(friendApp);
    }

    @Transactional
    @Override
    public Object updateFriend(Friend friend) {
        return userdao.updateFriend(friend);
    }

    @Override
    public Object getFriend(int uid) {
        return userdao.getFriend(uid);
    }

    @Transactional
    @Override
    public Object sendReminder(Reminder reminder){ return userdao.sendReminder(reminder);}

    @Transactional
    @Override
    public boolean updateReminder(int rid){
        return userdao.updateReminder(rid);
    }

    @Override
    public Object getReminder(int destination){
        return userdao.getReminder(destination);
    }
}

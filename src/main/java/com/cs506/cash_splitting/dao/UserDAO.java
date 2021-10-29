package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.Group;
import com.cs506.cash_splitting.model.Password;
import com.cs506.cash_splitting.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserDAO {
    Object get();
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
}

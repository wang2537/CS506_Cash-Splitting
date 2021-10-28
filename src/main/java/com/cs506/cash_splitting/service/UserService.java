package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.model.Password;
import com.cs506.cash_splitting.model.User;

public interface UserService {
    Object get();
    Object get(String username);
    String getUserName(int uid);
    boolean addOrUpdateUser(User user);
    boolean addOrUpdatePassword(Password password);
    int getUid(String username);
    boolean login(String username, String password);
}

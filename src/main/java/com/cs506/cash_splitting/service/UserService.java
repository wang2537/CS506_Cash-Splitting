package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.model.User;

public interface UserService {
    Object get();
    Object get(String username);
    String getUserName(int uid);
    boolean addOrUpdate(User user);
    boolean login(String username, String password);
}

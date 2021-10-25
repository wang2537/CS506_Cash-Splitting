package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.User;

import java.util.List;

public interface UserDAO {
    Object get();
    Object get(String username);
    boolean addOrUpdate(User user);
    String getUserName (int uid);
    List<?> check(String username, String password);

}

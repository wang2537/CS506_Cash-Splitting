package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.Application;

public interface ApplicationDAO {
    Object get();
    Object get(String username);
    boolean update(Application application);
    boolean create(Application application);
}

package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.model.Application;

public interface ApplicationService {
    Object get();
    Object get(String username);
    boolean update(Application application);
    boolean create(Application application);
}

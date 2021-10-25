package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.model.Application;
import com.cs506.cash_splitting.model.Property;

public interface PropertyService {
    Object get();
    Object get(String username);
    Object get_info();
    boolean checkin(Application application);
    boolean add(Property property);
}

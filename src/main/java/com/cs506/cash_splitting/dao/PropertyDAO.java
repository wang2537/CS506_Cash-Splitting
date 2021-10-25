package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.Application;
import com.cs506.cash_splitting.model.Property;

public interface PropertyDAO {
    Object get();
    Object get(String username);
    Object get_info();
    boolean checkin(Application application);
    boolean add(Property property);

}

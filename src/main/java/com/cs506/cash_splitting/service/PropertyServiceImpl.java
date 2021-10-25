package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.dao.PropertyDAO;
import com.cs506.cash_splitting.model.Application;
import com.cs506.cash_splitting.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropertyServiceImpl implements PropertyService{

    @Autowired
    private PropertyDAO propertyDAO;

    @Transactional
    @Override
    public Object get() {
        return propertyDAO.get();
    }

    @Transactional
    @Override
    public Object get(String username) {
        return propertyDAO.get(username);
    }

    @Transactional
    @Override
    public Object get_info() {
        return propertyDAO.get_info();
    }

    @Transactional
    @Override
    public boolean checkin(Application application) {
        return propertyDAO.checkin(application);
    }

    @Transactional
    @Override
    public boolean add(Property property) {
        return propertyDAO.add(property);
    }
}

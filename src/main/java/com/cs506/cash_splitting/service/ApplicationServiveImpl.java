package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.dao.ApplicationDAO;
import com.cs506.cash_splitting.dao.UserDAO;
import com.cs506.cash_splitting.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationServiveImpl implements ApplicationService{

    @Autowired
    private ApplicationDAO applicationDAO;

    @Autowired
    private UserDAO userdao;

    @Transactional
    @Override
    public Object get() {
        return applicationDAO.get();
    }

    @Transactional
    @Override
    public Object get(String username) {
        return applicationDAO.get(username);
    }

    @Transactional
    @Override
    public boolean update(Application application) {
        return applicationDAO.update(application);
    }

    @Transactional
    @Override
    public boolean create(Application application) {

        return applicationDAO.create(application);
    }
}

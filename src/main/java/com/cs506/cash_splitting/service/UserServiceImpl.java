package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.dao.UserDAO;
import com.cs506.cash_splitting.model.Password;
import com.cs506.cash_splitting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userdao;

    @Transactional
    @Override
    public Object get() {
        return userdao.get();
    }

    @Transactional
    @Override
    public Object get(String username) {
        return userdao.get(username);
    }

    @Override
    public String getUserName(int uid) {
        return userdao.getUserName(uid);
    }

    @Override
    public int getUid(String username) {
        return userdao.get_uid(username);
    }

    @Transactional
    @Override
    public boolean addOrUpdateUser(User user) {
        return userdao.addOrUpdateUser(user);
    }

    @Transactional
    @Override
    public boolean addOrUpdatePassword(Password password) {
        return userdao.addOrUpdatePassword(password);
    }
    @Override
    public boolean login(String username, String password) {
        return userdao.check(username, password).isEmpty();
    }
}

package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.GroupChat;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;

@Repository
public class ChatDAOImpl implements ChatDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public boolean sendGroupMessage(@RequestBody GroupChat groupChat){
        Session currSession = entityManager.unwrap(Session.class);
        currSession.saveOrUpdate(groupChat);
        return true;
    }

}

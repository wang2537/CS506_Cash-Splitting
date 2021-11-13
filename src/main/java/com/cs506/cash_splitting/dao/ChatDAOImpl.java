package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.FriendChat;
import com.cs506.cash_splitting.model.GroupChat;
import com.cs506.cash_splitting.model.User;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean sendFriendMessage(FriendChat friendChat) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.saveOrUpdate(friendChat);
        return true;
    }

    @ResponseBody
    @Override
    public Object getFriendMessages(int uid, int friendId) {
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.createSQLQuery("SELECT * FROM friend_chatdb WHERE " +
                "(source = :source1 AND destination = :destination1) OR " +
                "(source = :source2 AND destination = :destination2) ORDER BY sendtime DESC").addEntity(FriendChat.class);
        query.setParameter("source1", uid);
        query.setParameter("destination1", friendId);
        query.setParameter("source2", friendId);
        query.setParameter("destination2", uid);
        List list = query.list();
        List<FriendChat> friendChatList = new ArrayList<>();
        for (Object o : list){
            friendChatList.add((FriendChat) o);
        }
        return friendChatList;
    }

}

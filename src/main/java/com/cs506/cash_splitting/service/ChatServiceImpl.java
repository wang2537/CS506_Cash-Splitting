package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.dao.ChatDAO;
import com.cs506.cash_splitting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatDAO chatdao;

    @Transactional
    @Override
    public boolean sendGroupMessage(GroupChat groupChat) {
        return chatdao.sendGroupMessage(groupChat);
    }

    @Transactional
    @Override
    public boolean sendFriendMessage(FriendChat friendChat) {
        return chatdao.sendFriendMessage(friendChat);
    }

    @Override
    public Object getFriendMessages(int uid, int friendId) {
        return chatdao.getFriendMessages(uid, friendId);
    }

    @Override
    public Object getGroupMessage(int gid) {
        return chatdao.getGroupMessage(gid);
    }
}

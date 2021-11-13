package com.cs506.cash_splitting;

import com.cs506.cash_splitting.dao.*;
import com.cs506.cash_splitting.model.*;
import com.cs506.cash_splitting.service.*;
import com.cs506.cash_splitting.controller.*;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChatTest extends CashApplicationTests{
    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatController chatController;

    private FriendChat friendChatTure;
    private FriendChat friendChatFalse;


    @Override
    @Before
    public void init() {
        super.init();
        friendChatTure = new FriendChat();
        friendChatFalse = new FriendChat();
        friendChatTure.setSource(4);
        friendChatTure.setDestination(7);
        friendChatTure.setContent("test message");
        friendChatTure.setSendtime("2111-11-11 11:11:11");
        friendChatFalse.setSource(1);
        friendChatFalse.setDestination(2);
        friendChatFalse.setContent("wrong");
        friendChatFalse.setSendtime("2111-11-11 11:11:11");
    }

    @Test
    @Transactional
    public void testSendMessageToFriend() {
        Assertions.assertTrue(chatController.sendFriendMessage(friendChatTure));
        Assertions.assertFalse(chatController.sendFriendMessage(friendChatFalse));
    }


    @Test
    @Transactional
    public void testGetFriendMessages() {
        Assertions.assertTrue(chatController.sendFriendMessage(friendChatTure));
        ArrayList<FriendChat> messageList = (ArrayList<FriendChat>) chatService.getFriendMessages(4, 7);
        FriendChat friendChat = messageList.get(0);
        Assertions.assertEquals(friendChat.getSource(), 4);
        Assertions.assertEquals(friendChat.getDestination(), 7);
        Assertions.assertEquals(friendChat.getContent(), "test message");
        Assertions.assertEquals(friendChat.getSendtime(), "2111-11-11 11:11:11");
        Assertions.assertEquals(messageList.size(), 9);
    }

    @Override
    @After
    public void after() {
        System.out.println("Chat Test finished");
        super.after();
    }



}

package com.cs506.cash_splitting;

import com.cs506.cash_splitting.model.*;
import com.cs506.cash_splitting.service.*;
import com.cs506.cash_splitting.controller.*;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChatTest extends CashApplicationTests{
    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatController chatController;

    private FriendChat friendChatTure;
    private FriendChat friendChatFalse;
    private GroupChat groupChat;
    private GroupChat nonExistGroup;

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

        groupChat = new GroupChat();
        groupChat.setContent("for test");
        groupChat.setGid(1);
        groupChat.setUid(1);

        nonExistGroup = new GroupChat();
        nonExistGroup.setUid(2);
        nonExistGroup.setGid(10);
    }

    @Test
    @Transactional
    public void testSendGroupMessage(){
        Assertions.assertTrue(chatController.sendGroupMessage(groupChat));
        Assertions.assertFalse(chatController.sendGroupMessage(nonExistGroup));
    }

    @Test
    @Transactional
    public void testGetGroupMeassge(){
        Assertions.assertTrue(chatController.sendGroupMessage(groupChat));
        ArrayList<GroupChat> messageList = (ArrayList<GroupChat>) chatService.getGroupMessage(1);
        GroupChat groupChat = messageList.get(0);
        Assertions.assertEquals(groupChat.getGid(), 1);
        Assertions.assertEquals(groupChat.getUid(), 1);
        Assertions.assertEquals(groupChat.getContent(), "for test");
        Assertions.assertEquals(messageList.size(), 3);
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

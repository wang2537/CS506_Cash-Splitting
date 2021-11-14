package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.model.*;

public interface ChatService {

    boolean sendGroupMessage(GroupChat groupChat);
    boolean sendFriendMessage(FriendChat friendChat);
    Object getFriendMessages(int uid, int friendId);
    Object getGroupMessage(int gid);
}

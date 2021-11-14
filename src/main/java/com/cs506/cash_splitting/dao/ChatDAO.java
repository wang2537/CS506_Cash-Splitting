package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.*;

public interface ChatDAO {

    boolean sendGroupMessage(GroupChat groupChat);
    boolean sendFriendMessage(FriendChat friendChat);

    Object getGroupMessage(int gid);
    Object getFriendMessages(int uid, int friendId);
}

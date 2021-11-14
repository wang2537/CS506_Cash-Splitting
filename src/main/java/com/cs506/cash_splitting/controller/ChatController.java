package com.cs506.cash_splitting.controller;

import com.cs506.cash_splitting.model.*;
import com.cs506.cash_splitting.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat/groupchat")
    public boolean sendGroupMessage(@RequestBody GroupChat groupChat){
        try {
            return chatService.sendGroupMessage(groupChat);
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/chat/groupchat/{gid}")
    public Object getGroupMessage(@PathVariable int gid){
        try {
            return chatService.getGroupMessage(gid);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/chat/friendchat")
    public boolean sendFriendMessage(@RequestBody FriendChat friendChat) {
        try {
            return chatService.sendFriendMessage(friendChat);
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/chat/friendchat/{uid}/{friendId}")
    public Object getfriendMessages(@PathVariable int uid, @PathVariable int friendId) {
        try {
            return chatService.getFriendMessages(uid, friendId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }


}

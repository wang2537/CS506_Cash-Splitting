package com.cs506.cash_splitting.controller;

import com.cs506.cash_splitting.model.*;
import com.cs506.cash_splitting.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat/groupchat")
    public boolean sendGroupMessage(GroupChat groupChat){
        return chatService.sendGroupMessage(groupChat);
    }

}

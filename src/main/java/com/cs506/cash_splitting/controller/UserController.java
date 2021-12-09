package com.cs506.cash_splitting.controller;

import com.cs506.cash_splitting.model.*;
import com.cs506.cash_splitting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup/user")
    public Map<Object,Object> add(@RequestBody User user) {
        userService.addOrUpdateUser(user);
        HashMap<Object,Object> result = new HashMap<>();
        result.put("uid", userService.getUid(user.getUsername()));
        return result;
    }

    @GetMapping("/uid/{username}")
    public int add(@PathVariable String username) {
        return userService.getUid(username);
    }

    @GetMapping("/username/{uid}")
    public String add(@PathVariable int uid) {
        return userService.getUserName(uid);
    }

    @PostMapping("/signup/password")
    public boolean add(@RequestBody Password password) {
        return userService.addOrUpdatePassword(password);
    }

    @PostMapping("/login")
    public Map<Object,Object> identify(@RequestBody Map<String, String> map) {
        HashMap<Object,Object> result = new HashMap<>();
        if (userService.login(map.get("username"), map.get("password"))){
            result.put("Success", false);
        }
        else{
            result.put("Success", true);
            String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
            try {
                MessageDigest md = MessageDigest.getInstance("md5");
                byte[] md5 =  md.digest(token.getBytes());
                result.put("token", md5);
                int uid = userService.getUid(map.get("username"));
                result.put("uid",uid);
                return result;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @PostMapping("/group")
    public boolean createGroup(@RequestBody Group group) {
        return userService.createGroup(group);}

    @PostMapping("/group/add/{gid}/{uid}")
    public boolean addMember(@PathVariable("gid") int gid, @PathVariable("uid") int uid){
        return userService.addMember(gid, uid);
    }

    @PostMapping("/group/quit/{gid}/{uid}")
    public boolean quit(@PathVariable("gid") int gid, @PathVariable("uid") int uid){
        return userService.quitGroup(gid, uid);
    }

    @PostMapping("/group/namechange/{gid}/{newGroupName}")
    public boolean changeGroupName(@PathVariable("gid") int gid, @PathVariable("newGroupName") String newGroupName){
        return userService.changeGroupname(gid, newGroupName);
    }

    @GetMapping("/group/listGroup/{uid}")
    public Object getGroupname(@PathVariable("uid") int uid){
        return userService.getGroupname(uid);
    }

    @GetMapping("/{uid}/friend")
    public Object getFriend(@PathVariable("uid") int uid) {
        return userService.getFriend(uid);
    }

    @PostMapping("/newFriendApp")
    public boolean newFriendRequest(@RequestBody FriendApp friendApp) {
        return userService.sendFriendRequest(friendApp);
    }

    @GetMapping("/{uid}/friendApp")
    public Object getFriendRequest(@PathVariable("uid") int uid) {
        return userService.getFriendRequest(uid);
    }

    @PostMapping("/friendApp/update")
    public Object updateFriendApp(@RequestBody FriendApp friendApp) {
        return userService.updateFriendApp(friendApp);
    }

    @PostMapping("/friend/update")
    public Object updateFriend(@RequestBody Friend friend) {
        HashMap<Object,Object> result = new HashMap<>();
        String value = (String) userService.updateFriend(friend);
        result.put("result", value);
        return result;
    }

    @PostMapping("/user/sendReminder")
    public Object sendReminder(@RequestBody Reminder reminder){
        return userService.sendReminder(reminder);
    }

    @GetMapping("/user/reminder/{destination}")
    public Object getReminder(@PathVariable("destination") int destination){
        return userService.getReminder(destination);
    }

    @PostMapping("/user/reminder/update/{rid}")
    public boolean updateReminder(@PathVariable("rid") int rid){
        return userService.updateReminder(rid);
    }



}

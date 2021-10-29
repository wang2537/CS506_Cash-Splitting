package com.cs506.cash_splitting.controller;

import com.cs506.cash_splitting.model.Group;
import com.cs506.cash_splitting.model.Password;
import com.cs506.cash_splitting.model.User;
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
}

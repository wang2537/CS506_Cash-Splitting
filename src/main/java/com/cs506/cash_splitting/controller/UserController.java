package com.cs506.cash_splitting.controller;

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
    public boolean add(@RequestBody User user) {
        return userService.addOrUpdateUser(user);
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
                return result;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @GetMapping("/getname/{uid}")
    public String getUserName(@PathVariable int uid) {
        return userService.getUserName(uid);
    }


}

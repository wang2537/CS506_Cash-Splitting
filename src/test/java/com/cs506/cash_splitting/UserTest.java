package com.cs506.cash_splitting;

import com.cs506.cash_splitting.dao.*;
import com.cs506.cash_splitting.model.*;
import com.cs506.cash_splitting.service.*;
import org.hibernate.Session;
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
public class UserTest extends CashApplicationTests{

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    public User createUser(){
        User user = new User();
        user.setFirstname("test_firstname");
        user.setLastname("test_lastname");
        user.setUsername("test_username");
        return user;
    }
    @Test
    @Transactional
    public void testGetUserNameById(){
        String username = "yuegu";
        Assertions.assertSame(userService.getUid(username),1);
    }

    @Test
    @Transactional
    public void testGetUidNameByUserName(){
        int uid = 1;
        Assertions.assertEquals(userService.getUserName(uid),"yuegu");
    }

    @Test
    @Transactional
    public void testLogin(){
        String username = "yuegu";
        String password = "123456";
        Assertions.assertTrue(userService.login(username, password));
    }

    @Test
    @Transactional
    public void testSignUp(){
        Assertions.assertTrue(userService.addOrUpdateUser(this.createUser()));
    }

    @Test
    @Transactional
    public void testSignUpAndLogin(){
        User user = this.createUser();
        Assertions.assertTrue(userService.addOrUpdateUser(user));
        int uid = userService.getUid(user.getUsername());
        Assertions.assertEquals(userService.getUserName(uid), user.getUsername());
        Password password = new Password();
        password.setPassword("password");
        password.setUid(uid);
        Assertions.assertTrue(userService.addOrUpdatePassword(password));
//        System.out.println(user.getUsername());
//        System.out.println(password.getPassword());
//        System.out.println(userService.login("test_username", "password"));
    }

    @Test
    @Transactional
    public void testSendFriendRequest() {
        FriendApp friendApp = new FriendApp();
        friendApp.setSource(1);
        friendApp.setDestination(2);
        friendApp.setAid(6);
        Assertions.assertTrue(userService.sendFriendRequest(friendApp));
        FriendApp friendApp2 = new FriendApp();
        friendApp2.setSource(1);
        friendApp2.setDestination(2);
        friendApp2.setAid(7);
        Assertions.assertFalse(userService.sendFriendRequest(friendApp2));
        FriendApp friendApp3 = new FriendApp();
        friendApp3.setSource(2);
        friendApp3.setDestination(1);
        friendApp3.setAid(8);
        Assertions.assertTrue(userService.sendFriendRequest(friendApp3));

    }

    @Test
    @Transactional
    public void testGetFriendRequest() {
        List<FriendApp> empty_list = new ArrayList<>();
        Assertions.assertNotEquals(userService.getFriendRequest(2), empty_list);
        Assertions.assertNotEquals(userService.getFriendRequest(5), empty_list);
        Assertions.assertEquals(userService.getFriendRequest(1), empty_list);
    }

    @Test
    @Transactional
    public void testUpdateFriendApp() {
        FriendApp friendApp = new FriendApp();
        friendApp.setAid(1);
        friendApp.setSource(2);
        friendApp.setDestination(3);
        friendApp.setStatus("approved");
        Assertions.assertEquals(userService.updateFriendApp(friendApp), true);
        Assertions.assertEquals(userService.updateFriendApp(friendApp), false);
        FriendApp friendApp2 = new FriendApp();
        friendApp2.setAid(6);
        friendApp2.setSource(4);
        friendApp2.setDestination(6);
        friendApp2.setStatus("approved");
        Assertions.assertEquals(userService.updateFriendApp(friendApp2), "successfully add old friend");
    }

    @Test
    @Transactional
    public void testUpdateFriend() {
        Friend friend = new Friend(6, 4);
        Assertions.assertEquals(userService.updateFriend(friend), "successfully add old friend");
        Friend friend1 = new Friend(7, 4);
        friend1.setStatus("invalid");
        Assertions.assertEquals(userService.updateFriend(friend1), "successfully delete friend");
        Friend friend2 = new Friend(4, 6);
        Assertions.assertEquals(userService.updateFriend(friend2), "nothing changed");

    }

}

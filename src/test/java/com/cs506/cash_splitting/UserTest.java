package com.cs506.cash_splitting;

import com.cs506.cash_splitting.dao.*;
import com.cs506.cash_splitting.model.*;
import com.cs506.cash_splitting.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest extends CashApplicationTests{

    @Autowired
    private UserService userService;

    @Autowired
    private FaqService faqService;

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
        String password = "1450575459";
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
        password.setPassword("1450575459");
        password.setUid(uid);
        Assertions.assertTrue(userService.addOrUpdatePassword(password));
        Assertions.assertTrue(userService.login("test_username", "123456"));
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
        friendApp2.setAid(11);
        friendApp2.setSource(4);
        friendApp2.setDestination(6);
        friendApp2.setStatus("approved");
        Assertions.assertEquals(userService.updateFriendApp(friendApp2), true);
    }

    @Test
    @Transactional
    public void testUpdateFriend() {
        Friend friend = new Friend(6, 4);
        Assertions.assertEquals(userService.updateFriend(friend), "succeed in adding old friend phyTA");
        Friend friend1 = new Friend(7, 4);
        friend1.setStatus("invalid");
        Assertions.assertEquals(userService.updateFriend(friend1), "Succeed in deleting Insipid");
        Friend friend2 = new Friend(4, 6);
        Assertions.assertEquals(userService.updateFriend(friend2), "error, nothing changed");

    }

    @Test
    @Transactional
    public void testCreateGroup(){
        Group group1 = new Group(2,1, "yueyu-group");
        Assertions.assertTrue(userService.createGroup(group1));
        Assertions.assertEquals(group1.getGid(), 2);

        // pass an existing gid, it will be ignored and automatically use a new gid
        Group group2 = new Group(1,1, "non-user-group");
        Assertions.assertTrue(userService.createGroup(group2));
        Assertions.assertEquals(group2.getGid(), 3);
    }

    @Test
    @Transactional
    public void testAddMember(){
        // user already in the group
        Assertions.assertFalse(userService.addMember(1,1));
        // user not in the group
        Assertions.assertTrue(userService.addMember(1,3));
        // no group with this gid
        Assertions.assertFalse(userService.addMember(5,1));
        // user used to be in the group
        Assertions.assertTrue(userService.addMember(1,2));
    }

    @Test
    @Transactional
    public void testQuitGroup(){
        // user not in the group
        Assertions.assertFalse(userService.quitGroup(1, 2));
        Assertions.assertFalse(userService.quitGroup(1, 3));
        //user in the group
        Assertions.assertTrue(userService.quitGroup(1, 1));
    }

    @Test
    @Transactional
    public void testChangeGroupName(){
        // group not exist
        Assertions.assertFalse(userService.changeGroupname(6, "newName"));
        // group exist
        Assertions.assertTrue(userService.changeGroupname(1, "newName"));
    }

    @Test
    @Transactional
    public void testGetGroupName(){
        Group obj = (Group) ((ArrayList) userService.getGroupname(1)).get(0);
        String groupname = obj.getGroupname();
        System.out.println(groupname);
        Assertions.assertEquals(groupname, "yuegu-group");
    }

    @Test
    @Transactional
    public void testGetFaq() {
        List FaqList = (List) faqService.getAllFaq();
        Assertions.assertEquals(FaqList.size(), 3);
    }
}

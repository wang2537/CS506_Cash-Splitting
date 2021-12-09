package com.cs506.cash_splitting;

import com.cs506.cash_splitting.controller.FaqController;
import com.cs506.cash_splitting.controller.UserController;
import com.cs506.cash_splitting.dao.*;
import com.cs506.cash_splitting.model.*;
import com.cs506.cash_splitting.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest extends CashApplicationTests{

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @Autowired
    private FaqController faqController;

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
        Assertions.assertSame(userController.add(username),1);
    }

    @Test
    @Transactional
    public void testGetUidNameByUserName(){
        int uid = 1;
        Assertions.assertEquals(userController.add(uid),"yuegu");
    }

    @Test
    @Transactional
    public void testLogin(){
        String username = "yuegu";
        String password = "123456";
        HashMap<String,String> input = new HashMap<>();
        input.put("username", username);
        input.put("password", password);
        HashMap<Object, Object> result = (HashMap<Object, Object>) userController.identify(input);
        Assertions.assertTrue((Boolean) result.get("Success"));
        Assertions.assertNotNull(result.get("token"));
        Assertions.assertEquals(result.get("uid"), 1);
    }

    @Test
    @Transactional
    public void testSignUp(){
        Map<Object,Object> result = new HashMap<>();
        result = userController.add(this.createUser());
        Assertions.assertNotNull(result.get("uid"));
        System.out.println(result.get("uid"));
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
        Assertions.assertTrue(userController.newFriendRequest(friendApp));
        FriendApp friendApp2 = new FriendApp();
        friendApp2.setSource(1);
        friendApp2.setDestination(2);
        friendApp2.setAid(7);
        Assertions.assertFalse(userController.newFriendRequest(friendApp2));
        FriendApp friendApp3 = new FriendApp();
        friendApp3.setSource(2);
        friendApp3.setDestination(1);
        friendApp3.setAid(8);
        Assertions.assertTrue(userController.newFriendRequest(friendApp3));

    }

    @Test
    @Transactional
    public void testGetFriendRequest() {
        List<FriendApp> empty_list = new ArrayList<>();
        Assertions.assertNotEquals(userController.getFriendRequest(2), empty_list);
        Assertions.assertNotEquals(userController.getFriendRequest(5), empty_list);
        Assertions.assertNotEquals(userController.getFriendRequest(1), empty_list);
    }

    @Test
    @Transactional
    public void testUpdateFriendApp() {
        FriendApp friendApp = new FriendApp();
        friendApp.setAid(1);
        friendApp.setSource(2);
        friendApp.setDestination(3);
        friendApp.setStatus("approved");
        Assertions.assertEquals(userController.updateFriendApp(friendApp), true);
        Assertions.assertEquals(userController.updateFriendApp(friendApp), false);
        FriendApp friendApp2 = new FriendApp();
        friendApp2.setAid(11);
        friendApp2.setSource(4);
        friendApp2.setDestination(6);
        friendApp2.setStatus("approved");
        Assertions.assertEquals(userController.updateFriendApp(friendApp2), true);

        FriendApp friendApp3 = new FriendApp();
        friendApp3.setAid(12);
        friendApp3.setSource(1);
        friendApp3.setDestination(8);
        friendApp3.setStatus("denied");
        Assertions.assertEquals(userController.updateFriendApp(friendApp3), true);

    }

    @Test
    @Transactional
    public void testUpdateFriend() {
        Friend friend = new Friend(6, 4);
        HashMap<Object,Object> result1 = new HashMap<>();
        result1.put("result", "succeed in adding old friend phyTA");
        Assertions.assertEquals(userController.updateFriend(friend), result1);
        Friend friend1 = new Friend(7, 4);
        friend1.setStatus("invalid");
        HashMap<Object,Object> result2 = new HashMap<>();
        result2.put("result", "Succeed in deleting Insipid");
        Assertions.assertEquals(userController.updateFriend(friend1), result2);
        Friend friend2 = new Friend(4, 6);
        HashMap<Object,Object> result3 = new HashMap<>();
        result3.put("result", "error, nothing changed");
        Assertions.assertEquals(userController.updateFriend(friend2), result3);

    }

    @Test
    @Transactional
    public void testGetFriendList() {
        FriendList friendList = (FriendList) userController.getFriend(4);
        Assertions.assertEquals(friendList.getUser().getUid(), 4);
        Assertions.assertEquals(friendList.getFriendList().size(), 2);
        Assertions.assertEquals(friendList.getFriendList().get(0).getUid(), 7);
    }


    @Test
    @Transactional
    public void testCreateGroup(){
        Group group1 = new Group(2,1, "yueyu-group");
        Assertions.assertTrue(userController.createGroup(group1));
        Assertions.assertEquals(group1.getGid(), 2);

        // pass an existing gid, it will be ignored and automatically use a new gid
        Group group2 = new Group(1,1, "non-user-group");
        Assertions.assertTrue(userController.createGroup(group2));
        Assertions.assertEquals(group2.getGid(), 3);
    }

    @Test
    @Transactional
    public void testAddMember(){
        // user already in the group
        Assertions.assertFalse(userController.addMember(1,1));
        // user not in the group
        Assertions.assertTrue(userController.addMember(1,3));
        // no group with this gid
        Assertions.assertFalse(userController.addMember(5,1));
        // user used to be in the group
        Assertions.assertTrue(userController.addMember(1,2));
    }

    @Test
    @Transactional
    public void testQuitGroup(){
        // user not in the group
        Assertions.assertFalse(userController.quit(1, 2));
        Assertions.assertFalse(userController.quit(1, 3));
        //user in the group
        Assertions.assertTrue(userController.quit(1, 1));
    }

    @Test
    @Transactional
    public void testChangeGroupName(){
        // group not exist
        Assertions.assertFalse(userController.changeGroupName(6, "newName"));
        // group exist
        Assertions.assertTrue(userController.changeGroupName(1, "newName"));
    }

    @Test
    @Transactional
    public void testGetGroupName(){
        Group obj = (Group) ((ArrayList) userController.getGroupname(1)).get(0);
        String groupname = obj.getGroupname();
        System.out.println(groupname);
        Assertions.assertEquals(groupname, "yuegu-group");
    }

    @Test
    @Transactional
    public void testGetFaq() {
        List FaqList = (List) faqController.getAllFaq();
        Assertions.assertEquals(FaqList.size(), 3);
    }

    @Test
    @Transactional
    public void testGetUserWithName() {
        User user = (User) userService.get("yuegu");
        Assertions.assertEquals(user.getUsername(), "yuegu");
        Assertions.assertEquals(user.getUid(), 1);
        Assertions.assertEquals(user.getFirstname(), "yueyu");
        Assertions.assertEquals(user.getLastname(), "wang");
        Assertions.assertEquals(user.getDefault_currency(), "USD");
    }

}

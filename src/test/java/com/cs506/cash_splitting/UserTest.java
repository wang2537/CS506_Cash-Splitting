package com.cs506.cash_splitting;

import com.cs506.cash_splitting.dao.UserDAO;
import com.cs506.cash_splitting.model.Password;
import com.cs506.cash_splitting.model.User;
import com.cs506.cash_splitting.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

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
        Assertions.assertTrue(userService.addOrUpdateUser(this.createUser()));
        int uid = userService.getUid(user.getUsername());
        Assertions.assertEquals(userService.getUserName(uid), user.getUsername());
        Password password = new Password();
        password.setPassword("password");
        password.setUid(uid);
        Assertions.assertTrue(userService.addOrUpdatePassword(password));
//        Assertions.assertTrue(userService.login(user.getUsername(), password.getPassword()));
    }
}

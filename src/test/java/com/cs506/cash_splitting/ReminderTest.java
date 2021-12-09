package com.cs506.cash_splitting;


import com.cs506.cash_splitting.model.*;
import com.cs506.cash_splitting.controller.*;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReminderTest extends CashApplicationTests{

    @Autowired
    private TransactionController transactionController;

    @Autowired
    private UserController userController;

    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Transaction transaction4;
    private Transaction transaction5;
    private Reminder reminder1;
    private Reminder reminder2;

    @Before
    @Override
    public void init() {
        super.init();
        transaction1 = new Transaction();
        transaction2 = new Transaction();
        transaction3 = new Transaction();
        transaction4 = new Transaction();
        transaction5 = new Transaction();
        reminder1 = new Reminder();
        reminder2 = new Reminder();

        reminder1.setRid(1);
        reminder1.setSource(7);
        reminder1.setDestination(4);

        reminder2.setRid(2);
        reminder2.setSource(8);
        reminder2.setDestination(4);

        transaction1.setTid(1);
        transaction1.setSource(7);
        transaction1.setDestination(4);
        transaction1.setAmount(100.0);
        transaction1.setCurrency("CNY");
        transaction1.setComment("Test transaction CNY");
        transaction1.setCreate_time("2021-11-11 11:11:11");

        transaction2.setTid(2);
        transaction2.setSource(7);
        transaction2.setDestination(4);
        transaction2.setAmount(49.99);
        transaction2.setCurrency("USD");
        transaction2.setComment("Test transaction USD");
        transaction2.setCreate_time("2021-11-12 11:11:11");

        transaction3.setTid(3);
        transaction3.setSource(7);
        transaction3.setDestination(4);
        transaction3.setAmount(5000.00);
        transaction3.setCurrency("JPY");
        transaction3.setComment("Test transaction JPY");
        transaction3.setCreate_time("2021-11-13 11:11:11");

        transaction4.setTid(4);
        transaction4.setSource(7);
        transaction4.setDestination(4);
        transaction4.setAmount(5000.00);
        transaction4.setCurrency("CNY");
        transaction4.setComment("Test transaction CNY");
        transaction4.setCreate_time("2021-12-7 11:11:11");

        transaction5.setTid(5);
        transaction5.setSource(8);
        transaction5.setDestination(4);
        transaction5.setAmount(3000.00);
        transaction5.setCurrency("CNY");
        transaction5.setComment("Test transaction CNY twice");
        transaction5.setCreate_time("2021-11-30 11:11:11");


    }

    @Test
    @Transactional
    public void testPassiveReminder(){
        transactionController.add(transaction1);
        transactionController.add(transaction2);
        transactionController.add(transaction3);
        transactionController.add(transaction4);
        transactionController.add(transaction5);
        List<Transaction> reminder = (List<Transaction>) transactionController.get_reminder(4);
        Assertions.assertEquals(reminder.size(), 4);
    }

    @Test
    @Transactional
    public void testSendAndUpdateReminder(){
        transactionController.add(transaction1);
        transactionController.add(transaction2);
        transactionController.add(transaction3);
        transactionController.add(transaction4);
        transactionController.add(transaction5);
        Assertions.assertTrue((Boolean) userController.sendReminder(reminder1));
        Assertions.assertTrue((Boolean) userController.sendReminder(reminder2));
        Assertions.assertTrue(userController.updateReminder(1));
        List reminderList7 = (List) userController.getReminder(4);
        Assertions.assertFalse(reminderList7.isEmpty());
        Assertions.assertTrue(userController.updateReminder(2));
        List reminderList8 = (List) userController.getReminder(4);
        Assertions.assertTrue(reminderList8.isEmpty());
    }

    @Test
    @Transactional
    public void testGetReminder(){
        transactionController.add(transaction1);
        transactionController.add(transaction2);
        transactionController.add(transaction3);
        transactionController.add(transaction4);
        transactionController.add(transaction5);
        Assertions.assertTrue((Boolean) userController.sendReminder(reminder1));
        Assertions.assertTrue((Boolean) userController.sendReminder(reminder2));
        List reminderList = (List) userController.getReminder(4);
        Assertions.assertFalse(reminderList.isEmpty());
        Assertions.assertEquals(reminderList.size(), 2);
    }

    @After
    @Override
    public void after() {
        super.after();
        System.out.println("Reminder test Finished");
    }



}

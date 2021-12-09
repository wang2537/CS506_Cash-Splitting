package com.cs506.cash_splitting;
import com.cs506.cash_splitting.controller.TransactionController;
import com.cs506.cash_splitting.model.*;
import com.cs506.cash_splitting.service.*;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionTest extends CashApplicationTests{
    @Autowired
    private  TransactionService transactionService;

    @Autowired
    private TransactionController transactionController;

    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Transaction transaction4;
    private Transaction transaction5;

    @Before
    @Override
    public void init() {
        super.init();
        transaction1 = new Transaction();
        transaction2 = new Transaction();
        transaction3 = new Transaction();
        transaction4 = new Transaction();
        transaction5 = new Transaction();


        transaction1.setTid(1);
        transaction1.setSource(4);
        transaction1.setDestination(7);
        transaction1.setAmount(100.0);
        transaction1.setCurrency("CNY");
        transaction1.setComment("Test transaction CNY");


        transaction2.setTid(2);
        transaction2.setSource(7);
        transaction2.setDestination(4);
        transaction2.setAmount(49.99);
        transaction2.setCurrency("USD");
        transaction2.setComment("Test transaction USD");


        transaction3.setTid(3);
        transaction3.setSource(4);
        transaction3.setDestination(7);
        transaction3.setAmount(5000.00);
        transaction3.setCurrency("JPY");
        transaction3.setComment("Test transaction JPY");


        transaction4.setTid(4);
        transaction4.setSource(7);
        transaction4.setDestination(4);
        transaction4.setAmount(5000.00);
        transaction4.setCurrency("CNY");
        transaction4.setComment("Test transaction CNY");


        transaction5.setTid(5);
        transaction5.setSource(4);
        transaction5.setDestination(7);
        transaction5.setAmount(3000.00);
        transaction5.setCurrency("CNY");
        transaction5.setComment("Test transaction CNY twice");

    }

    @Test
    @Transactional
    public void testEmptyTransactionAndBalance() {
        List transactionList = (List) transactionController.get_transaction(1);
        Assertions.assertTrue(transactionList.isEmpty());
        List balanceList = (List) transactionController.get_total_balance(1);
        Assertions.assertTrue(balanceList.isEmpty());
    }

    @Test
    @Transactional
    public void testSendTransactionAndGetTransaction() {
        transactionController.add(transaction1);
        transactionController.add(transaction2);
        transactionController.add(transaction3);
        transactionController.add(transaction4);
        List transactionList = (List) transactionController.get_transaction(4);
        Assertions.assertEquals(transactionList.size(), 4);

    }

    @Test
    @Transactional
    public void testBalanceNotEmpty() {
        transactionController.add(transaction1);
        transactionController.add(transaction2);
        transactionController.add(transaction3);
        transactionController.add(transaction4);
        List balanceList = (List) transactionController.get_total_balance(4);
        Assertions.assertEquals(balanceList.size(), 3);
    }

    @Test
    @Transactional
    public void testGetBalance() {
        transactionController.add(transaction1);
        transactionController.add(transaction2);
        transactionController.add(transaction3);
        transactionController.add(transaction4);
        Map<String, Integer> source_destination = new HashMap<>();
        source_destination.put("source", 4);
        source_destination.put("destination", 7);
        List balance_between_list = (List) transactionController.get_balance(source_destination);
        Assertions.assertEquals(balance_between_list.size(), 3);
    }

    @Test
    @Transactional
    public void testPayBalance() {
        transactionController.add(transaction1);
        transactionController.add(transaction2);
        transactionController.add(transaction3);
        transactionController.add(transaction4);
        Map<String, Integer> source_destination = new HashMap<>();
        source_destination.put("source", 4);
        source_destination.put("destination", 7);
        List balance_between_list = (List) transactionController.get_balance(source_destination);
        Assertions.assertEquals(balance_between_list.size(), 3);
        transactionController.update_one_transaction(1);
        transactionController.update_one_transaction(2);
        transactionController.update_one_transaction(3);
        transactionController.update_one_transaction(4);
        List balance_between_list_after = (List) transactionController.get_balance(source_destination);
        Assertions.assertTrue(balance_between_list_after.isEmpty());
        List individual_balance_after_source = (List) transactionController.get_total_balance(4);
        Assertions.assertTrue(individual_balance_after_source.isEmpty());
        List individual_balance_after_dest = (List) transactionController.get_total_balance(7);
        Assertions.assertTrue(individual_balance_after_dest.isEmpty());
    }

    @Test
    @Transactional
    public void testSettleAll() {
        transactionController.add(transaction1);
        transactionController.add(transaction2);
        transactionController.add(transaction3);
        transactionController.add(transaction4);
        transactionController.add(transaction5);
        Map<String, Object> CNYmap = new HashMap<>();
        CNYmap.put("source", 7);
        CNYmap.put("destination", 4);
        CNYmap.put("currency", "CNY");
        transactionController.settle_all(CNYmap);
        Map<String, Integer> source_destination = new HashMap<>();
        source_destination.put("source", 4);
        source_destination.put("destination", 7);
        List balance_between_list_after_CNY = (List) transactionController.get_balance(source_destination);
        Assertions.assertEquals(balance_between_list_after_CNY.size(), 2);

        Map<String, Object> JPYmap = new HashMap<>();
        JPYmap.put("source", 4);
        JPYmap.put("destination", 7);
        JPYmap.put("currency", "JPY");
        transactionController.settle_all(JPYmap);
        List balance_between_list_after_JPY = (List) transactionController.get_balance(source_destination);
        Assertions.assertEquals(balance_between_list_after_JPY.size(), 1);
        List individual_balance_after_source = (List) transactionController.get_total_balance(4);
        Assertions.assertFalse(individual_balance_after_source.isEmpty());
    }

    @Test
    @Transactional
    public void testGroupSplit() {
        Object []source = {1};
        Object []destination = {2, 3, 4, 5, 6};
        List <Object> amount = new ArrayList<>();
        amount.add(10.00);
        amount.add(20.00);
        amount.add(30.00);
        amount.add(40.00);
        amount.add(60.00);
        Object []currency = {"USD"};
        Object []comment = {"Test"};
        Object []gid = {1};
        Map<String, List<Object>> map = new HashMap<>();
        map.put("source", List.of(source));
        map.put("destination", List.of(destination));
        map.put("amount", amount);
        map.put("currency", List.of(currency));
        map.put("gid", List.of((gid)));
        map.put("comment", List.of(comment));
        transactionController.batch_transaction(map);
        List balanceList = (List) transactionController.get_total_balance(1);
        Assertions.assertEquals(balanceList.size(), 1);
    }


}

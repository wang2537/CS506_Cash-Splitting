package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.Balance;
import com.cs506.cash_splitting.model.User;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BalanceDAOImpI implements BalanceDAO{
    @Autowired
    private EntityManager entityManager;

    @Override
    @ResponseBody
    public Object get(){
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.createSQLQuery("select * from historydb").addEntity(Balance.class);
        List <Balance> balanceList = new ArrayList<>();
        List list = query.list();
        for (Object o : list) {
            Balance balance = (Balance) o;
            balanceList.add(balance);
        }
        return balanceList;
    }

    @Override
    @ResponseBody
    public Object getRenterBalance(String renterName) {
        Session currSession = entityManager.unwrap(Session.class);
        int uid = get_uid(renterName);
        SQLQuery query = currSession.createSQLQuery("select * from historydb where destination_id = :uid").addEntity(Balance.class);
        query.setParameter("uid", uid);
        List <Balance> balanceList = new ArrayList<>();
        List list = query.list();
        for (Object o : list) {
            Balance balance = (Balance) o;
            balanceList.add(balance);
        }
        return balanceList;
    }

    @Override
    public boolean change_pay(Balance balance) {
        Session currSession = entityManager.unwrap(Session.class);
        User user = currSession.get(User.class, balance.getDestination_id());
        user.setBalance(user.getBalance() + balance.getAmount());
        currSession.update(user);
        currSession.saveOrUpdate(balance);
        return true;
    }

    public int get_uid(String username) {
        Session currSession = entityManager.unwrap(Session.class);
        Query query = currSession.createSQLQuery("select * from userdb" +
                " where username = :name").addEntity(User.class);
        query.setParameter("name", username);
        List list = query.list();
        if (list.isEmpty()){
            return -1;
        }
        User user = (User) list.get(0);
        return user.getUid();
    }




}

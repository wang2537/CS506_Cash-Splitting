package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.Transaction;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionDAOImpl implements TransactionDAO{

    @Autowired
    private EntityManager entityManager;
    @Override
    public Object getTotalBalance(int uid) {
        Session currSession = entityManager.unwrap(Session.class);
        Query query = currSession.createSQLQuery("with m1 as (select currency, SUM(amount) as total from transactiondb where source = :userid and status = :sta" +
                " group by currency), m2 as (select currency, 0-SUM(amount) as total from transactiondb where destination = :userid and status = :sta group by currency)," +
                "m3 as (select currency, total from m2 where currency not in (select distinct currency from m1)), m4 as (select currency, total from m1 where currency" +
                " not in (select distinct currency from m2) group by currency) select m1.currency, m1.total+m2.total as total from m1, m2 where m1.currency = m2.currency " +
                "UNION select * from m3 UNION select * from m4 ");
        query.setParameter("userid", uid);
        query.setParameter("sta", "unpaid");
        return query.getResultList();
    }

    @Override
    public Object createTransaction(Transaction transaction) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.saveOrUpdate(transaction);
//        if (transaction.getCurrency().equals("USD")){
//        int source_id = transaction.getSource();
//        int destination_id = transaction.getDestination();
//        double amount = transaction.getAmount();
//        //set source
//        User source = currSession.get(User.class, source_id);
//        double lent = source.getLent();
//        double src_balance = source.getTotal_balance();
//        BigDecimal bg3 = new BigDecimal(amount+lent);
//        source.setLent(bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//        BigDecimal bg = new BigDecimal(src_balance-amount);
//        source.setTotal_balance(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//        //set destination
//        User destination = currSession.get(User.class, destination_id);
//        double borrow = destination.getBorrowed();
//        double des_balance = destination.getTotal_balance();
//        BigDecimal bg4 = new BigDecimal(borrow+amount);
//        destination.setBorrowed(bg4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//        BigDecimal bg2 = new BigDecimal(des_balance+amount);
//        destination.setTotal_balance(bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//        currSession.saveOrUpdate(source);
//        currSession.saveOrUpdate(destination);
//        }
        return true;
    }

    @Override
    public Object getBalance(int source_id, int destination_id) {
        Session currSession = entityManager.unwrap(Session.class);
        Query query = currSession.createSQLQuery("with m1 as (select currency, SUM(amount) as total from transactiondb" +
                " where source = :src1 and destination = :des1 and status = :sta1 group by currency)," +
                "m2 as (select currency, 0-SUM(amount) as total from transactiondb where source = :src2 and destination = :des2" +
                " and status = :sta2 group by currency), m3 as (select currency, total from m2 where currency " +
                "not in (select distinct currency from m1) group by currency), m4 as (select currency, total " +
                "from m1 where currency not in (select distinct currency from m2) group by currency), m5 as (select m1.currency, m1.total+m2.total as total from " +
                "m1, m2 where m1.currency=m2.currency)select * from m3 UNION select  * from m4 UNION select * from m5");
        query.setParameter("src1", source_id);
        query.setParameter("des1", destination_id);
        query.setParameter("sta1", "unpaid");
        query.setParameter("src2", destination_id);
        query.setParameter("des2", source_id);
        query.setParameter("sta2", "unpaid");


        return query.getResultList();
    }

    @Override
    public double double_two(double number) {
        BigDecimal bg = new BigDecimal(number);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public Object getTransaction(int uid) {
        Session currSession = entityManager.unwrap(Session.class);
        Query query = currSession.createSQLQuery("select * from transactiondb where source = :user or destination = :user order by create_time ASC ").addEntity(Transaction.class);
        query.setParameter("user", uid);
        List list = query.getResultList();
        List<Transaction> transactionList = new ArrayList<>();
        for (Object o : list){
            Transaction t = (Transaction) o;
            transactionList.add(t);
        }
        return transactionList;
    }

    @Override
    public Object updateOneTransaction(int tid) {
        Session currSession = entityManager.unwrap(Session.class);
        Query query = currSession.createSQLQuery("select * from transactiondb where tid = :tid").addEntity(Transaction.class);
        query.setParameter("tid", tid);
        Transaction transaction = (Transaction) query.getResultList().get(0);
        transaction.setStatus("paid");
        currSession.saveOrUpdate(transaction);
        return true;

    }
}

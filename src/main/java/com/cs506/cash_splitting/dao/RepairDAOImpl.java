package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.Repair;
import com.cs506.cash_splitting.model.User;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class RepairDAOImpl implements RepairDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    @ResponseBody
    public Object get() {
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.createSQLQuery("select * from repairdb order by priority ASC, create_time ASC")
                .addEntity(Repair.class);
        List<Repair> repairList = new ArrayList<>();
        List list = query.list();
        for (Object o : list){
            Repair repair = (Repair) o;
            repairList.add(repair);
        }
        return repairList;
    }


    public int get_uid(String username) {
        Session currSession = entityManager.unwrap(Session.class);
        org.hibernate.Query query = currSession.createSQLQuery("select * from userdb" +
                " where username = :name").addEntity(User.class);
        query.setParameter("name", username);
        List list = query.list();
        System.out.println(list);
        if (list.isEmpty()){
            return -1;
        }
        User user = (User) list.get(0);
        return user.getUid();
    }

    @Override
    @ResponseBody
    public Object getUser(String username){
        int uid1 = get_uid(username);
        if (uid1 == -1) return new ArrayList<>();
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.createSQLQuery("select * from repairdb where uid = :uid ")
                .addEntity(Repair.class);
        query.setParameter("uid", uid1);
        List<Repair> repairList = new ArrayList<>();
        List list = query.list();
        for (Object o : list){
            Repair repair = (Repair) o;
            repairList.add(repair);
        }
        return repairList;
    }

    @Override
    public boolean addOrUpdate(Repair repair) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.saveOrUpdate(repair);
        return true;
    }

    @Override
    public boolean change_status(int mid) {
        Session currSession = entityManager.unwrap(Session.class);
        Repair repair = currSession.get(Repair.class, mid);
        if(repair.getStatus().equals("completed")){
            return true;
        }
        repair.setStatus("completed");
        repair.setModify_time(getStringToday());
        currSession.update(repair);
        return true;
    }

    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}

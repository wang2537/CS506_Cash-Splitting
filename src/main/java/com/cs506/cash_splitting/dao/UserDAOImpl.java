package com.cs506.cash_splitting.dao;
import com.cs506.cash_splitting.model.User;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    @ResponseBody
    public Object get() {
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.createSQLQuery("select * from userdb").addEntity(User.class);;
        List <User> userList = new ArrayList<>();
        List list = query.list();
        for (Object o : list) {
            User user = (User) o;
            userList.add(user);
        }
        return userList;
    }

    @Override
    @ResponseBody
    public Object get(String username1) {
        Session currSession = entityManager.unwrap(Session.class);
        int uid = get_uid(username1);
        return currSession.get(User.class, uid);
    }

    @Override
    public boolean addOrUpdate(User user) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.saveOrUpdate(user);
        return true;
    }

    @Override
    public String getUserName(int uid) {
        Session currSession = entityManager.unwrap(Session.class);
        return currSession.get(User.class, uid).getUsername();
    }


    @Override
    public List<?> check(String username, String password) {
        Session currSession = entityManager.unwrap(Session.class);
        Query query = currSession.createSQLQuery("select password from userdb where username = :name and password = :code ");
        query.setParameter("name", username);
        query.setParameter("code", password);
        return query.getResultList();
    }


    public int get_uid(String username) {
        Session currSession = entityManager.unwrap(Session.class);
        org.hibernate.Query query = currSession.createSQLQuery("select * from userdb" +
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

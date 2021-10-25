package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.Application;
import com.cs506.cash_splitting.model.Property;
import com.cs506.cash_splitting.model.User;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ApplicationImpl implements ApplicationDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    @ResponseBody
    public Object get() {
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.createSQLQuery("select * from applicationdb")
                .addEntity(Application.class);
        List<Application> applicationList = new ArrayList<>();
        List list = query.list();
        for (Object o : list){
            Application application = (Application) o;
            applicationList.add(application);
        }
        return applicationList;
    }

    @Override
    @ResponseBody
    public Object get(String username) {
        Session currSession = entityManager.unwrap(Session.class);
        int uid = get_uid(username);
        SQLQuery query = currSession.createSQLQuery("select * from applicationdb where rid = :rid")
                .addEntity(Application.class);
        query.setParameter("rid", uid);
        List<Application> applicationList = new ArrayList<>();
        List list = query.list();
        for (Object o : list){
            Application application = (Application) o;
            applicationList.add(application);
        }
        return applicationList;
    }

    @Override
    public boolean update(Application application) {
        Session currSession = entityManager.unwrap(Session.class);
        Application originApplication = currSession.get(Application.class, application.getAid());
        if (application.getStatus().equals("approved")) {
            Property property = currSession.get(Property.class, application.getPid());
            property.setRid(application.getRid());
            User renter = currSession.get(User.class, application.getRid());
            currSession.update(property);
            currSession.update(renter);
        }
        if (application.getStatus().equals("denied")) {
            User renter = currSession.get(User.class, application.getRid());
            currSession.update(renter);
        }
        originApplication.setStatus(application.getStatus());
        originApplication.setModify_time(application.getModify_time());
        currSession.update(originApplication);
        return true;
    }

    @Override
    public boolean create(Application application) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.saveOrUpdate(application);
        if (application.getStatus().equals("processing")) {
            User renter = currSession.get(User.class, application.getRid());
            currSession.update(renter);

        }
        else if (application.getStatus().equals("approved")) {
            User renter = currSession.get(User.class, application.getRid());
            currSession.update(renter);
        }
        else if (application.getStatus().equals("denied")) {
            User renter = currSession.get(User.class, application.getRid());
            currSession.update(renter);
        }

        return true;
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
}

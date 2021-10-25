package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.*;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PropertyDAOImpl implements PropertyDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    @ResponseBody
    public Object get() {
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.createSQLQuery("select * from propertydb where rid = 1")
                .addEntity(Property.class);
        List<Property> propertyList = new ArrayList<>();
        List list = query.list();
        for (Object o : list){
            Property property = (Property) o;
            propertyList.add(property);
        }
        return propertyList;
    }

    @Override
    @ResponseBody
    public Object get(String username) {
        Session currSession = entityManager.unwrap(Session.class);
        int uid = get_uid(username);
        SQLQuery query = currSession.createSQLQuery("select * from propertydb where rid = :rid")
                .addEntity(Property.class);
        query.setParameter("rid", uid);
        System.out.println(uid);
        List<Property> propertyList = new ArrayList<>();
        List list = query.list();
        for (Object o : list){
            Property property = (Property) o;
            propertyList.add(property);
        }
        return propertyList;
    }

    @Override
    @ResponseBody
    public Object get_info() {
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery query = currSession.createSQLQuery("select * from propertydb")
                .addEntity(Property.class);
        List<PropertyID> mapList = new ArrayList<>();
        List list = query.list();
        for (Object o : list){
            Property property = (Property) o;
            User user = currSession.get(User.class, property.getRid());
            mapList.add(new PropertyID(property.getAddress(), user.getUsername(),
                    user.getFirstname(), user.getLastname()));
        }
        return mapList;
    }

    @Override
    public boolean checkin(Application application) {
        Session currSession = entityManager.unwrap(Session.class);
        Property property = currSession.get(Property.class, application.getPid());
        property.setRid(application.getRid());
        currSession.update(property);
        return true;
    }

    @Override
    public boolean add(Property property) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.saveOrUpdate(property);
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

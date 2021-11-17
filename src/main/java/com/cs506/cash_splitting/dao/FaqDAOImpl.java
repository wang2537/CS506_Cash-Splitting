package com.cs506.cash_splitting.dao;


import com.cs506.cash_splitting.model.Faq;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FaqDAOImpl implements FaqDAO{

    @Autowired
    private EntityManager entityManager;

    @ResponseBody
    @Override
    public Object getAllFaq() {
        Session currSession = entityManager.unwrap(Session.class);
        SQLQuery sqlQuery = currSession.createSQLQuery("select * from faqdb").addEntity(Faq.class);
        List list = sqlQuery.list();
        List<Faq> faqList = new ArrayList<>();
        for (Object o : list){
            faqList.add((Faq) o);
        }
        return faqList;
    }


}

package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.dao.FaqDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaqServiceImpl implements FaqService{

    @Autowired
    private FaqDAO faqDAO;

    @Override
    public Object getAllFaq() {
        return faqDAO.getAllFaq();
    }
}

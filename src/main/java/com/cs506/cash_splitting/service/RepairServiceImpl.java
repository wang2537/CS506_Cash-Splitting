package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.dao.RepairDAO;
import com.cs506.cash_splitting.model.Repair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RepairServiceImpl implements RepairService{

    @Autowired
    private RepairDAO repairdao;

    @Transactional
    @Override
    public Object get() {
        return repairdao.get();
    }

    @Transactional
    @Override
    public Object getUser(String username) {
        return repairdao.getUser(username);
    }

    @Transactional
    @Override
    public boolean addOrUpdate(Repair repair) {
        return repairdao.addOrUpdate(repair);
    }

    @Transactional
    @Override
    public boolean changeStatus(int mid) {
        return repairdao.change_status(mid);
    }
}

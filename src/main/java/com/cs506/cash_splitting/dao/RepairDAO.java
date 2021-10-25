package com.cs506.cash_splitting.dao;

import com.cs506.cash_splitting.model.Repair;

public interface RepairDAO {
    Object get();
    Object getUser(String username);
    boolean addOrUpdate(Repair repair);
    boolean change_status(int mid);
}

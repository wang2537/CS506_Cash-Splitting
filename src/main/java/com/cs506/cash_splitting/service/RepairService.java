package com.cs506.cash_splitting.service;

import com.cs506.cash_splitting.model.Repair;

public interface RepairService {
    Object get();
    Object getUser(String username);
    boolean addOrUpdate(Repair repair);
    boolean changeStatus(int mid);
}

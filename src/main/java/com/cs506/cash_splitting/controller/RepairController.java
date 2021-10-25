package com.cs506.cash_splitting.controller;

import com.cs506.cash_splitting.model.Repair;
import com.cs506.cash_splitting.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RepairController {

    @Autowired
    private RepairService repairService;

    @GetMapping("/repair")
    public Object get() {
        return repairService.get();
    }

    @GetMapping("/repair/{user}")
    public Object getUser(@PathVariable String user) {
        return repairService.getUser(user);
    }

    @PostMapping("/repair")
    public boolean add(@RequestBody Repair repair) {
        return repairService.addOrUpdate(repair);
    }

    @GetMapping("/repair/complete/{mid}")
    public boolean changeStatus(@PathVariable int mid) {
        return repairService.changeStatus(mid);
    }

}

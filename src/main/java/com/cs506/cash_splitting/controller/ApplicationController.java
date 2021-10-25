package com.cs506.cash_splitting.controller;

import com.cs506.cash_splitting.model.Application;
import com.cs506.cash_splitting.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/application")
    public Object get() {
        return applicationService.get();
    }

    @GetMapping("/application/{username}")
    public Object getRenterBalance(@PathVariable String username) {
        return applicationService.get(username);
    }

    @PostMapping("/application")
    public Object createApplication(@RequestBody Application application) {
        return applicationService.create(application);
    }

    @PostMapping("/application/update")
    public Object updateApplication(@RequestBody Application application) {
        return applicationService.update(application);
    }


}
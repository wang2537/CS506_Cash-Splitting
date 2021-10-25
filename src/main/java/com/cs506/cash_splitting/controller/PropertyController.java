package com.cs506.cash_splitting.controller;


import com.cs506.cash_splitting.model.Property;
import com.cs506.cash_splitting.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @GetMapping("/property")
    public Object get() {
        return propertyService.get();
    }

    @GetMapping("/property/{username}")
    public Object get(@PathVariable String username) {
        return propertyService.get(username);
    }

    @GetMapping("/property/get")
    public Object get_info() {
        return propertyService.get_info();
    }

    @PostMapping("/property")
    public boolean add(@RequestBody Property property) {
        return propertyService.add(property);
    }
}

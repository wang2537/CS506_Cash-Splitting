package com.cs506.cash_splitting.controller;

import com.cs506.cash_splitting.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FaqController {

    @Autowired
    private FaqService faqService;

    @GetMapping("/faq")
    public Object getAllFaq() {
        return faqService.getAllFaq();
    }
}

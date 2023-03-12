package com.baeldung.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping("/management")
public class ManagementController {

    // Management page
    @GetMapping
    public String management(final Locale locale) {
        return "management";
    }
}

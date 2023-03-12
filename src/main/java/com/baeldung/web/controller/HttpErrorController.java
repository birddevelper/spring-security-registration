package com.baeldung.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HttpErrorController {

    // Access Denied Page
    @GetMapping("/accessDenied")
    public String accessDenied(){

        return "error/accessDenied";
    }
}

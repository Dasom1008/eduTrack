package com.edutrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TR001Controller {

    @GetMapping("/tR001/selectList")
    public String selectList() {
        return "tR001/tR001_List";  
    }

}
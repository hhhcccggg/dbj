package com.zwdbj.server.cfgserver.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app")
public class AppController {
    @RequestMapping(value = "/health",method = RequestMethod.GET)
    public String health() {
        return "OK";
    }
}

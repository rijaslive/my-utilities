package com.apps.essentials.essentials.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RiJAS
 * @Date 25-01-2025
 */
@RestController
public class HealthCheckController {

    @GetMapping("/__health")
    public String healthCheck(){
        return "success";
    }

}

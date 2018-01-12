package de.zalando.backlog.reportgenerator.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Value("${my.text}")
    private String text;

    @RequestMapping("/")
    public String getALMReport() {
        return "Hello " + text;
    }
}

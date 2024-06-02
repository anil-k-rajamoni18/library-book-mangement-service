package com.develop.librarybookmanagementservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {
    private static final String DEFAULT_VERSION = "0.0.0";
    @Autowired
    private BuildProperties properties;

    @GetMapping
    public String healthCheck() {
        String appVersion = StringUtils.hasText(properties.getVersion()) ? properties.getVersion() : DEFAULT_VERSION;
        return String.format("Library Book Management Service is up and running. Version: %s", appVersion);
    }
}

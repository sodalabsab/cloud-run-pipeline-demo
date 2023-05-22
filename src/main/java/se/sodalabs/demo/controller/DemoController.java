package se.sodalabs.demo.controller;

import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    public DemoController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    private final BuildProperties buildProperties;

    @GetMapping("/")
    public String sayHello(){
        return "Hello world";
    }
    @GetMapping("/version")
    public String getVersion(){
        return buildProperties.getVersion();
    }}

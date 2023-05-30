package se.sodalabs.demo.controller;

import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.sodalabs.demo.service.HubService;

@RestController
public class DemoController {

  public DemoController(BuildProperties buildProperties, HubService hubService) {
    this.buildProperties = buildProperties;
    this.hubService = hubService;
  }

  private final BuildProperties buildProperties;
  private final HubService hubService;

  @GetMapping("/")
  public String sayHello() {
    return "Hello world";
  }

  @GetMapping("/register")
  public ResponseEntity register() {
    return hubService.registerWithHub();
  }

  @GetMapping("/heartbeat")
  public ResponseEntity sendHeartbeat() {
    return hubService.sendHeartbeat();
  }

  @GetMapping("/mood/{mood}")
  public ResponseEntity setMood(@PathVariable String mood) {
    return hubService.setNewMood(mood);
  }

  @GetMapping("/version")
  public String getVersion() {
    return buildProperties.getVersion();
  }
}

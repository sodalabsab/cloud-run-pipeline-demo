package se.sodalabs.demo.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.sodalabs.demo.service.HubService;

@Component
public class ScheduledHeartbeat {

  private final HubService hubService;

  public ScheduledHeartbeat(HubService hubService) {
    this.hubService = hubService;
  }

  @Scheduled(fixedRate = 10_000)
  public void sendHeartbeat() {
    hubService.sendHeartbeat();
  }
}

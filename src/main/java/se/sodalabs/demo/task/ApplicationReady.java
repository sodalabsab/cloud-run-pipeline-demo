package se.sodalabs.demo.task;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import se.sodalabs.demo.service.HubService;

@Component
public class ApplicationReady {

  private final HubService hubService;

  public ApplicationReady(HubService hubService) {
    this.hubService = hubService;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void registerParticipant() {
    hubService.registerWithHub();
  }

}

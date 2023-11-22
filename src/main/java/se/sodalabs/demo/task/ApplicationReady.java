package se.sodalabs.demo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import se.sodalabs.demo.service.HubService;

@Component
public class ApplicationReady {

  Logger logger = LoggerFactory.getLogger(ApplicationReady.class);

  private final HubService hubService;

  public ApplicationReady(HubService hubService) {
    this.hubService = hubService;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void registerParticipant() {
    try {
      hubService.registerNewParticipant();
    } catch (ResourceAccessException e) {
      logger.error(
          "Failed to reach hub at "
              + this.hubService.getHubAddress()
              + "; will continue, but you need to make sure that it is available "
              + "(or change the environment variable HUB_ADDRESS to point to the "
              + "correct place).");
    }
  }
}

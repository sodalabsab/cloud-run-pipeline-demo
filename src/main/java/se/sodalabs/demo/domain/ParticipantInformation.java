package se.sodalabs.demo.domain;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParticipantInformation {
  private String id;
  private String name;

  public ParticipantInformation(@Value("${service.name}") String name) {
    this.id = String.valueOf(UUID.randomUUID());
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}

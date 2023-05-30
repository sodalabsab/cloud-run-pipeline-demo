package se.sodalabs.demo.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.sodalabs.demo.domain.ParticipantInformation;

@Service
public class HubService {

  private final ParticipantInformation participantInformation;

  @Value("${hub.address}")
  String hubAdress;

  RestTemplate restTemplate = new RestTemplate();
  HttpHeaders headers = new HttpHeaders();
  ObjectMapper objectMapper = new ObjectMapper();

  public HubService(ParticipantInformation participantInformation) {
    this.participantInformation = participantInformation;
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("participantId", participantInformation.getId());

    restTemplate.setErrorHandler(new ErrorHandler());

    objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
  }

  public ResponseEntity<String> registerWithHub() {
    String payload;
    try {
      payload = objectMapper.writeValueAsString(participantInformation);
      HttpEntity<String> request = new HttpEntity<>(payload, headers);
      return restTemplate.exchange(hubAdress + "/register", HttpMethod.POST, request, String.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public ResponseEntity<String> sendHeartbeat() {
    HttpEntity<String> request = new HttpEntity<>(headers);
    return restTemplate.exchange(hubAdress + "/heartbeat", HttpMethod.PUT, request, String.class);
  }

  public ResponseEntity<String> setNewMood(String mood) {
    HttpEntity<String> request = new HttpEntity<>(headers);
    return restTemplate.exchange(
        hubAdress + "/mood/" + mood, HttpMethod.PUT, request, String.class);
  }
}

package se.sodalabs.demo.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

  Logger logger = LoggerFactory.getLogger(HubService.class);

  public HubService(ParticipantInformation participantInformation) {
    this.participantInformation = participantInformation;
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("participantId", participantInformation.getId());

    restTemplate.setErrorHandler(new ErrorHandler());

    objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    // The standard JDK HTTP library does not support HTTP PATCH
    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();
    restTemplate.setRequestFactory(requestFactory);
  }

  public String getHubAdress() {
    return hubAdress;
  }

  public ResponseEntity<String> registerWithHub() {
    String payload;
    try {
      payload = objectMapper.writeValueAsString(participantInformation);
      HttpEntity<String> request = new HttpEntity<>(payload, headers);
      return restTemplate.exchange(
          hubAdress + "/api/v1/participant/", HttpMethod.POST, request, String.class);
    } catch (JsonProcessingException e) {
      logger.error("Could not register with hub: " + e.getMessage());
    }
    return null;
  }

  public ResponseEntity<String> sendHeartbeat() {
    long timestamp = System.currentTimeMillis();
    headers.set("heartbeat", String.valueOf(timestamp));
    HttpEntity<String> request = new HttpEntity<>(headers);
    ResponseEntity<String> response =
        restTemplate.exchange(
            hubAdress + "/api/v1/participant/", HttpMethod.PATCH, request, String.class);
    return returnParsedHubResponse(response);
  }

  public ResponseEntity<String> setAvailability(String availability) {
    String payload = "\"" + availability + "\"";
    HttpEntity<String> request = new HttpEntity<>(payload, headers);
    ResponseEntity<String> response =
        restTemplate.exchange(
            hubAdress + "/api/v1/participant/", HttpMethod.PUT, request, String.class);
    return returnParsedHubResponse(response);
  }

  private static ResponseEntity<String> returnParsedHubResponse(
      ResponseEntity<String> responseFromHub) {
    if (responseFromHub.getStatusCode().is2xxSuccessful()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else if (responseFromHub.getStatusCode().is4xxClientError()) {
      return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    } else {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
